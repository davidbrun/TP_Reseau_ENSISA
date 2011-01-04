package hassen.automation.common;

import java.net.Socket;
import java.util.Vector;

public class SessionConfigurator {

	private Settings settings;
	private Socket connection;
	private boolean allreadyTried;
	
	public SessionConfigurator (Settings settings) {
		this.settings = settings;
		this.connection = null;
		this.allreadyTried = false;
	}

	public Settings config (Device device) {
		return null;
	}

	public Vector<Device> getInputs () {
		return null;
	}

	public Vector<Device> getOutputs () {
		return null;
	}

	public Vector<Mapping> getMaps () {
		return null;
	}

	public boolean clearMaps () {
		return false;
	}

	public boolean eraseMap (int id) {
		return true;
	}

	public boolean addMap (Mapping mapping) {
		return true;
	}

	public void connect () throws Exception {
		if (connection != null) disconnect();
		if (allreadyTried) {
			throw new Exception ("AllReady tried");
		} else {
			allreadyTried = true;
			connection = new Socket (settings.getServerName(), settings.getCtrlPort());
		}
	}

	public void disconnect () throws Exception {
		if (connection != null) connection.close();
		allreadyTried = false;
		connection = null;
	}

}
