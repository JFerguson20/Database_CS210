package project.value;

import project.one.MyException;

public class BooleanValue extends AbstractValue {
	private Boolean value;

	public BooleanValue(Boolean value) {
		this.value = value;
	}

	public BooleanValue() {

	}

	@Override
	public int compareTo(AbstractValue v2) {
		if (v2.getClass().getName().equals("project.value.BooleanValue")) {
			return value.compareTo((Boolean) v2.getValue());
		} else {
			return -1;
		}
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(String value) throws MyException {
		this.value = Boolean.parseBoolean(value);
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
