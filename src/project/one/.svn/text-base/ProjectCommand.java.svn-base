package project.one;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.database.Database;

/**
 * The Class ProjectCommand.
 */
public class ProjectCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"^\\s*project\\s*(\\(.*?\\)+)\\s*over\\s*(.*)\\s*;",
			Pattern.CASE_INSENSITIVE);// for queryList
	private Pattern p1 = Pattern.compile(
			"^\\s*project\\s+(\\w+)\\s+over\\s+(.+\\s*);",
			Pattern.CASE_INSENSITIVE); // for
	// tableName

	private String queryList;

	private String fieldList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);
		Matcher matcher1 = p1.matcher(input);
		if (matcher.find()) {
			queryList = matcher.group(1);
			fieldList = matcher.group(2);

			return true;
		} else if (matcher1.find()) {
			queryList = matcher1.group(1);
			fieldList = matcher1.group(2);
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

		Database.getDB().projectTable(queryList.trim(), fieldList);

	}

	public String[] getQueryList() {
		String[] s = new String[2];
		s[0] = queryList.trim();
		s[1] = fieldList;
		return s;
	}

}