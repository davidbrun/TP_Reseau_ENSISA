package hassen.automation.common;

import java.net.DatagramSocket;

public class SessionServerEvent implements Runnable {

	private IEventProcessor processor;
	private DatagramSocket connection;
	private Thread thread;
	
	public SessionServerEvent (DatagramSocket connection, IEventProcessor processor) {
		this.connection = connection;
		this.processor = processor;
	}

	public void startSession() {
		thread = new Thread (this);
		thread.start();
	}

	@Override
	public void run() {
		boolean loop = true;
		EventReader reader = new EventReader (connection);
		while (loop) {
			reader.receive ();
			process (reader);
		}
	}

	private void process(EventReader reader) {
		switch (reader.getType()) {
		case Protocol.SEND_EVENT:
			processSendEvent(reader.getEvent());
			break;
		default :
			processError ();
			break;
		}
	}

	private void processSendEvent(Event event) {
		processor.processEvent(event);
	}

	private void processError() {
	}

}
