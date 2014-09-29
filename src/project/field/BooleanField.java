package project.field;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import project.one.MyException;
import project.value.AbstractValue;
import project.value.BooleanValue;

/**
 * The Class BooleanField. A Abstract Field of type Boolean.
 */
public class BooleanField extends AbstractField {

	/**
	 * Instantiates a new boolean field.
	 * 
	 * @param fieldName
	 *            the field name
	 */
	public BooleanField(String fieldName, int positionInRow) {
		super(fieldName, new BooleanValue(), "BOOLEAN", positionInRow);
	}

	@Override
	public void writeBinary(String value, RandomAccessFile RAF, int startOfRow)
			throws MyException {

		try {
			RAF.seek(startOfRow + positionInRow);
			boolean x;
			if (value.equalsIgnoreCase("true")) {
				x = true;
			} else if (value.equalsIgnoreCase("false")) {
				x = false;
			} else {
				System.out
						.print("the value you inserted was not 'true' or 'false'");
				throw new MyException();

			}
			RAF.writeBoolean(x);
		} catch (IOException e) {
			System.out.print("There was an error inserting the boolean value");
			throw new MyException();
		}

	}

	@Override
	public BooleanValue readBinary(RandomAccessFile RAF, int startOfRow)
			throws MyException {

		try {
			RAF.seek(startOfRow + positionInRow);

			return new BooleanValue(RAF.readBoolean());
		} catch (IOException e) {
			// should never get this exception.
			System.out
					.println("There was an error reading a value from the file");
			throw new MyException();
		}

	}

	@Override
	public BooleanValue readBinary(RandomAccessFile RAF, Long position) throws MyException {
		try {
			RAF.seek(position);

			return new BooleanValue(RAF.readBoolean());
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
