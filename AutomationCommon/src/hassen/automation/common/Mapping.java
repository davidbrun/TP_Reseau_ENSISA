package hassen.automation.common;

public class Mapping {

	private int id;
	private Device from;
	private Device to;

	public Mapping(Device from, Device to) {
		this (lastID++, from, to);
	}

	public Mapping(int id, Device from, Device to) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
	}

	public int getId() {
		return id;
	}

	public Device getFrom() {
		return from;
	}

	public Device getTo() {
		return to;
	}

	public String toString () {
		StringBuffer tmp = new StringBuffer ();
		tmp.append("Mapping #");
		tmp.append(getId());
		tmp.append(from.toString());
		tmp.append(" -> ");
		tmp.append(to.toString());
		return tmp.toString();
	}

	static int lastID = 1;
}
