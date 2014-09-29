package project.one;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;

import project.xml.SAX;

/**
 * The Class Driver.
 */
public class Driver {

	/** The commands. */
	private ICommand[] commands = new ICommand[] { new SelectCommand(),
			new UpdateCommand(), new PrintTableCommand(), new ExitCommand(),
			new ReadCommand(), new BackuptoCommand(), new RestoreCommand(),
			new DefineTableCommand(), new RenameTableCommand(),
			new DropCommand(), new DefineIndexCommand(), new DeleteCommand(),
			new InsertCommand(), new ProjectCommand(), new JoinCommand(),
			new IntersectionCommand(), new UnionCommand(), new MinusCommand() };

	/**
	 * Run.
	 */
	public void run() {
		Scanner sc = new Scanner(System.in);

		read(sc);

	}

	/**
	 * Read.
	 * 
	 * @param sc
	 *            the sc
	 */
	public void read(Scanner sc) {
		boolean foundMatch = false;
		String in;

		while (true) {
			System.out.println(">");

			in = sc.nextLine();
			while (in.indexOf(';') < 0) {
				if (!sc.hasNext()) {
					break;
				}
				in += " " + sc.next();
			}

			for (ICommand c : commands) {
				if (c.matches(in)) {
					foundMatch = true;
					try {
						c.execute();
					} catch (MyException e) {
						System.out.println(e.getMessage());
					}
					break;
				}
			}
			if (!foundMatch) {
				System.out.println("This was not a valid statement");
			}

			if (!sc.hasNextLine()) {
				run();
			}
			foundMatch = false;
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException {

		try {
			SAX.main(null);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new File("TableInfo" + File.separatorChar + "InsertedData").mkdirs();
		new File("TableInfo" + File.separatorChar + "Tables").mkdirs();
		new File("TableInfo" + File.separatorChar + "BackedUpTables").mkdirs();
		new Driver().run();
	}

}