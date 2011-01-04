package hassen.automation.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class EventReader {

	private DatagramSocket socket;
	private DataInputStream inputStream;
	private int type;
	private Event event;
	
	public EventReader(DatagramSocket socket) {
		this.socket = socket;
	}

	public Event getEvent() {
		return event;
	}

	private int readInt () {
		try {
			return inputStream.readInt();
		} catch (IOException e) {
			return 0;
		}
	}

	private void receiveSendEvent () {
		event = new Event ();
	}

	public void receive() {
		try {
			byte message [] = new byte [Protocol.AUTOMATION_MAX_DATA_SIZE];
			DatagramPacket packet = new DatagramPacket(message, message.length);
			socket.receive(packet);
			ByteArrayInputStream bais = new ByteArrayInputStream(message);
			this.inputStream = new DataInputStream (bais);
			type = readInt ();
			switch (type) {
			case Protocol.SEND_EVENT : receiveSendEvent(); break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getType() {
		return type;
	}

}
