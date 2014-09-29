package test.two;

import static org.junit.Assert.*;

import org.junit.Test;

import project.database.RelationalOperations;
import project.database.Table;
import project.one.MyException;

public class TestTable {

	@Test
	public void test() throws MyException {

		assertTrue(testTableToString());
	}

	private boolean testTableToString() throws MyException {
		String f1 = "Sal integer, c char(3), bool  boolean, var VArchar  , as reAl, ad Date";
		String f2 = "Sal integer, c integer, ch char( 4 ), var VArchar  , as BOOLEAN, a boolean";
		RelationalOperations t1 = new Table("Test Table", f1);
		RelationalOperations t2 = new Table("Another Test", f2);

		String testString1 = "Test Table: Sal INTEGER, c CHAR(3), bool BOOLEAN, var VARCHAR, as REAL, ad DATE, ";
		String testString2 = "Another Test: Sal INTEGER, c INTEGER, ch CHAR(4), var VARCHAR, as BOOLEAN, a BOOLEAN, ";

		return (testString1.equals(t1.toString()) && testString2.equals(t2
				.toString()));
	}

}
