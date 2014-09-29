package project.one;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.database.Database;

/**
 * The Class DropCommand.
 */
public class DropCommand implements ICommand {

	private Pattern pattern = Pattern.compile("drop\\s+table\\s+(.+);",
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

	/**
	 * Removes the specified table from the database;
	 * 
	 * @throws MyException
	 *             if table doesnt exist
	 */
	public void execute() throws MyException {

		Database.getDB().removeTable(tableName);
		new File("TableInfo" + File.separatorChar + "InsertedData"
				+ File.separatorChar + tableName + ".bin").delete();

	}

}