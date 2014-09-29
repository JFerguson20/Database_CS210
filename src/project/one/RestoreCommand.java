package project.one;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.database.Database;

/**
 * The Class RestoreCommand.
 */
public class RestoreCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"^\\s*restore\\s+from\\s*'(.+)'\\s*;", Pattern.CASE_INSENSITIVE);

	private String fileName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			fileName = matcher.group(1);

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

		Database.getDB().restoreCommand(fileName);

	}

}