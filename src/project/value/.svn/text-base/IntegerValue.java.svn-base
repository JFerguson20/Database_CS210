package project.value;

import project.one.MyException;

public class IntegerValue extends AbstractValue {
	private Integer value;

	public IntegerValue(int value) {
		this.value = value;
	}

	public IntegerValue() {

	}

	@Override
	public int compareTo(AbstractValue v2) {
		if (v2.getClass().getName().equals("project.value.IntegerValue")) {

			return value.compareTo((Integer) v2.getValue());
		} else {

			return -1;
		}
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(String value) throws MyException {
		try {
			this.value = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new MyException(
					"The type of value in the where clause does not match the type of field");
		}

	}

	@Override
	public String toString() {
		return value.toString();
	}
}
