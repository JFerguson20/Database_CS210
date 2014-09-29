package project.value;

import project.one.MyException;

public class RealValue extends AbstractValue {
	private Double value;

	public RealValue(double value) {
		this.value = value;
	}

	public RealValue() {

	}

	@Override
	public int compareTo(AbstractValue v2) {
		if (v2.getClass().getName().equals("project.value.RealValue")) {
			return value.compareTo((Double) v2.getValue());
		} else {
			return -1;
		}
	}

	public Double getValue() {
		return value;
	}

	public void setValue(String value) throws MyException {
		try {
			this.value = Double.parseDouble(value);
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
