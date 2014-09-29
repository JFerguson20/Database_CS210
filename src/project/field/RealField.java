package project.field;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import project.one.MyException;
import project.value.AbstractValue;
import project.value.RealValue;

/**
 * The Class RealField. A Abstract Field of type Real.
 */
public class RealField extends AbstractField {

	/**
	 * Instantiates a new real field.
	 * 
	 * @param fieldName
	 *            the field name
	 */
	public RealField(String fieldName, int positionInRow) {
		super(fieldName, new RealValue(), "REAL", positionInRow);

	}

	@Override
	public void writeBinary(String value, RandomAccessFile RAF, int startOfRow)
			throws MyException {

		try {
			RAF.seek(startOfRow + positionInRow);
			RAF.writeDouble(Double.parseDouble(value));
		} catch (IOException e) {
			System.out.print("There was an error inserting the real value");
			throw new MyException();
		} catch (NumberFormatException e) {
			System.out.print("The real you tried to insert wasnt an number!");
			throw new MyException();
		}

	}

	@Override
	public RealValue readBinary(RandomAccessFile RAF, int startOfRow)
			throws MyException {

		try {
			RAF.seek(startOfRow + positionInRow);
			return new RealValue(RAF.readDouble());
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
			return new RealValue(RAF.readDouble());
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
