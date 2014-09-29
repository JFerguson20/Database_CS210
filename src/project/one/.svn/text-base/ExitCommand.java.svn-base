package project.one;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.xml.XMLWriter;

/**
 * The Class ExitCommand.
 */
public class ExitCommand implements ICommand {

	private Pattern pattern = Pattern.compile("exit\\s*;",
			Pattern.CASE_INSENSITIVE);

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	/**
	 * Quits the program.
	 * 
	 * @throws FileNotFoundException
	 * @throws MyException
	 */
	public void execute() {

		try {
			XMLWriter.main(null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.exit(0);

	}

}