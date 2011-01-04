package hassen.automation.common;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

public class SessionServer implements Runnable {

	private IDeviceManager manager;
	private Socket connection;
	private Thread thread;
	
	public SessionServer (Socket connection, IDeviceManager document) {
		this.connection = connection;
		this.manager = document;
	}

	public void startSession() {
		thread = new Thread (this);
		thread.start();
	}

	@Override
	public void run() {
		try {
			ManagementReader reader = new ManagementReader (connection.getInputStream());
			boolean loop = true;
			while (loop) {
				reader.receive ();
				process (reader);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void process(ManagementReader reader) {
		switch (reader.getType()) {
		case Protocol.QUERY_CONFIG:
			processConfig();
			break;
		case Protocol.QUERY_DECLARE:
			processDeclare(reader.getDevice());
			break;
		case Protocol.QUERY_UNDECLARE:
			processUndeclare(reader.getId());
			break;
		case Protocol.QUERY_INPUTS:
			processInputs ();
			break;
		case Protocol.QUERY_OUTPUTS:
			processOutputs ();
			break;
		case Protocol.QUERY_MAPS:
			processMaps ();
			break;
		case Protocol.QUERY_MAP_CLEAR:
			processMapClear ();
			break;
		case Protocol.QUERY_MAP_ERASE:
			processMapErase (reader.getId());
			break;
		case Protocol.QUERY_MAP_ADD:
			processMapAdd(reader.getFrom(), reader.getTo());
			break;
		default :
			processError ();
			break;
		}
	}

	private void processMapAdd(int from, int to) {
		try {
			boolean success = manager.mapAdd(from, to);
			ManagementWriter writer = new ManagementWriter (connection.getOutputStream());
			if (success) writer.createReplySuccess();
			else writer.createReplyFail();
			writer.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processMapErase(int id) {
		try {
			boolean success = manager.mapErase(id);
			ManagementWriter writer = new ManagementWriter (connection.getOutputStream());
			if (success) writer.createReplySuccess();
			else writer.createReplyFail();
			writer.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processMapClear() {
		try {
			boolean success = manager.mapClear();
			ManagementWriter writer = new ManagementWriter (connection.getOutputStream());
			if (success) writer.createReplySuccess();
			else writer.createReplyFail();
			writer.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processMaps() {
		try {
			ManagementWriter writer = new ManagementWriter (connection.getOutputStream());
			writer.createReplyMaps(manager.getMappings());
			writer.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processOutputs() {
		try {
			Vector<Device> devices = new Vector<Device> ();
			for (Device d : manager.getDevices()) {
				if (d.isOutput()) devices.add(d);
			}
			ManagementWriter writer = new ManagementWriter (connection.getOutputStream());
			writer.createReplyOutputs(devices);
			writer.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processInputs() {
		try {
			Vector<Device> devices = new Vector<Device> ();
			for (Device d : manager.getDevices()) {
				if (d.isInput()) devices.add(d);
			}
			ManagementWriter writer = new ManagementWriter (connection.getOutputStream());
			writer.createReplyInputs(devices);
			writer.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processUndeclare(int id) {
		try {
			boolean success = manager.undeclare(id);
			ManagementWriter writer = new ManagementWriter (connection.getOutputStream());
			if (success) writer.createReplySuccess();
			else writer.createReplyFail();
			writer.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processDeclare(Device device) {
		try {
			boolean success = manager.declare(device);
			ManagementWriter writer = new ManagementWriter (connection.getOutputStream());
			if (success) writer.createReplySuccess();
			else writer.createReplyFail();
			writer.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processConfig() {
		try {
			Settings settings = manager.getSettings();
			ManagementWriter writer = new ManagementWriter (connection.getOutputStream());
			writer.createReplyConfig(settings);
			writer.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processError() {
	}

}
