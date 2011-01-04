package hassen.automation.common;

public class Event {

	private int id;
	private int deviceID;
	private int event;
	private int value;
	static int count = 0;
	
	public Event() {
		super();
		this.id = ++count;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public boolean isOnEvent() {
		return event == EVENT_ON;
	}

	public boolean isOffEvent() {
		return event == EVENT_OFF;
	}

	public boolean isNextEvent() {
		return event == EVENT_NEXT;
	}

	public boolean isPreviousEvent() {
		return event == EVENT_PREVIOUS;
	}

	public boolean isZeroEvent() {
		return event == EVENT_ZERO;
	}

	public boolean isValueEvent() {
		return event == EVENT_VALUE;
	}

	public void setOnEvent(int deviceID) {
		event = EVENT_ON;
		this.deviceID = deviceID;
	}

	public void setOffEvent(int deviceID) {
		event = EVENT_OFF;
		this.deviceID = deviceID;
	}

	public void setNextEvent(int deviceID) {
		event = EVENT_NEXT;
		this.deviceID = deviceID;
	}

	public void setPreviousEvent(int deviceID) {
		event = EVENT_PREVIOUS;
		this.deviceID = deviceID;
	}

	public void setZeroEvent(int deviceID) {
		event = EVENT_ZERO;
		this.deviceID = deviceID;
	}

	public void setValueEvent(int deviceID, int value) {
		event = EVENT_VALUE;
		this.deviceID = deviceID;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String toString () {
		StringBuffer tmp = new StringBuffer ();
		tmp.append("Event #");
		tmp.append(id);
		tmp.append(" on ");
		tmp.append(deviceID);
		if (isOnEvent()) tmp.append(" On");
		if (isOffEvent()) tmp.append(" Off");
		if (isNextEvent()) tmp.append(" Next");
		if (isPreviousEvent()) tmp.append(" Previous");
		if (isZeroEvent()) tmp.append(" Zero");
		if (isValueEvent()) {
			tmp.append(" ");
			tmp.append(value);
		}
		return tmp.toString();
	}

	static private int EVENT_ON			= 0x01;
	static private int EVENT_OFF		= 0x02;
	static private int EVENT_NEXT		= 0x03;
	static private int EVENT_PREVIOUS	= 0x04;
	static private int EVENT_ZERO		= 0x05;
	static private int EVENT_VALUE		= 0x06;

}
