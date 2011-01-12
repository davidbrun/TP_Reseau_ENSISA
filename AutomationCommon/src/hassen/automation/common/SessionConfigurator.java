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

	public Vector<Device> getInputs ()
	{
		try
		{
			connect();
			ManagementWriter w = new ManagementWriter(connection.getOutputStream());
			w.createQueryInputs();
			w.send();
			ManagementReader r = new ManagementReader(connection.getInputStream());
			r.receive();
			if (r.getType() == Protocol.REPLY_INPUTS)
				return r.getDevices();
			else
				return null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	public Vector<Device> getOutputs ()
	{
		try
		{
			connect();
			ManagementWriter w = new ManagementWriter(connection.getOutputStream());
			w.createQueryOutputs();
			w.send();
			ManagementReader r = new ManagementReader(connection.getInputStream());
			r.receive();
			if (r.getType() == Protocol.REPLY_OUTPUTS)
				return r.getDevices();
			else
				return null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	public Vector<Mapping> getMaps ()
	{
		try
		{
			connect();
			ManagementWriter w = new ManagementWriter(connection.getOutputStream());
			w.createQueryMaps();
			w.send();
			ManagementReader r = new ManagementReader(connection.getInputStream());
			r.receive();
			if (r.getType() == Protocol.REPLY_SUCCESS)
				return r.getMappings();
			else
				return null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	public boolean clearMaps () {
		try
		{
			connect();
			ManagementWriter w = new ManagementWriter(connection.getOutputStream());
			w.createQueryMapClear();
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

	public boolean eraseMap (int id)
	{		
		try
		{
			connect();
			ManagementWriter w = new ManagementWriter(connection.getOutputStream());
			w.createQueryMapErase(id);
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

	public boolean addMap (Mapping mapping)
	{
		try
		{
			connect();
			ManagementWriter w = new ManagementWriter(connection.getOutputStream());
			w.createQueryMapAdd(mapping);
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