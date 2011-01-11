package hassen.automation.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class ManagementWriter {

	private OutputStream outputStream;
	private ByteArrayOutputStream baos = new ByteArrayOutputStream ();
	private DataOutputStream output = new DataOutputStream (baos);
	
	public ManagementWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	private void writeInt (int v) {
		try {
			output.writeInt(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	private void writeString (String v) {
		try {
			output.writeUTF(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	private void writeDeviceDescription (Device device) {
		if (device.isInput()) writeInt(Protocol.DEVICE_INPUT);
		if (device.isOutput()) writeInt(Protocol.DEVICE_OUTPUT);
		if (device.isBinary()) writeInt(Protocol.DEVICE_BINARY);
		if (device.isEvent()) writeInt(Protocol.DEVICE_EVENT);
		if (device.isRange())
		{
			writeInt(Protocol.DEVICE_RANGE);
			writeInt(device.getMin());
			writeInt(device.getMax());
		}
		if (device.isComposite())
		{
			writeInt(Protocol.DEVICE_COMPOSITE);
			writeInt(device.getMin());
			writeInt(device.getMax());
		}
		writeInt(device.getId());
	}

	public void send() {
		byte [] message = baos.toByteArray();
		try {
			outputStream.write(message);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createQueryConfig()
	{
		writeInt(Protocol.QUERY_CONFIG);
	}

	public void createQueryDeclare(Device device)
	{
		writeInt(Protocol.QUERY_DECLARE);
		writeDeviceDescription(device);
	}

	public void createQueryUndeclare(Device device)
	{
		writeInt(Protocol.QUERY_UNDECLARE);
		writeInt(device.getId());
	}
	
	public void createQueryInputs ()
	{
		writeInt(Protocol.QUERY_INPUTS);
	}
	
	public void createQueryOutputs ()
	{
		writeInt(Protocol.QUERY_OUTPUTS);
	}
	
	public void createQueryMaps ()
	{
		writeInt(Protocol.QUERY_MAPS);
	}
	
	public void createQueryMapClear ()
	{
		writeInt(Protocol.QUERY_MAP_CLEAR);
	}
	
	public void createQueryMapAdd (Mapping mapping)
	{
		writeInt(Protocol.QUERY_MAP_ADD);
		writeInt (mapping.getFrom().getId());
		writeInt (mapping.getTo().getId());	
	}
	
	public void createQueryMapErase(int id)
	{
		writeInt(Protocol.QUERY_MAP_ERASE);
		writeInt(id);
	}
	
	public void createReplySuccess ()
	{
		writeInt(Protocol.REPLY_SUCCESS);
	}
	
	public void createReplyFail ()
	{
		writeInt(Protocol.REPLY_FAIL);
	}
	
	public void createReplyInputs(Vector<Device> inputs)
	{
		writeInt(Protocol.REPLY_INPUTS);
		writeInt(inputs.size());
		for (Device d : inputs)
		{
			writeDeviceDescription(d);
		}
	}
	
	public void createReplyOutputs(Vector<Device> outputs)
	{
		writeInt(Protocol.REPLY_OUTPUTS);
		writeInt(outputs.size());
		for (Device d : outputs)
		{
			writeDeviceDescription(d);
		}
	}
	
	public void createReplyMaps(Vector<Mapping> maps)
	{
		writeInt(Protocol.REPLY_MAPS);
		writeInt(maps.size());
		for (Mapping m : maps)
		{
			writeInt(m.getId());
			writeDeviceDescription(m.getFrom());
			writeDeviceDescription(m.getTo());
		}
	}
	
	public void createReplyConfig (Settings settings) {
	}
	
}