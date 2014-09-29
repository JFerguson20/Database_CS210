package project.one;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.database.Database;
import project.database.Table;
import project.field.AbstractField;

/**
 * The Class PrintTableCommand.
 */
public class PrintTableCommand implements ICommand {

	private Pattern pattern = Pattern.compile("print\\s+(.+);",
			Pattern.CASE_INSENSITIVE);

	private String tableName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			tableName = matcher.group(1);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#execute()
	 */
	public void execute() throws MyException {

		if (tableName.trim().equalsIgnoreCase("dictionary")) {
			System.out.println(Database.getDB());
		} else {
			Database.getDB().printTable(tableName.trim(), "0"); // print all the
																// table.
		}
	}

}