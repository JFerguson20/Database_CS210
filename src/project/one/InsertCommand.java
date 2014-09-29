package project.one;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.database.Database;
import project.database.Table;
import project.field.*;

/**
 * The Class InsertCommand.
 */
public class InsertCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"insert\\s*(.+)\\s*into\\s+(.+);", Pattern.CASE_INSENSITIVE);

	private String tableName;

	private String valueList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			tableName = matcher.group(2);
			valueList = matcher.group(1).trim();

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

		Database.getDB().insertData(tableName.trim(), valueList);

	}

}