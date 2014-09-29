package project.one;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.database.Database;

/**
 * The Class UpdateCommand.
 */
public class UpdateCommand implements ICommand {

	private Pattern pattern = Pattern
			.compile(
					"update\\s+(.+)\\s+set\\s+(\\S+)\\s*=\\s*(\\S+)\\s*(where .+)?\\s*;",
					Pattern.CASE_INSENSITIVE);
	private Pattern patternWhere = Pattern.compile(
			"where\\s+(\\w+)\\s*(<=| >=| !=|=|<|>)(.*)",
			Pattern.CASE_INSENSITIVE);

	private String tableName;

	private String where;
	private String valueToInsert;
	private String fieldName = null;
	private String relop = null;
	private String value = null;
	private String fieldToUpdate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			tableName = matcher.group(1);
			fieldToUpdate = matcher.group(2);
			valueToInsert = matcher.group(3);
			where = matcher.group(4);
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
			Database.getDB().updateField(tableName.trim(), "0",
					valueToInsert.trim(), fieldToUpdate); // updates field in
															// whole table.
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

			Database.getDB().updateField(tableName.trim(), where,
					valueToInsert.trim(), fieldToUpdate);
		}

	}
}