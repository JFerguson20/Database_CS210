package project.collections;

import java.io.Serializable;
import java.util.ArrayList;
import project.database.RelationalOperations;
import project.field.AbstractField;
import project.one.MyException;
import project.value.AbstractValue;

public class Dataset implements Comparable<Row>, RelationalOperations,
		Serializable {
	public ArrayList<Row> dataSet;
	private ArrayList<AbstractField> datasetFields;

	public Dataset() {
		dataSet = new ArrayList<Row>();
	}

	public void addRow(Row row) {
		dataSet.add(row);
	}

	@Override
	public String toString() {
		String dataSetString = "";
		for (AbstractField f : datasetFields) {
			dataSetString += f + ", ";
		}

		dataSetString += "\n";
		for (Row r : dataSet) {
			dataSetString += r + "\n";
		}
		return dataSetString;
	}

	@Override
	public int compareTo(Row o) {

		for (Row r : dataSet) {
			if (r.compareTo(o) == 0) { // if equal to a row return 0.
				return 0;
			}
		}
		return -1;
	}

	public void removeRow(Row r) {
		dataSet.remove(r);
	}

	public Dataset intersect(RelationalOperations r2) throws MyException {
		Dataset datasetB = r2.makeDataset();
		checkUnionCompatable(datasetB);
		ArrayList<Row> rowsToKeep = new ArrayList<Row>();

		for (Row row : dataSet) {
			for (Row r : datasetB.dataSet) {
				if (r.compareTo(row) == 0) {
					rowsToKeep.add(row);
				}
			}

		}
		dataSet = rowsToKeep;

		return this;
	}

	public Dataset union(RelationalOperations r2) throws MyException {
		Dataset datasetB = r2.makeDataset();
		checkUnionCompatable(datasetB);
		ArrayList<Row> rowsToRemove = new ArrayList<Row>();
		for (int i = 0; i < dataSet.size(); i++) {
			Row row = dataSet.get(i);
			for (Row r : datasetB.dataSet) {
				if (r.compareTo(row) == 0) {
					rowsToRemove.add(r);
				}
			}
		}
		for (Row rowToRemove : rowsToRemove) {
			datasetB.removeRow(rowToRemove);
		}

		combineDataset(datasetB);
		return this;
	}

	public Dataset minus(RelationalOperations r2) throws MyException {

		Dataset datasetB = r2.makeDataset();
		checkUnionCompatable(datasetB);
		ArrayList<Row> rowsToRemove = new ArrayList<Row>();
		for (Row row : dataSet) {
			for (Row r : datasetB.dataSet) {
				if (r.compareTo(row) == 0) {
					rowsToRemove.add(row);
				}
			}
		}
		for (Row rowToRemove : rowsToRemove) {
			removeRow(rowToRemove);
		}

		return this;
	}

	private void combineDataset(Dataset d) {
		for (Row r : d.dataSet) {
			addRow(r);
		}

	}

	public void setFields(ArrayList<AbstractField> fields) {
		datasetFields = new ArrayList<AbstractField>(fields);
	}

	private AbstractField getField(String fieldName) {

		for (AbstractField s : datasetFields) {
			if (s.getFieldName().equals(fieldName)) {
				return s;
			}
		}
		return null;
	}

	private boolean checkWhere(String fieldName, String relop, AbstractValue v,
			AbstractValue v2) throws MyException {

		if (fieldName.equals("0")) { // for print all, select all, update all,
										// delete all
			return true;
		}

		if (getField(fieldName.trim()) == null) {
			throw new MyException("That field name doesnt exist");
		}

		if (relop.equals("=")) {
			return v.compareTo(v2) == 0;
		} else if (relop.equals("!=")) {
			return v.compareTo(v2) != 0;
		} else if (relop.equals("<")) {
			return v.compareTo(v2) < 0;
		} else if (relop.equals(">")) {
			return v.compareTo(v2) > 0;
		} else if (relop.equals("<=")) {
			return v.compareTo(v2) <= 0;
		} else if (relop.equals(">=")) {
			return v.compareTo(v2) >= 0;
		} else {
			throw new MyException("Inpropper format of the where clause");
		}

	}

	@Override
	public Dataset select(String whereClause) throws MyException {
		Dataset newDataset = new Dataset();
		String fieldName = null;
		String relop = null;
		String value = null;
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

		AbstractValue v = null;
		AbstractValue v2 = null;

		if (!fieldName.equals("0")) {
			if (getField(fieldName) == null) {
				throw new MyException("The field " + fieldName
						+ " does not exist");
			}
			v2 = getField(fieldName).getValueType();

			v2.setValue(value);
		}

		for (Row row : dataSet) {
			if (!fieldName.equals("0")) {
				v = row.getValueAtIndexInRow(datasetFields
						.indexOf(getField(fieldName)));
			}
			if (checkWhere(fieldName, relop, v, v2)) {
				newDataset.addRow(row);
			}
		}

		newDataset.setFields(datasetFields);
		return newDataset;
	}

	@Override
	public Dataset project(String[] projectClause) throws MyException {
		ArrayList<AbstractField> fieldsProject = new ArrayList<AbstractField>();
		ArrayList<Integer> indexOfFields = new ArrayList<Integer>();
		for (String s : projectClause) {
			if (getField(s.trim()) != null) {
				fieldsProject.add(getField(s.trim()));
			} else {
				throw new MyException("The field " + s + " does not exist");
			}
		}
		for (AbstractField f : fieldsProject) {
			indexOfFields.add(datasetFields.indexOf(f));
		}
		Dataset newDataset = new Dataset();

		for (Row r : dataSet) {
			Row row = new Row();
			for (Integer i : indexOfFields) {
				row.addValue(r.getValues().get(i));
			}
			newDataset.addRow(row);
		}
		newDataset.setFields(fieldsProject);
		return newDataset;
	}

	private void joinFields(Dataset datasetB) {
		for (AbstractField f : datasetB.datasetFields) {
			datasetFields.add(f);
		}
	}

	@Override
	public Dataset join(RelationalOperations r2) throws MyException {
		Dataset datasetB = r2.makeDataset();
		Dataset newDataset = new Dataset();
		for (Row rowA : dataSet) {

			for (Row rowB : datasetB.dataSet) {
				Row newRow = new Row();
				newRow.combineRow(rowA);
				newRow.combineRow(rowB);
				newDataset.addRow(newRow);
			}
		}
		newDataset.setFields(datasetFields);
		newDataset.joinFields(datasetB);
		return newDataset;
	}

	@Override
	public Dataset makeDataset() throws MyException {
		return this;
	}

	public void checkUnionCompatable(Dataset datasetB) throws MyException {
		if (datasetFields.size() != datasetB.datasetFields.size()) {
			throw new MyException(
					"The two tables do not have the same number of fields");
		}
		// check if field types are the same
		for (int i = 0; i < datasetFields.size(); i++) {
			if (!datasetFields.get(i).getDataType()
					.equals(datasetB.datasetFields.get(i).getDataType())) {
				throw new MyException(
						"The two tables do not have the same type of fields");
			}
		}
	}

}
