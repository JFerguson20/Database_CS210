package project.field;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import project.one.MyException;
import project.value.AbstractValue;
import project.value.IntegerValue;

/**
 * The Class IntegerField. A Abstract Field of type Integer.
 */
public class IntegerField extends AbstractField {

	/**
	 * Instantiates a new integer field.
	 * 
	 * @param fieldName
	 *            the field name
	 */
	public IntegerField(String fieldName, int positionInRow) {
		super(fieldName, new IntegerValue(), "INTEGER", positionInRow);
	}

	@Override
	public void writeBinary(String value, RandomAccessFile RAF, int startOfRow)
			throws MyException {

		try {
			RAF.seek(startOfRow + positionInRow);
			RAF.writeInt(Integer.parseInt(value));
		} catch (IOException e) {
			System.out.print("There was an error inserting the integer value");
			throw new MyException();
		} catch (NumberFormatException e) {
			System.out
					.print("The integer you tried to insert wasnt an integer!");
			throw new MyException();
		}

	}

	@Override
	public IntegerValue readBinary(RandomAccessFile RAF, int startOfRow)
			throws MyException {

		try {
			RAF.seek(startOfRow + positionInRow);
			return new IntegerValue(RAF.readInt());
		} catch (IOException e) {
			// should never get this exception.
			System.out
					.println("There was an error reading a value from the file");
			throw new MyException();
		}

	}

	@Override
	public AbstractValue readBinary(RandomAccessFile RAF, Long position)
			throws MyException {
		try {
			RAF.seek(position);
			return new IntegerValue(RAF.readInt());
		} catch (IOException e) {
			// should never get this exception.
			System.out
					.println("There was an error reading a value from the file");
			throw new MyException();
		}
	}
	
	@Override
	public void rewriteBinary(String value, RandomAccessFile RAF, int startOfRow)
			throws MyException {
		writeBinary(value, RAF, startOfRow);
		
	}

}
