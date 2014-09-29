package project.one;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.database.Database;
import project.database.Table;

/**
 * The Class DefineTableCommand.
 */
public class DefineTableCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"define\\s+table\\s+(.+)\\s+having\\s+fields\\s*(.+);",
			Pattern.CASE_INSENSITIVE);

	private String tableName;
	private String fieldList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			tableName = matcher.group(1);
			fieldList = matcher.group(2);
			return true;
		}
		return false;
	}

	/**
	 * Creates a table with fields and puts them in the database.
	 * 
	 * @throws MyException
	 *             if table name already exists.
	 */
	public void execute() throws MyException {
		if (tableName.trim().equalsIgnoreCase("dictionary")) {
			throw new MyException("You can't name a table dictionary");
		}
		Table myTable = new Table(tableName.trim(), fieldList.trim());
		Database.getDB().addTable(tableName.trim(), myTable);

	}

}