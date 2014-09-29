package project.one;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.database.Database;

/**
 * The Class DeleteCommand.
 */
public class DeleteCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"delete\\s+(\\w+)(\\s+where\\s+(.+))?\\s*;",
			Pattern.CASE_INSENSITIVE);
	private Pattern patternWhere = Pattern.compile(
			"where\\s+(\\w+)\\s*(<=| >=| !=|=|<|>)(.*)",
			Pattern.CASE_INSENSITIVE);

	private String tableName;
	private String where;
	private String fieldName = null;
	private String relop = null;
	private String value = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			tableName = matcher.group(1);
			where = matcher.group(2);
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

		if (where == null) {
			new File("TableInfo" + File.separatorChar + "InsertedData"
					+ File.separatorChar + tableName.trim() + ".bin").delete();
		} else {
			Matcher matcherWhere = patternWhere.matcher(where);
			if (matcherWhere.find()) {
				fieldName = matcherWhere.group(1).trim();
				relop = matcherWhere.group(2).trim();
				value = matcherWhere.group(3).trim();
				where = fieldName + " " + relop + " " + value; // add them all
																// together
																// split with
																// spaces to
																// split in the
																// table class
			} else {
				throw new MyException("Invalid format of the where clause");
			}

			Database.getDB().deleteRows(tableName.trim(), where);
		}

	}

}