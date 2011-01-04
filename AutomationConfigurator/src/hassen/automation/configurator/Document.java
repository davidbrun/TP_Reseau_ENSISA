package hassen.automation.configurator;

import hassen.automation.common.Device;
import hassen.automation.common.SessionDevice;
import hassen.automation.common.Settings;

public class Document {

	private Settings settings = new Settings();
	private Device device;
	private SessionDevice sessionDevice;

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

	public void declare() {
		getSessionDevice().declare(device);
	}

	public void undeclare() {
		getSessionDevice().undeclare(device);
	}

	public void start() {
	}

}
