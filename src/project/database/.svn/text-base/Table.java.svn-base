package project.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.collections.Dataset;
import project.collections.Row;
import project.field.*;
import project.one.MyException;
import project.value.AbstractValue;

/**
 * The Class Table.
 */
public class Table implements RelationalOperations, Serializable {

	private String tableName;
	private int rowLength = 1;
	private ArrayList<AbstractField> fields;
	private String fieldName = null; // for where clauses
	private String relop = null; // for where clauses
	private String value = null; // for where clauses
	private Dataset serDataset;

	/**
	 * Creates a new table, and fields with a field list Used from define table
	 * command
	 * 
	 * @param tableName
	 *            the table name
	 * @throws MyException
	 */
	public Table(String tableName, String fieldList) throws MyException {
		this(tableName);
		addFields(fieldList);

	}

	/**
	 * Creates a new table taking one field/data type at a time Only used in SAX
	 * 
	 * @param tableName
	 * @throws MyException
	 */
	public Table(String tableName) throws MyException {
		this.tableName = tableName;
		fields = new ArrayList<AbstractField>();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String tableString = tableName + ": ";

		for (AbstractField f : fields) {

			tableString += f.toString() + ", ";

		}

		return tableString;
	}

	/**
	 * creates a field in the table.
	 * 
	 * @param fieldName
	 * @param dataType
	 * @throws MyException
	 */
	public void createField(String fieldName, String dataType)
			throws MyException {

		dataType = dataType.toUpperCase();

		// check if duplicate names.
		for (AbstractField f : fields) {
			if (f.getFieldName().equals(fieldName)) {
				throw new MyException("Fields contained duplicates");
			}
		}

		if (!fieldName.matches("^[a-zA-Z]+$")) {
			throw new MyException("Field Name " + fieldName
					+ " must contain only letters");
		}

		if (dataType.equals("INTEGER")) {
			fields.add(new IntegerField(fieldName, rowLength));
			rowLength += 4;
		} else if (dataType.equals("DATE")) {
			fields.add(new DateField(fieldName, rowLength));
			rowLength += 8;
		} else if (dataType.equals("REAL")) {
			fields.add(new RealField(fieldName, rowLength));
			rowLength += 8;
		} else if (dataType.equals("VARCHAR")) {
			fields.add(new VarcharField(fieldName, rowLength));
			rowLength += 8;
		} else if (dataType.equals("BOOLEAN")) {
			fields.add(new BooleanField(fieldName, rowLength));
			rowLength += 1;
		} else if (dataType.contains("CHAR")) {
			Pattern pattern = Pattern.compile("char\\s*\\((\\s*+\\d+\\s*)\\)",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(dataType);
			if (matcher.find()) {
				String charS = matcher.group(1);
				charS = charS.trim();
				int charSize = Integer.parseInt(charS);
				fields.add(new CharField(fieldName, charSize, rowLength));
				rowLength += charSize * 2;
			}
		} else {
			throw new MyException("Data Type " + dataType + " is invalid.");
		}

	}

	private void addFields(String fieldList) throws MyException {
		String[] fieldArray = new String[2];
		if (fieldList.charAt(0) == '('
				&& fieldList.charAt(fieldList.length() - 1) == ')') {
			fieldList = fieldList.substring(1, fieldList.length() - 1); // takes
																		// off
																		// the (
																		// and )
		} else {
			throw new MyException(
					"Grammar is invalid, you need () around the field list");
		}
		for (String s : fieldList.split(",")) {
			fieldArray = s.trim().split(" ", 2);
			try {
				createField(fieldArray[0].trim(), fieldArray[1].trim());
			} catch (ArrayIndexOutOfBoundsException e) {

				throw new MyException("Invalid field grammar", e);
			}
		}

	}

	protected void setTableName(String newName) {
		tableName = newName;
	}

	private AbstractField getField(String fieldName) {

		for (AbstractField s : fields) {
			if (s.getFieldName().equals(fieldName)) {
				return s;
			}
		}
		return null;
	}

	private boolean fieldNameExists(String fieldName) {

		for (AbstractField s : fields) {
			if (s.getFieldName().equals(fieldName)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkWhere(String fieldName, String relop,
			AbstractValue value, RandomAccessFile RAF, int startOfRow,
			AbstractField field) throws MyException {

		if (fieldName.equals("0")) { // for print all, select all, update all,
										// delete all
			return true;
		}

		if (!fieldNameExists(fieldName.trim())) {
			throw new MyException("That field name doesnt exist");
		}

		if (relop.equals("=")) {
			return field.readBinary(RAF, startOfRow).compareTo(value) == 0;
		} else if (relop.equals("!=")) {
			return field.readBinary(RAF, startOfRow).compareTo(value) != 0;
		} else if (relop.equals("<")) {
			return field.readBinary(RAF, startOfRow).compareTo(value) < 0;
		} else if (relop.equals(">")) {
			return field.readBinary(RAF, startOfRow).compareTo(value) > 0;
		} else if (relop.equals("<=")) {
			return field.readBinary(RAF, startOfRow).compareTo(value) <= 0;
		} else if (relop.equals(">=")) {
			return field.readBinary(RAF, startOfRow).compareTo(value) >= 0;
		} else {
			throw new MyException("Inpropper format of the where clause");
		}

	}

	/**
	 * Inserts the value list into the table.
	 * 
	 * @param valueList
	 *            the data to insert
	 * @throws MyException
	 */
	public void insertRow(String valueList) throws MyException {

		try {

			RandomAccessFile RAF = new RandomAccessFile("TableInfo"
					+ File.separatorChar + "InsertedData" + File.separatorChar
					+ tableName + ".bin", "rw");

			if (valueList.charAt(0) == '('
					&& valueList.charAt(valueList.length() - 1) == ')') {
				valueList = valueList.substring(1, valueList.length() - 1);
			} else {
				RAF.close();
				throw new MyException(
						"Grammar is invalid, you need () around the value list");
			}

			String values[] = valueList.split(",");

			int i = 0;
			long fileLength = RAF.length();
			RAF.seek(RAF.length()); // go to end of file.
			if (fields.size() == values.length) {
				RAF.writeBoolean(true); // write true at the beginning of each
										// row.
										// Used when want to delete row.
				for (AbstractField s : fields) {
					try {
						s.writeBinary(
								values[i].trim(),
								RAF,
								(int) ((RAF.getFilePointer() / rowLength) * rowLength));
					} catch (MyException e) {
						RAF.setLength(fileLength); // takes off the last row if
													// there was an error.
						RAF.close();
						throw new MyException(
								" -- The whole value list was NOT inserted");
					}
					i++;
				}

			} else {
				RAF.close();
				throw new MyException(
						"You didnt insert the same number of values as fields");
			}

			RAF.close();

		} catch (IOException e) {
			throw new MyException(e);
		}
	}

	private String[] splitWhere(String whereClause) {
		String[] where = new String[] { null, null, null };
		int i = 1;
		for (String s : whereClause.split(" ")) {
			if (i == 1)
				fieldName = s;
			else if (i == 2)
				relop = s;
			else if (i == 3)
				value = s;

			i++;
		}
		return where;
	}

	/**
	 * deletes the rows where the where clause is true.
	 * 
	 * @param whereClause
	 * @throws MyException
	 */
	public void deleteRow(String whereClause) throws MyException {

		splitWhere(whereClause);

		AbstractField field = getField(fieldName);
		AbstractValue newValue = null;
		if (field != null) {
			newValue = field.getValueType();
			newValue.setValue(value);
		}

		try {
			RandomAccessFile RAF = new RandomAccessFile("TableInfo"
					+ File.separatorChar + "InsertedData" + File.separatorChar
					+ tableName + ".bin", "rw");
			long rafPos = 0;
			for (int i = 0; i < RAF.length() / rowLength; i++) {
				if (RAF.readBoolean()) { // checks if row is deleted
					rafPos = RAF.getFilePointer();

					if (checkWhere(fieldName, relop, newValue, RAF, i
							* rowLength, field)) {
						RAF.seek(rafPos - 1);
						RAF.writeBoolean(false); // flag false as deleted
					}
					RAF.seek((i + 1) * rowLength); // go to next row if
													// checkWhere is false;
				}
			}
			RAF.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * updates the field in all rows if where clause = "0". if not updates the
	 * field when the where clause is true.
	 * 
	 * @param whereClause
	 * @param valueToInsert
	 * @param fieldToUpdate
	 * @throws MyException
	 */
	public void updateField(String whereClause, String valueToInsert,
			String fieldToUpdate) throws MyException {

		splitWhere(whereClause);

		AbstractField fieldWhere = getField(fieldName);

		AbstractField fieldUpdate = getField(fieldToUpdate.trim());
		AbstractValue newValue = null;
		if (fieldWhere != null) {
			newValue = fieldWhere.getValueType();
			newValue.setValue(value);
		}
		if (fieldUpdate == null) {
			throw new MyException(fieldToUpdate + " Does not exist");
		}

		try {

			RandomAccessFile RAF = new RandomAccessFile("TableInfo"
					+ File.separatorChar + "InsertedData" + File.separatorChar
					+ tableName + ".bin", "rw");

			for (int i = 0; i < RAF.length() / rowLength; i++) {
				if (RAF.readBoolean()) { // checks if row is deleted

					if (checkWhere(fieldName, relop, newValue, RAF, i
							* rowLength, fieldWhere)) {
						fieldUpdate.writeBinary(valueToInsert, RAF, i
								* rowLength);
					}
					RAF.seek((i + 1) * rowLength); // go to next row.
				}

			}
			RAF.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.database.RelationalOperations#selectTable(java.lang.String)
	 */
	public void printTable(String whereClause) throws MyException {
		splitWhere(whereClause);

		AbstractField field = getField(fieldName);
		AbstractValue newValue = null;
		if (field != null) {
			newValue = field.getValueType();
			newValue.setValue(value);
		}

		System.out.println(this);

		try {

			RandomAccessFile RAF = new RandomAccessFile("TableInfo"
					+ File.separatorChar + "InsertedData" + File.separatorChar
					+ tableName + ".bin", "rw");

			long rafPos = 0;
			for (int i = 0; i < RAF.length() / rowLength; i++) {

				if (RAF.readBoolean()) { // checks if row is deleted
					rafPos = RAF.getFilePointer();

					if (checkWhere(fieldName, relop, newValue, RAF, i
							* rowLength, field)) {
						RAF.seek(rafPos);
						for (AbstractField s : fields) {
							System.out.print(s.readBinary(RAF, i * rowLength));
							System.out.print("     ");
						}
						System.out.println("");
					}
				}
				RAF.seek((i + 1) * rowLength); // go to next row
			}
			RAF.close();
		} catch (IOException e) {
			throw new MyException(e);
		}
	}

	@Override
	public Dataset select(String whereClause) throws MyException {
		Dataset datasetA = makeDataset();
		return datasetA.select(whereClause);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * project.database.RelationalOperations#projectTable(java.lang.String[])
	 */
	@Override
	public Dataset project(String[] projectClause) throws MyException {

		Dataset datasetA = makeDataset();
		return datasetA.project(projectClause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * project.database.RelationalOperations#joinTable(project.database.Table)
	 */
	@Override
	public Dataset join(RelationalOperations r2) throws MyException {

		Dataset datasetA = makeDataset();

		return datasetA.join(r2);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * project.database.RelationalOperations#unionTable(project.database.Table)
	 */
	@Override
	public Dataset union(RelationalOperations r2) throws MyException {

		Dataset datasetA = makeDataset();

		return datasetA.union(r2);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * project.database.RelationalOperations#intersectTable(project.database
	 * .Table)
	 */
	@Override
	public Dataset intersect(RelationalOperations r2) throws MyException {
		Dataset datasetA = makeDataset();

		return datasetA.intersect(r2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * project.database.RelationalOperations#minusTable(project.database.Table)
	 */
	@Override
	public Dataset minus(RelationalOperations r2) throws MyException {
		Dataset datasetA = makeDataset();

		return datasetA.minus(r2);
	}

	@Override
	public Dataset makeDataset() throws MyException {

		Dataset dataset = new Dataset();

		try {

			RandomAccessFile RAF = new RandomAccessFile("TableInfo"
					+ File.separatorChar + "InsertedData" + File.separatorChar
					+ tableName + ".bin", "rw");

			for (int i = 0; i < RAF.length() / rowLength; i++) {
				Row row = new Row();
				if (RAF.readBoolean()) { // checks if row is deleted

					for (AbstractField f : fields) {
						row.addValue(f.readBinary(RAF, i * rowLength));
					}

					dataset.addRow(row);

				}
				RAF.seek((i + 1) * rowLength);
			}

			RAF.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		dataset.setFields(fields);
		return dataset;
	}

	protected void backup() throws MyException {
		serDataset = makeDataset();
	}

	protected void restore() throws MyException {
		
		new File("TableInfo" + File.separatorChar + "InsertedData"
				+ File.separatorChar + tableName + ".bin").delete(); // delete
																		// file
																		// so
																		// can
																		// overwrite
																		// new
																		// one.

		for (Row r : serDataset.dataSet) {
			ArrayList<AbstractValue> rowValues = new ArrayList<AbstractValue>(
					r.getValues());
			String rowString = "";
			for (AbstractValue v : rowValues) {
				rowString += v.toString() + ",";
			}
			rowString = rowString.substring(0, rowString.length() - 1);
			rewriteBinaryFile(rowString);
		}
	}
	
	private void rewriteBinaryFile(String valueList) throws MyException {

		try {

			RandomAccessFile RAF = new RandomAccessFile("TableInfo"
					+ File.separatorChar + "InsertedData" + File.separatorChar
					+ tableName + ".bin", "rw");


			String values[] = valueList.split(",");

			int i = 0;
			long fileLength = RAF.length();
			RAF.seek(RAF.length()); // go to end of file.
			if (fields.size() == values.length) {
				RAF.writeBoolean(true); // write true at the beginning of each
										// row.
										// Used when want to delete row.
				for (AbstractField s : fields) {
					try {
						s.rewriteBinary(
								values[i].trim(),
								RAF,
								(int) ((RAF.getFilePointer() / rowLength) * rowLength));
					} catch (MyException e) {
						RAF.setLength(fileLength); 							// there was an error.
						RAF.close();
					}
					i++;
				}

			} else {
				RAF.close();
				throw new MyException(
						"You didnt insert the same number of values as fields");
			}

			RAF.close();

		} catch (IOException e) {
			throw new MyException(e);
		}
	}

}
