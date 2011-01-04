package hassen.automation.common;

public class Device {

	private int kind;
	private int type;
	private int id;
	private int min, max;

	public boolean isInput() {
		return kind == KIND_INPUT;
	}
	public boolean isOutput() {
		return kind == KIND_OUTPUT;
	}
	
	public void setInput() {
		this.kind = KIND_INPUT;
	}
	public void setOutput() {
		this.kind = KIND_OUTPUT;
	}

	public boolean isBinary() {
		return type == TYPE_BINARY;
	}
	public boolean isEvent() {
		return type == TYPE_EVENT;
	}
	public boolean isRange() {
		return type == TYPE_RANGE;
	}
	public boolean isComposite() {
		return type == TYPE_COMPOSITE;
	}

	public void setBinary() {
		this.type = TYPE_BINARY;
	}
	public void setEvent() {
		this.type = TYPE_EVENT;
	}
	public void setRange(int min, int max) {
		this.type = TYPE_RANGE;
		this.min = min;
		this.max = max;
	}
	public void setComposite(int min, int max) {
		this.type = TYPE_COMPOSITE;
		this.min = min;
		this.max = max;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getMin() {
		return min;
	}
	public int getMax() {
		return max;
	}

	public String toString () {
		StringBuffer tmp = new StringBuffer ();
		if (isInput()) tmp.append("Input ");
		if (isOutput()) tmp.append("Output");
		tmp.append(" #");
		tmp.append(getId());
		if (isBinary()) tmp.append(" B");
		if (isEvent()) tmp.append(" E");
		if (isRange()) {
			tmp.append(" R");
			tmp.append("[");
			tmp.append(getMin());
			tmp.append(", ");
			tmp.append(getMax());
			tmp.append("]");
		}
		if (isComposite()) {
			tmp.append(" C");
			tmp.append("[");
			tmp.append(getMin());
			tmp.append(", ");
			tmp.append(getMax());
			tmp.append("]");
		}
		return tmp.toString();
	}
	
	static private int KIND_INPUT		= 0x01;
	static private int KIND_OUTPUT		= 0x02;

	static private int TYPE_BINARY		= 0x01;
	static private int TYPE_EVENT		= 0x02;
	static private int TYPE_RANGE 		= 0x03;
	static private int TYPE_COMPOSITE	= 0x04;
}
