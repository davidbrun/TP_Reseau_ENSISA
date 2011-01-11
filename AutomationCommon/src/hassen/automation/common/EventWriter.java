package hassen.automation.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EventWriter {

	private DatagramSocket socket;
	private Settings settings;
	private ByteArrayOutputStream baos = new ByteArrayOutputStream ();
	private DataOutputStream output = new DataOutputStream (baos);
	
	public EventWriter(DatagramSocket socket, Settings settings) {
		this.socket = socket;
		this.settings = settings;
	}

	private void writeInt (int v) {
		try {
			output.writeInt(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send() {
		byte [] message = baos.toByteArray();
		try {
			InetAddress address = InetAddress.getByName(settings.getMulticastAddress());
			DatagramPacket packet = new DatagramPacket(message, 0, message.length, address, settings.getDataPort()); 
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeEventDescription (Event event)
	{
		if (event.isOnEvent())
		{
			writeInt(Protocol.EVENT_ON);
			writeInt(event.getDeviceID());
		}	
		if (event.isOffEvent())
		{
			writeInt(Protocol.EVENT_OFF);
			writeInt(event.getDeviceID());
		}
		if (event.isNextEvent())
		{
			writeInt(Protocol.EVENT_NEXT);
			writeInt(event.getDeviceID());
		}
		if (event.isPreviousEvent())
		{
			writeInt(Protocol.EVENT_PREVIOUS);
			writeInt(event.getDeviceID());
		}
		if (event.isValueEvent())
		{
			writeInt(Protocol.EVENT_VALUE);
			writeInt(event.getDeviceID());
			writeInt (event.getValue());
		}
		if (event.isZeroEvent())
		{
			writeInt (Protocol.EVENT_ZERO);
			writeInt(event.getDeviceID());
		}
	}
	
	public void createSendEvent (Event event)
	{
		writeInt(Protocol.SEND_EVENT);
		writeEventDescription(event);
	}
}
