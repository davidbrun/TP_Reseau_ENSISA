package hassen.automation.device;

import hassen.automation.common.Device;
import hassen.automation.common.Event;
import hassen.automation.common.SessionClientEvent;
import hassen.automation.common.SessionDevice;
import hassen.automation.common.Settings;

public class Document {

	private Settings settings = new Settings();
	private Device device;
	private SessionDevice sessionDevice;
	private SessionClientEvent sessionEvent;

	public Settings getSettings() {
		return settings;
	}
	
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public SessionDevice getSessionDevice () {
		if (sessionDevice == null) sessionDevice = new SessionDevice(settings);
		return sessionDevice;
	}

	public SessionClientEvent getSessionEvent () {
		if (sessionEvent == null) sessionEvent = new SessionClientEvent(settings);
		return sessionEvent;
	}

	public void declare() {
		getSessionDevice().declare(device);
	}

	public void undeclare() {
		getSessionDevice().undeclare(device);
	}

	public void send(Event event) {
		getSessionEvent().sendEvent(event);
	}

	private boolean checkEvent (Event event) {
		if (getDevice().isBinary()) {
			if (event.isOnEvent() || event.isOffEvent()) {
				return true;
			}
		} else return false;
		if (getDevice().isEvent()) {
			if (event.isNextEvent() || event.isPreviousEvent() || event.isZeroEvent()) {
				return true;
			}
		} else return false;
		if (getDevice().isRange()) {
			if (event.isValueEvent()) {
				return true;
			}
		} else return false;
		if (getDevice().isComposite()) {
			return true;
		} else return false;
	}

	public void start() {
		if (getDevice().isOutput()) {
			while (true) {
				Event event = getSessionEvent().receiveEvent ();
				if (event == null) return;
				if (event.getDeviceID() != getDevice().getId()) continue;
				boolean goodEvent = checkEvent (event);
				if (goodEvent) {
					System.out.print("Receive adapted event : ");
				} else {
					System.out.print("Receive wrong event : ");
				}
				System.out.print(event.toString());
				System.out.println ();
			}
		}
	}

}
