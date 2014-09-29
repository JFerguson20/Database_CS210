package project.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import project.database.Database;
import project.one.*;

public class XMLWriter {

	private void write(String file) throws FileNotFoundException {

		String[] tables = Database.getDB().toString().split("\\n");
		String ENCODING = "ISO-8859-1";
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		out.println("<?xml version=\"1.0\" encoding=\"" + ENCODING + "\"?>");
		out.println("<DATABASE>");
		if (tables[0].equals("")) {
			return;
		} else {
			for (String t : tables) {
				String[] table = t.split(" ");
				out.println("<Table>");
				out.println("<tableName>"
						+ table[0].substring(0, table[0].length() - 1)
						+ "</tableName>");
				for (int i = 1; i < table.length; i += 2) {
					out.println("<Field>");
					out.println("<fieldName>" + table[i] + "</fieldName>");
					out.println("<dataType>"
							+ table[i + 1].substring(0,
									table[i + 1].length() - 1) + "</dataType>");
					out.println("</Field>");
				}
				out.println("</Table>");

			}
			out.println("</DATABASE>");
			out.close();
		}
	}

	/**
	 * Creates a xml file called database.xml with all of the tables and fields.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String file = "TableInfo" + File.separatorChar + "Tables/"
				+ File.separatorChar + "database.xml";
		new XMLWriter().write(file);
	}

}
