package project.field;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

import project.one.MyException;
import project.value.*;

public abstract class AbstractField implements Serializable {

	protected String fieldName;
	protected AbstractValue valueType;
	protected String dataType;
	protected int positionInRow;

	/**
	 * Instantiates a new abstract field.
	 * 
	 * @param fieldName
	 *            the field name
	 */
	public AbstractField(String fieldName, AbstractValue valueType,
			String dataType, int positionInRow) {
		this.fieldName = fieldName;
		this.valueType = valueType;
		this.dataType = dataType;
		this.positionInRow = positionInRow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return fieldName + " " + dataType;
	}

	/**
	 * Gets the field name.
	 * 
	 * @return the Field Name
	 */
	public String getFieldName() {
		return fieldName;
	}

	public AbstractValue getValueType() {
		return valueType;
	}

	public String getDataType() {
		return dataType;
	}

	public int getPositionInRow() {
		return positionInRow;
	}

	/**
	 * Writes a value to a file in binary
	 * 
	 * @param value
	 *            you are writing to the binary file
	 * @param RAF
	 *            the RandomAccessFile to write the value to
	 * @throws MyException
	 *             if was unsuccessful inserting value
	 */
	public abstract void writeBinary(String value, RandomAccessFile RAF,
			int startOfRow) throws MyException;
	
	public abstract void rewriteBinary(String value, RandomAccessFile RAF, int startOfRow) throws MyException;

	/**
	 * Reads a binary value from a file
	 * 
	 * @param RAF
	 *            the RandomAccessFile to read the value from
	 * @return Returns the field in what every type of Object the field is
	 * @throws MyException
	 */
	public abstract AbstractValue readBinary(RandomAccessFile RAF,
			int startOfRow) throws MyException;

	public abstract AbstractValue readBinary(RandomAccessFile RAF, Long position) throws MyException;

}
