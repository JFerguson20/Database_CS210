package project.value;

import project.one.MyException;

public class VarcharValue extends AbstractValue {
	private String value;

	public VarcharValue(String value) {
		this.value = value;
	}

	public VarcharValue() {

	}

	@Override
	public int compareTo(AbstractValue v2) {
		if (v2.getClass().getName().equals("project.value.VarcharValue")) {
			return value.compareTo((String) v2.getValue());
		} else {
			return -1;
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) throws MyException {
		if (value.charAt(0) != '\'' && value.charAt(value.length() - 1) != '\'') {
			throw new MyException("Chars must contain ' ' around them.");
		}
		value = value.substring(1, value.length() - 1); // take off '

		this.value = value;

	}

	@Override
	public String toString() {
		return value;
	}
}
