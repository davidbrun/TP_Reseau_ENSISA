package hassen.automation.common;

public interface Protocol {

	public static final String MULTICAST_ADDRESS = "239.239.239.239";
	static final public int AUTOMATION_CTRLPORT_ID 		= 10101;
	static final public int AUTOMATION_DATAPORT_ID 		= 10102;
	public static final int AUTOMATION_MAX_DATA_SIZE 	= 15;

	static final public int QUERY_START		= 0x0000;
	static final public int QUERY_CONFIG	= QUERY_START + 1;
	static final public int QUERY_DECLARE	= QUERY_START + 2;
	static final public int QUERY_UNDECLARE = QUERY_START + 3;
	static final public int QUERY_INPUTS	= QUERY_START + 4;
	static final public int QUERY_OUTPUTS	= QUERY_START + 5;
	static final public int QUERY_MAPS		= QUERY_START + 6;
	static final public int QUERY_MAP_CLEAR	= QUERY_START + 7;
	static final public int QUERY_MAP_ADD	= QUERY_START + 8;
	static final public int QUERY_MAP_ERASE	= QUERY_START + 9;
	static final public int SEND_EVENT		= QUERY_START +10;

	static final public int REPLY_START		= 0x8000;
	static final public int REPLY_SUCCESS	= REPLY_START + 1;
	static final public int REPLY_FAIL		= REPLY_START + 2;
	static final public int REPLY_CONFIG	= REPLY_START + 3;
	static final public int REPLY_INPUTS	= REPLY_START + 4;
	static final public int REPLY_OUTPUTS	= REPLY_START + 5;
	static final public int REPLY_MAPS		= REPLY_START + 6;

	static final public int DEVICE_INPUT 	= 0x000001;
	static final public int DEVICE_OUTPUT	= 0x000002;

	static final public int DEVICE_BINARY 	= 0x000100;
	static final public int DEVICE_EVENT 	= 0x000200;
	static final public int DEVICE_RANGE 	= 0x000300;
	static final public int DEVICE_COMPOSITE= 0x000400;

	static final public int EVENT_ON		= 0x010000;
	static final public int EVENT_OFF		= 0x020000;
	static final public int EVENT_NEXT		= 0x030000;
	static final public int EVENT_PREVIOUS	= 0x040000;
	static final public int EVENT_ZERO		= 0x050000;
	static final public int EVENT_VALUE		= 0x060000;
}
