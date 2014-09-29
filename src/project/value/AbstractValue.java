package project.value;

import java.io.Serializable;

import project.one.MyException;

public abstract class AbstractValue implements Comparable<AbstractValue>,
		Serializable {

	public abstract int compareTo(AbstractValue o1);

	public abstract <T> T getValue();

	public abstract void setValue(String value) throws MyException;

}
