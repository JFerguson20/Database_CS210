package project.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import project.field.AbstractField;
import project.one.MyException;
import project.value.AbstractValue;

public class Row implements Comparable<Row>, Serializable {
	private ArrayList<AbstractValue> values;

	public Row() throws MyException {
		values = new ArrayList<AbstractValue>();
	}

	public void addValue(AbstractValue value) {
		values.add(value);
	}

	public AbstractValue getValueAtIndexInRow(int i) {
		return values.get(i);
	}

	@Override
	public String toString() {
		String rowsString = "";
		for (AbstractValue f : values) {
			rowsString += f.toString() + "     ";
		}
		return rowsString;
	}

	public void combineRow(Row rowB) {
		for (AbstractValue v : rowB.values) {
			addValue(v);
		}
	}

	@Override
	public int compareTo(Row o) {
		int value = 0;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i).getValue().equals(o.values.get(i).getValue())) {

			} else {
				return -1; // return -1 if value is not equal.
			}
		}
		return value;

	}

	public ArrayList<AbstractValue> getValues() {
		return values;
	}

}
