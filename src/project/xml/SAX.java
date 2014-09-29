package project.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.transform.TransformerConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import project.database.Database;
import project.database.Table;
import project.one.*;

public class SAX {
	/**
	 * Reads from the file database.xml and creates the tables and fields from
	 * the file.
	 * 
	 * @param args
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerConfigurationException
	 */
	public static void main(String[] args) throws SAXException, IOException,
			TransformerConfigurationException {
		new SAX().saxReader();
	}

	private void saxReader() throws SAXException, IOException {
		XMLReader reader = XMLReaderFactory.createXMLReader();
		reader.setContentHandler(new ContentHandler());

		try {
			Scanner sc = new Scanner(new File("TableInfo" + File.separatorChar
					+ "Tables" + File.separatorChar + "database.xml"));
			if (sc.hasNext()) {
				reader.parse("TableInfo" + File.separatorChar + "Tables"
						+ File.separatorChar + "database.xml");
			}

		} catch (FileNotFoundException e) {

		}

	}

	private class ContentHandler extends DefaultHandler {

		private boolean tableName = false;
		private boolean fieldName = false;
		private boolean dataType = false;
		private String myTableName;
		private String fName = "";
		private Table myTable;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if (qName.equals("tableName")) {
				tableName = true;
			} else if (qName.equals("fieldName")) {
				fieldName = true;
			} else if (qName.equals("dataType")) {
				dataType = true;
			}

		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (qName.equals("tableName")) {
				tableName = false;
				try {
					Database.getDB().addTable(myTableName, myTable);
				} catch (MyException e) {
					e.printStackTrace();
				}
			} else if (qName.equals("fieldName")) {
				fieldName = false;
			} else if (qName.equals("dataType")) {
				dataType = false;
			}

		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String data = new String(ch, start, length);

			if (tableName) {
				try {
					myTableName = data;
					myTable = new Table(myTableName);
				} catch (MyException e) {
					e.printStackTrace();
				}
			} else if (fieldName) {
				fName = data;
			} else if (dataType) {
				try {
					myTable.createField(fName, data);
				} catch (MyException e) {
					e.printStackTrace();
				}
			}

		}
	}
}