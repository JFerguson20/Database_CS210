package project.one;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

/**
 * The Class ReadCommand.
 */
public class ReadCommand implements ICommand {

	private Pattern pattern = Pattern.compile("read\\s+'(.+)'\\s*;",
			Pattern.CASE_INSENSITIVE);

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

	/**
	 * Reads a file with a scanner sends the scanner to read(sc) in the class
	 * Driver.
	 * 
	 * @throws MyException
	 *             the my exception
	 */
	public void execute() throws MyException {
		Scanner sc;

		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new MyException();
		}
		while (sc.hasNext())
			new Driver().read(sc);

	}

}