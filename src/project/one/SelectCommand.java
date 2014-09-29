package project.one;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.collections.Dataset;
import project.database.Database;
import project.database.RelationalOperations;
import project.database.Table;
import project.one.PrintTableCommand;

/**
 * The Class SelectCommand.
 */
public class SelectCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"^\\s*select\\s+(\\w+)(\\s+where\\s+(.+))?\\s*;", // for select a
																// tableName
			Pattern.CASE_INSENSITIVE);

	private Pattern patternWhere = Pattern.compile(
			"where\\s+(\\w+)\\s*(<=| >=| !=|=|<|>)(.*)",
			Pattern.CASE_INSENSITIVE);
	private Pattern p2 = Pattern.compile(
			"^\\s*select\\s*(\\(.*?\\)+)\\s*(\\s+where\\s+(.+))?\\s*;",
			Pattern.CASE_INSENSITIVE); // for
	// select
	// a
	// queryList
	private String where;

	private String queryList;
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
		Matcher m2 = p2.matcher(input);
		if (m2.find()) {
			queryList = m2.group(1);
			where = m2.group(2);
			return true;
		}
		if (matcher.find()) {
			queryList = matcher.group(1);
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
			Database.getDB().selectTable(queryList.trim(), "0"); // print all
																	// the
																	// table.
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
			Database.getDB().selectTable(queryList.trim(), where);
		}

	}

	public String[] getQueryList() {
		String[] s = new String[2];
		if (where == null) {
			where = "0";
		} else {
			Matcher matcherWhere = patternWhere.matcher(where);

			if (matcherWhere.find()) {
				fieldName = matcherWhere.group(1).trim();
				relop = matcherWhere.group(2).trim();
				value = matcherWhere.group(3).trim();
				where = fieldName + " " + relop + " " + value;
			}
		}
		s[0] = queryList.trim();
		s[1] = where;
		return s;
	}

}