package project.one;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.database.Database;

/**
 * The Class IntersectionCommand.
 */
public class IntersectionCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"^\\s*intersect\\s+(.+\\s+)and\\s+(.+\\s*);",
			Pattern.CASE_INSENSITIVE); // If two table names

	private Pattern p2 = Pattern.compile(
			"^(\\s*intersect\\s*(\\(.*\\)+)\\s*and\\s*)",
			Pattern.CASE_INSENSITIVE); // first part of double query lists
	private Pattern p3 = Pattern.compile(
			"(^\\s*intersect\\s+(\\w+)\\s+and\\s*)", Pattern.CASE_INSENSITIVE); // for
																				// if
																				// first
																				// part
																				// is
																				// tableName
																				// and
																				// second
																				// part
																				// is
																				// queryList
	private Pattern p4 = Pattern.compile("(.*);"); // second query statement or
													// tablename.

	private String queryList;

	private String queryList2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	public boolean matches(String input) {
		String s = new String(input);
		Matcher matcher1 = pattern.matcher(s);
		Matcher matcher2 = p2.matcher(s);
		Matcher matcher3 = p3.matcher(s);
		if (matcher2.find()) {
			String BeginningOfStatement = matcher2.group(1);
			s = s.replace(BeginningOfStatement, "").trim();
			queryList = matcher2.group(2).trim();
			Matcher matcher4 = p4.matcher(s);
			if (matcher4.find()) {
				queryList2 = matcher4.group(1).trim();
				return true;
			}
		} else if (matcher3.find()) {
			String BeginningOfStatement = matcher3.group(1);
			queryList = matcher3.group(2);
			s = s.replace(BeginningOfStatement, "").trim();
			Matcher matcher4 = p4.matcher(s);
			if (matcher4.find()) {
				queryList2 = matcher4.group(1).trim();
				return true;
			}
		}
		if (matcher1.find()) {
			queryList = matcher1.group(1);
			queryList2 = matcher1.group(2);

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

		Database.getDB().intersectTable(queryList.trim(), queryList2.trim());

	}

	public String[] getQueryList() {
		String[] s = new String[2];
		s[0] = queryList.trim();
		s[1] = queryList2.trim();
		return s;
	}

}