package project.database;

import java.io.*;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.collections.Dataset;
import project.one.ICommand;
import project.one.IntersectionCommand;
import project.one.JoinCommand;
import project.one.MinusCommand;
import project.one.MyException;
import project.one.ProjectCommand;
import project.one.SelectCommand;
import project.one.UnionCommand;

/**
 * The Class Database.
 */
public class Database implements Serializable {

	private static Database db = null;
	private Integer i = 0;
	private Hashtable<String, Table> tables;

	private Hashtable<Integer, Dataset> datasets = new Hashtable<Integer, Dataset>();;

	private Database() {
		tables = new Hashtable<String, Table>();
	}

	public void backupCommand(String fileName) throws MyException {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			Collection<Table> c = tables.values();
			fos = new FileOutputStream("TableInfo" + File.separatorChar
					+ "BackedUpTables" + File.separatorChar + fileName);
			for (Table t : c) {
				t.backup();
			}
			out = new ObjectOutputStream(fos);
			out.writeObject(tables);
			out.close();
		} catch (FileNotFoundException e) {
			throw new MyException("There was a problem with that file name");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void restoreCommand(String fileName) throws MyException {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(fileName);
			in = new ObjectInputStream(fis);
			tables = (Hashtable<String, Table>) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			try{
				fis = new FileInputStream("TableInfo" + File.separatorChar
						+ "BackedUpTables" + File.separatorChar + fileName);
				in = new ObjectInputStream(fis);
				tables = (Hashtable<String, Table>) in.readObject();
				in.close();
			} catch (FileNotFoundException e1){
				throw new MyException("That file does not exist");
			} catch (Exception e1) {	
				e.printStackTrace();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collection<Table> c = tables.values();

		for (Table t : c) {
			t.restore();
		}
	}

	/**
	 * Adds a table.
	 * 
	 * @param tableName
	 *            the name of the table to add.
	 * @param table
	 *            the Table to add
	 * @throws MyException
	 *             if table already exists.
	 */
	public void addTable(String tableName, Table table) throws MyException {
		if (tableExists(tableName)) {
			throw new MyException("Table " + tableName + " already exists.");
		}
		tables.put(tableName, table);
	}

	/**
	 * Gets the Database, if one doesn't exist it creates a Database.
	 * 
	 * @return the database
	 */
	public static Database getDB() {
		if (db == null) {
			db = new Database();
		}
		return db;
	}

	/**
	 * Puts every every table in the database in a String.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		Collection<Table> c = tables.values();

		String s = "";
		for (Table t : c) {
			s += t + "\n";
		}
		return s;
	}

	/**
	 * Removes the table from the database.
	 * 
	 * @param tableName
	 *            the table name
	 * @return the table
	 * @throws MyException
	 *             if table doesn't exist
	 */
	public Table removeTable(String tableName) throws MyException {
		if (!tableExists(tableName)) {
			throw new MyException("Table " + tableName + " doesnt exist");
		}
		return tables.remove(tableName.trim());
	}

	/**
	 * Checks if table already exists.
	 * 
	 * @param tableName
	 * @return true if the table already exists.
	 */
	public boolean tableExists(String tableName) {
		return tables.containsKey(tableName);
	}

	/**
	 * Rename table.
	 * 
	 * @param tableName
	 *            the table name
	 * @param newTableName
	 *            the new table name
	 * @throws MyException
	 *             if new table name already exists
	 */
	public void renameTable(String tableName, String newTableName)
			throws MyException {

		if (tableExists(newTableName)) {
			throw new MyException("Table " + tableName + " already exists.");
		}

		try {
			Table newTable = removeTable(tableName);
			newTable.setTableName(newTableName);
			addTable(newTableName, newTable);
		} catch (NullPointerException e) {

			throw new MyException("Table " + tableName + " not found.", e);
		}
	}

	public void printTable(String tableName, String whereClause)
			throws MyException {
		getTable(tableName).printTable(whereClause);
	}

	public void insertData(String tableName, String valueList)
			throws MyException {
		getTable(tableName).insertRow(valueList);
	}

	public void deleteRows(String tableName, String whereClause)
			throws MyException {
		getTable(tableName).deleteRow(whereClause);
	}

	public void updateField(String tableName, String whereClause,
			String valueToInsert, String fieldToUpdate) throws MyException {
		getTable(tableName).updateField(whereClause, valueToInsert,
				fieldToUpdate);
	}

	private String parseRelationalOperations(String input) throws MyException {

		ICommand[] RelationalCommands = new ICommand[] { new SelectCommand(),
				new ProjectCommand(), new JoinCommand(),
				new IntersectionCommand(), new UnionCommand(),
				new MinusCommand() };
		Pattern pattern = Pattern.compile("(\\([^)^(]*?\\))");
		Matcher match = pattern.matcher(input);

		while (match.find()) {
			String inside = match.group(1);// process inside and get a
											// QueryList
			input = input.replace(inside, " " + i.toString() + " ");
			inside = inside.substring(1, inside.length() - 1) + ";";

			for (ICommand c : RelationalCommands) {

				if (c.matches(inside)) {
					if (c instanceof SelectCommand) {
						SelectCommand s = (SelectCommand) c;
						datasets.put(i, select(s.getQueryList()));
					} else if (c instanceof ProjectCommand) {
						ProjectCommand p = (ProjectCommand) c;
						datasets.put(i, project(p.getQueryList()));
					} else if (c instanceof JoinCommand) {
						JoinCommand j = (JoinCommand) c;
						datasets.put(i, join(j.getQueryList()));
					} else if (c instanceof IntersectionCommand) {
						IntersectionCommand inter = (IntersectionCommand) c;
						datasets.put(i, intersect(inter.getQueryList()));
					} else if (c instanceof UnionCommand) {
						UnionCommand u = (UnionCommand) c;
						datasets.put(i, union(u.getQueryList()));
					} else if (c instanceof MinusCommand) {
						MinusCommand m = (MinusCommand) c;
						datasets.put(i, minus(m.getQueryList()));
					}

				}

				match = pattern.matcher(input);
			}
			i++;
		}

		return input.trim();

	}

	private Dataset select(String[] queryLists) throws MyException {
		RelationalOperations r1;
		if (queryLists[0].matches("\\d+")) {
			r1 = datasets.remove(Integer.parseInt(queryLists[0]));
		} else {
			r1 = getTable(queryLists[0]);
		}

		return r1.select(queryLists[1]);
	}

	private Dataset project(String[] queryLists) throws MyException {
		RelationalOperations r1;

		if (queryLists[0].matches("\\d+")) {
			r1 = datasets.remove(Integer.parseInt(queryLists[0]));
		} else {
			r1 = getTable(queryLists[0]);
		}
		return r1.project(queryLists[1].split(","));
	}

	private Dataset join(String[] queryLists) throws MyException {
		RelationalOperations r1;
		RelationalOperations r2;

		if (queryLists[0].matches("\\d+")) {
			r1 = datasets.remove(Integer.parseInt(queryLists[0]));
		} else {
			r1 = getTable(queryLists[0]);
		}

		if (queryLists[1].matches("\\d+")) {
			r2 = datasets.remove(Integer.parseInt(queryLists[1]));
		} else {
			r2 = getTable(queryLists[1]);
		}

		return r1.join(r2);
	}

	private Dataset union(String[] queryLists) throws MyException {
		RelationalOperations r1;
		RelationalOperations r2;

		if (queryLists[0].matches("\\d+")) {
			r1 = datasets.remove(Integer.parseInt(queryLists[0]));
		} else {
			r1 = getTable(queryLists[0]);
		}

		if (queryLists[1].matches("\\d+")) {
			r2 = datasets.remove(Integer.parseInt(queryLists[1]));
		} else {
			r2 = getTable(queryLists[1]);
		}

		return r1.union(r2);
	}

	private Dataset intersect(String[] queryLists) throws MyException {
		RelationalOperations r1;
		RelationalOperations r2;
		if (queryLists[0].matches("\\d+")) {
			r1 = datasets.remove(Integer.parseInt(queryLists[0]));
		} else {
			r1 = getTable(queryLists[0]);
		}

		if (queryLists[1].matches("\\d+")) {
			r2 = datasets.remove(Integer.parseInt(queryLists[1]));
		} else {
			r2 = getTable(queryLists[1]);
		}

		return r1.intersect(r2);
	}

	private Dataset minus(String[] queryLists) throws MyException {
		RelationalOperations r1;
		RelationalOperations r2;
		if (queryLists[0].matches("\\d+")) {
			r1 = datasets.remove(Integer.parseInt(queryLists[0]));
		} else {
			r1 = getTable(queryLists[0]);
		}

		if (queryLists[1].matches("\\d+")) {
			r2 = datasets.remove(Integer.parseInt(queryLists[1]));
		} else {
			r2 = getTable(queryLists[1]);
		}

		return r1.minus(r2);
	}

	public void selectTable(String queryList, String whereClause)
			throws MyException {
		String[] s = new String[2];
		s[0] = parseRelationalOperations(queryList);
		s[1] = whereClause;
		System.out.println(select(s));
	}

	public void projectTable(String queryList, String projectClause)
			throws MyException {
		String[] s = new String[2];
		s[0] = parseRelationalOperations(queryList);
		s[1] = projectClause;
		System.out.println(project(s));
	}

	public void joinTable(String queryList, String queryList2)
			throws MyException {
		String[] s = new String[2];
		s[0] = parseRelationalOperations(queryList);
		s[1] = parseRelationalOperations(queryList2);
		System.out.println(join(s));

	}

	public void unionTable(String queryList, String queryList2)
			throws MyException {
		String[] s = new String[2];
		s[0] = parseRelationalOperations(queryList);
		s[1] = parseRelationalOperations(queryList2);
		System.out.println(union(s));
	}

	public void intersectTable(String queryList, String queryList2)
			throws MyException {
		String[] s = new String[2];
		s[0] = parseRelationalOperations(queryList);
		s[1] = parseRelationalOperations(queryList2);
		System.out.println(intersect(s));
	}

	public void minusTable(String queryList, String queryList2)
			throws MyException {
		String[] s = new String[2];
		s[0] = parseRelationalOperations(queryList);
		s[1] = parseRelationalOperations(queryList2);
		System.out.println(minus(s));
	}

	private Table getTable(String tableName) throws MyException {

		if (tableExists(tableName.trim())) {
			return tables.get(tableName.trim());
		} else {
			throw new MyException("This table doesnt exist: " + tableName);
		}

	}
}
