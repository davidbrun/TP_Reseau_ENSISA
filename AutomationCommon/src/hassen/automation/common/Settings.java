package hassen.automation.common;

public class Settings {

	private String serverName;
	private int ctrlPort;
	private String multicastAddress;
	private int dataPort;
	
	public Settings() {
		super();
		this.serverName = "localhost";
		this.ctrlPort = Protocol.AUTOMATION_CTRLPORT_ID;
		this.multicastAddress = Protocol.MULTICAST_ADDRESS;
		this.dataPort = Protocol.AUTOMATION_DATAPORT_ID;
	}

	public Settings(String serverName, int ctrlPort, String multicastAddress, int dataPort) {
		super();
		this.serverName = serverName;
		this.ctrlPort = ctrlPort;
		this.multicastAddress = multicastAddress;
		this.dataPort = dataPort;
	}

	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public int getCtrlPort() {
		return ctrlPort;
	}
	public void setCtrlPort(int ctrlPort) {
		this.ctrlPort = ctrlPort;
	}
	public String getMulticastAddress() {
		return multicastAddress;
	}

	public void setMulticastAddress(String multicastAddress) {
		this.multicastAddress = multicastAddress;
	}

	public int getDataPort() {
		return dataPort;
	}
	public void setDataPort(int dataPort) {
		this.dataPort = dataPort;
	}

}
