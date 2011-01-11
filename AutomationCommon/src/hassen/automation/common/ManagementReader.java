package hassen.automation.common;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class ManagementReader {

	private DataInputStream inputStream;
	private int type;
	private Device device;
	private int id;
	private int from, to;
	private Settings settings;
	private Vector<Device> devices;
	private Vector<Mapping> mappings;
	
	public ManagementReader(InputStream inputStream) {
		this.inputStream = new DataInputStream (inputStream);
	}

	public Device getDevice() {
		return device;
	}

	public int getId() {
		return id;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	public Settings getSettings() {
		return settings;
	}

	public Vector<Device> getDevices() {
		return devices;
	}

	public Vector<Mapping> getMappings() {
		return mappings;
	}

	private int readInt () {
		try {
			return inputStream.readInt();
		} catch (IOException e) {
			return 0;
		}
	}

	private String readString () {
		try {
			return inputStream.readUTF();
		} catch (IOException e) {
			return "";
		}
	}

	private Device readDeviceDescription () {
		Device local = new Device ();
		int kind = readInt();
		if (kind == Protocol.DEVICE_INPUT) local.setInput();
		if (kind == Protocol.DEVICE_OUTPUT) local.setOutput();
		int type = readInt();
		if (type == Protocol.DEVICE_BINARY) local.setBinary();
		if (type == Protocol.DEVICE_EVENT) local.setEvent();
		if (type == Protocol.DEVICE_RANGE)
		{
			int min = readInt();
			int max = readInt();
			local.setRange(min, max);
		}
		if (type == Protocol.DEVICE_COMPOSITE)
		{
			int min = readInt();
			int max = readInt();
			local.setComposite(min, max);
		}
		int id = readInt();
		local.setId(id);
		return local;
	}

	private void receiveQueryConfig () { }

	private void receiveQueryDeclare ()
	{
		device = readDeviceDescription();
	}

	private void receiveQueryUndeclare ()
	{
		id = readInt();
	}

	private void receiveQueryInputs () { }

	private void receiveQueryOutputs () { }

	private void receiveQueryMaps () { }

	private void receiveQueryMapClear () { }

	private void receiveQueryMapAdd ()
	{
		from = readInt();
		to = readInt();
	}

	private void receiveQueryMapErase ()
	{
		id = readInt();
	}

	private void receiveReplySuccess () { }

	private void receiveReplyFail () { }

	private void receiveReplyConfig () { }

	private void receiveReplyInputs ()
	{
		int size = readInt();
		devices = new Vector<Device>(size);
		for (int i = 0; i<size; i++)
		{
			Device local = readDeviceDescription();
			devices.add(local);
		}
	}

	private void receiveReplyOutputs ()
	{
		int size = readInt();
		devices = new Vector<Device>(size);
		for (int i = 0; i<size; i++)
		{
			Device local = readDeviceDescription();
			devices.add(local);
		}
	}

	private void receiveReplyMaps ()
	{
		int size = readInt();
		mappings = new Vector<Mapping>(size);
		for (int i = 0; i<size; i++)
		{
			int id = readInt();
			Device from = readDeviceDescription();
			Device to = readDeviceDescription();
			
			Mapping local = new Mapping(id, from, to);
			mappings.add(local);
		}
	}

	public void receive()
	{
		type = readInt ();
		switch (type)
		{
			case Protocol.QUERY_CONFIG : receiveQueryConfig(); break;
			case Protocol.QUERY_DECLARE : receiveQueryDeclare(); break;
			case Protocol.QUERY_UNDECLARE: receiveQueryUndeclare(); break;
			case Protocol.QUERY_INPUTS : receiveQueryInputs(); break;
			case Protocol.QUERY_OUTPUTS : receiveQueryOutputs(); break;
			case Protocol.QUERY_MAPS : receiveQueryMaps(); break;
			case Protocol.QUERY_MAP_CLEAR : receiveQueryMapClear(); break;
			case Protocol.QUERY_MAP_ADD : receiveQueryMapAdd(); break;
			case Protocol.QUERY_MAP_ERASE: receiveQueryMapErase(); break;
			case Protocol.REPLY_SUCCESS: receiveReplySuccess(); break;
			case Protocol.REPLY_FAIL: receiveReplyFail(); break;
			case Protocol.REPLY_CONFIG: receiveReplyConfig(); break;
			case Protocol.REPLY_INPUTS: receiveReplyInputs(); break;
			case Protocol.REPLY_OUTPUTS: receiveReplyOutputs(); break;
			case Protocol.REPLY_MAPS : receiveReplyMaps(); break;
		}
	}

	public int getType()
	{
		return type;
	}
}