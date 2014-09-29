package project.one;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class DefineIndexCommand.
 */
public class DefineIndexCommand implements ICommand {

	private Pattern pattern = Pattern.compile("define\\s+index\\s+on\\s+(.+);",
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
	public void execute() {

		System.out.println("DIDNT FINISH DEFINE INDEX");

	}

}