package hassen.automation.server;

import hassen.automation.common.IDeviceManager;
import hassen.automation.common.SessionServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ManagementServer implements Runnable {

	private IDeviceManager deviceManager;

	public ManagementServer(IDeviceManager deviceManager) {
		super();
		this.deviceManager = deviceManager;
	}

	@Override
	public void run() {
		try {
			ServerSocket deviceServer = new ServerSocket(deviceManager.getSettings().getCtrlPort());
			while (true) {
				Socket client = deviceServer.accept();
				Thread managementSocket = new Thread (new SessionServer(client, deviceManager));
				managementSocket.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
