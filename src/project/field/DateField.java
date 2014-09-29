package project.field;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.one.MyException;
import project.value.AbstractValue;
import project.value.DateValue;

/**
 * The Class DateField. A Abstract Field of type Date.
 */
public class DateField extends AbstractField {

	/**
	 * Instantiates a new date field.
	 * 
	 * @param fieldName
	 *            the field name
	 */
	public DateField(String fieldName, int positionInRow) {
		super(fieldName, new DateValue(), "DATE", positionInRow);
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
				if (value.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
					Date date = new SimpleDateFormat("MM/dd/yyyy").parse(value);
					RAF.writeLong(date.getTime());
				} else {
					System.out.print("The date must be in 'MM/dd/yyyy' format");
					throw new MyException();
				}
			} else {
				System.out.print("The date must contain ' ' around the date");
				throw new MyException();

			}

		} catch (IOException e) {
			throw new MyException();
		} catch (ParseException e) {
			throw new MyException();
		}
	}

	@Override
	public DateValue readBinary(RandomAccessFile RAF, int startOfRow)
			throws MyException {

		try {
			RAF.seek(startOfRow + positionInRow);
			return new DateValue(RAF.readLong());
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
			return new DateValue(RAF.readLong());
		} catch (IOException e) {

			System.out
					.println("There was an error reading a value from the file");
			throw new MyException();
		}
	}

	@Override
	public void rewriteBinary(String value, RandomAccessFile RAF, int startOfRow)
			throws MyException {
		value = "'" + value + "'";
		writeBinary(value, RAF, startOfRow);

	}

}
