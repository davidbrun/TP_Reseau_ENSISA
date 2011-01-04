package hassen.automation.common;

import java.util.Vector;

public interface IDeviceManager {

	public abstract boolean mapAdd(int from, int to);

	public abstract boolean mapErase(int id);

	public abstract boolean mapClear();

	public abstract Vector<Mapping> getMappings();

	public abstract Vector<Device> getDevices();

	public abstract boolean undeclare(int id);

	public abstract boolean declare(Device device);

	public abstract Settings getSettings();
}