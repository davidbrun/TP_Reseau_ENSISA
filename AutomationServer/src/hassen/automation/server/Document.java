package hassen.automation.server;

import hassen.automation.common.Device;
import hassen.automation.common.Event;
import hassen.automation.common.IEventProcessor;
import hassen.automation.common.IDeviceManager;
import hassen.automation.common.Mapping;
import hassen.automation.common.SessionClientEvent;
import hassen.automation.common.SessionServerEvent;
import hassen.automation.common.Settings;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Vector;

public class Document implements IDeviceManager, IEventProcessor {

	private Settings settings = new Settings();
	private Vector<Device> devices;
	private Vector<Mapping> mappings;
	private Thread managementServer, eventServer;
	private SessionClientEvent sessionEvent;
	
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public Settings getSettings() {
		return settings;
	}

	public void start() {
		managementServer = new Thread (new ManagementServer(this));
		managementServer.start();
		try {
			MulticastSocket socket = new MulticastSocket(settings.getDataPort());
			socket.joinGroup(InetAddress.getByName(settings.getMulticastAddress()));
			eventServer = new Thread (new SessionServerEvent(socket, this));
			eventServer.start();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized Vector<Device> getDevices() {
		if (devices == null) devices = new Vector <Device> (5);
		return devices;
	}

	@Override
	public synchronized Vector<Mapping> getMappings() {
		if (mappings == null) mappings = new Vector <Mapping> (5);
		return mappings;
	}

	private Device getDevice (int id) {
		for (Device d : getDevices()) {
			if (d.getId() == id) return d;
		}
		return null;
	}

	private Mapping getMapping (int id) {
		for (Mapping m : getMappings()) {
			if (m.getId() == id) return m;
		}
		return null;
	}

	private boolean mappingExists (Mapping mapping) {
		for (Mapping m : getMappings()) {
			if (m.getFrom() != mapping.getFrom()) continue;
			if (m.getTo() != mapping.getTo()) continue;
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean mapAdd(int from, int to) {
		Device fromDevice = getDevice (from);
		if (fromDevice == null) return false;
		Device toDevice = getDevice (to);
		if (toDevice == null) return false;
		Mapping m = new Mapping (fromDevice, toDevice);
		if (mappingExists(m)) return false;
		getMappings().add(m);
		return true;
	}

	@Override
	public synchronized boolean mapClear() {
		getMappings().clear();
		return true;
	}

	@Override
	public synchronized boolean mapErase(int id) {
		Mapping m = getMapping (id);
		if (m == null) return false;
		getMappings().remove(m);
		return true;
	}

	@Override
	public synchronized boolean declare(Device device) {
		if (getDevice(device.getId()) != null) return false;
		getDevices().add(device);
		return true;
	}

	@Override
	public synchronized boolean undeclare(int id) {
		Device device = getDevice(id);
		if (device == null) return false;
		getDevices().remove(device);
		return true;
	}

	public synchronized SessionClientEvent getSessionEvent () {
		if (sessionEvent == null) sessionEvent = new SessionClientEvent(settings);
		return sessionEvent;
	}

	@Override
	public synchronized void processEvent(Event event) {
		for (Mapping m : getMappings()) {
			if (event.getDeviceID() != m.getFrom().getId()) continue;
			int to = m.getTo().getId();
			Event eventTo = new Event ();
			if (event.isOnEvent()) eventTo.setOnEvent(to);
			if (event.isOffEvent()) eventTo.setOffEvent(to);
			if (event.isNextEvent()) eventTo.setNextEvent(to);
			if (event.isPreviousEvent()) eventTo.setPreviousEvent(to);
			if (event.isZeroEvent()) eventTo.setZeroEvent(to);
			if (event.isValueEvent()) eventTo.setValueEvent(to, event.getValue());
			getSessionEvent().sendEvent(eventTo);
		}
	}

}
