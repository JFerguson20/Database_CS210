package project.field;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.one.MyException;
import project.value.AbstractValue;
import project.value.VarcharValue;

/**
 * The Class VarcharField. A Abstract Field of type VarChar.
 */
public class VarcharField extends AbstractField {

	/**
	 * Instantiates a new varchar field.
	 * 
	 * @param fieldName
	 *            the field name
	 */
	public VarcharField(String fieldName, int positionInRow) {
		super(fieldName, new VarcharValue(), "VARCHAR", positionInRow);

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
				RandomAccessFile varcharFile = new RandomAccessFile(
						"TableInfo/InsertedData/Varchars.bin", "rw");
				varcharFile.seek(varcharFile.length()); // go to end of file
				RAF.writeLong(varcharFile.getFilePointer()); // write position
																// in RAF of
																// string in
																// varchars.bin
				varcharFile.writeUTF(value);
				varcharFile.close();
			} else {
				System.out.print("Varchars must contain ' ' around them.");
				throw new MyException();
			}

		} catch (IOException e) {
			System.out.print("There was a error inserted the varchar field");
			throw new MyException();
		}
	}

	@Override
	public VarcharValue readBinary(RandomAccessFile RAF, int startOfRow)
			throws MyException {

		try {
			RAF.seek(startOfRow + positionInRow);
			long pos = RAF.readLong();
			RandomAccessFile varcharFile = new RandomAccessFile(
					"TableInfo/InsertedData/Varchars.bin", "r");
			varcharFile.seek(pos);
			String varchar = varcharFile.readUTF();
			varcharFile.close();
			return new VarcharValue(varchar);
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
			long pos = RAF.readLong();
			RandomAccessFile varcharFile = new RandomAccessFile(
					"TableInfo/InsertedData/Varchars.bin", "r");
			varcharFile.seek(pos);
			String varchar = varcharFile.readUTF();
			varcharFile.close();
			return new VarcharValue(varchar);
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
