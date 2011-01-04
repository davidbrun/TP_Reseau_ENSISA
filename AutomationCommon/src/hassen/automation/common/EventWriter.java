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

	public void createSendEvent (Event event) {
	}
}
