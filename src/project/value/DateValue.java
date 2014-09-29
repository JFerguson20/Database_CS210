package project.value;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.one.MyException;

public class DateValue extends AbstractValue {
	private Long value;

	public DateValue(long value) {

		this.value = value;

	}

	public DateValue() {

	}

	public int compareTo(AbstractValue v2) {
		if (v2.getClass().getName().equals("project.value.DateValue")) {
			return value.compareTo((Long) v2.getValue());
		} else {
			return -1;
		}
	}

	public Long getValue() {
		return value;
	}

	public void setValue(String value) throws MyException {

		if (value.charAt(0) != '\'' && value.charAt(value.length() - 1) != '\'') {
			throw new MyException("dates must contain ' ' around them.");
		}
		value = value.substring(1, value.length() - 1);

		try {
			Date date = new SimpleDateFormat("MM/dd/yyyy").parse(value);

			this.value = date.getTime();

		} catch (ParseException e) {
			throw new MyException(
					"The type of value in the where clause does not match the type of field");
		}

	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(new Date(value));

	}

}
