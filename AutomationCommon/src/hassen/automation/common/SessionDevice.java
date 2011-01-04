package hassen.automation.common;

import java.net.Socket;

public class SessionDevice
{
	private Settings settings;
	private Socket connection;
	private boolean allreadyTried;
	
	public SessionDevice (Settings settings)
	{
		this.settings = settings;
		this.connection = null;
		this.allreadyTried = false;
	}

	public Settings config (Device device) {
		return null;
	}

	public boolean declare (Device device)
	{
		try
		{
			connect();
			ManagementWriter w = new ManagementWriter(connection.getOutputStream());
			w.createQueryDeclare(device);
			w.send();
			ManagementReader r = new ManagementReader(connection.getInputStream());
			r.receive();
			if (r.getType() == Protocol.REPLY_SUCCESS)
				return true;
			else
				return false;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean undeclare (Device device)
	{
		try
		{
			//connect();
			ManagementWriter w = new ManagementWriter(connection.getOutputStream());
			w.createQueryUndeclare(device);
			w.send();
			ManagementReader r = new ManagementReader(connection.getInputStream());
			r.receive();
			if (r.getType() == Protocol.REPLY_SUCCESS)
				return true;
			else
				return false;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public void connect () throws Exception
	{
		if (connection != null) disconnect();
		if (allreadyTried) {
			throw new Exception ("AllReady tried");
		} else {
			allreadyTried = true;
			connection = new Socket (settings.getServerName(), settings.getCtrlPort());
		}
	}

	public void disconnect () throws Exception
	{
		if (connection != null) connection.close();
		allreadyTried = false;
		connection = null;
	}
}