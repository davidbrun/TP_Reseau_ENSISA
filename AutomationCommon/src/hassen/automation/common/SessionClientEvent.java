package hassen.automation.common;

import java.net.InetAddress;
import java.net.MulticastSocket;

public class SessionClientEvent {

	private Settings settings;
	private MulticastSocket connection;
	private boolean allreadyTried;
	
	public SessionClientEvent (Settings settings) {
		this.settings = settings;
		this.connection = null;
		this.allreadyTried = false;
	}

	public void sendEvent (Event event)
	{
		try
		{
			connect();
			EventWriter ew = new EventWriter(connection, settings);
			ew.createSendEvent(event);
			ew.send();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Event receiveEvent ()
	{
		try
		{
			connect();
			EventReader er = new EventReader(connection);
			er.receive();
			return er.getEvent();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void connect () throws Exception {
		if (connection != null) disconnect();
		if (allreadyTried) {
			throw new Exception ("AllReady tried");
		} else {
			allreadyTried = true;
			connection = new MulticastSocket(settings.getDataPort());
			connection.joinGroup(InetAddress.getByName(settings.getMulticastAddress()));
		}
	}

	public void disconnect () throws Exception {
		if (connection != null) connection.close();
		allreadyTried = false;
		connection = null;
	}

}
