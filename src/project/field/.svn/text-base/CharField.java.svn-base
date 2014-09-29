package project.field;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import project.one.MyException;
import project.value.AbstractValue;
import project.value.BooleanValue;
import project.value.CharValue;

/**
 * The Class CharField. A Abstract Field of type Char.
 */
public class CharField extends AbstractField {

	private int charSize;

	/**
	 * Instantiates a new char field.
	 * 
	 * @param fieldName
	 *            the field name
	 * @param charSize
	 *            the char size
	 */
	public CharField(String fieldName, int charSize, int positionInRow) {
		super(fieldName, new CharValue(), "CHAR", positionInRow);
		this.charSize = charSize;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.field.AbstractField#toString()
	 */
	@Override
	public String toString() {
		return fieldName + " " + dataType + "(" + charSize + ")";
	}

	@Override
	public void writeBinary(String value, RandomAccessFile RAF, int startOfRow)
			throws MyException {

		try {
			RAF.seek(startOfRow + positionInRow);

			if (value.charAt(0) == '\''
					&& value.charAt(value.length() - 1) == '\'') { // check if
																	// it is
																	// surrounded
																	// by ''
				value = value.substring(1, value.length() - 1);
				char[] x = value.toCharArray();

				if (x.length != charSize) {
					System.out
							.print("There is not a correct number of characters");
					throw new MyException();
				}

				for (char c : x) {
					if (!Character.isLetter(c)) {
						System.out
								.print("Your char did not contain only letters");
						throw new MyException();
					}
					RAF.writeChar(c);
				}
			} else {
				System.out.print("Chars must contain ' ' around them.");
				throw new MyException();
			}

		} catch (IOException e) {
			System.out.print("There was an error inserting the char value");
			throw new MyException();
		}

	}

	@Override
	public CharValue readBinary(RandomAccessFile RAF, int startOfRow)
			throws MyException {
		String value = "";
		try {
			RAF.seek(startOfRow + positionInRow);
			for (int i = 0; i < charSize; i++) {
				value += RAF.readChar();
			}
			return new CharValue(value);
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
			String value = "";
			for (int i = 0; i < charSize; i++) {
				value += RAF.readChar();
			}
			return new CharValue(value);
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
		value = "'"+ value+"'";
		writeBinary(value,RAF,startOfRow);
		
	}
}
