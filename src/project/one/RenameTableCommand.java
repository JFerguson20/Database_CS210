package project.one;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.database.Database;
import project.database.Table;

/**
 * The Class RenameTableCommand.
 */
public class RenameTableCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"rename\\s+table\\s+(.+\\s+)to\\s+(.+);", Pattern.CASE_INSENSITIVE);

	private String tableName;

	private String newTableName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			tableName = matcher.group(1);
			newTableName = matcher.group(2);

			return true;
		}
		return false;
	}

	/**
	 * Renames a table that is already defined.
	 * 
	 * @throws MyException
	 *             if table doesn't exist.
	 */
	public void execute() throws MyException {
		Database.getDB().renameTable(tableName.trim(), newTableName.trim());
	}

}