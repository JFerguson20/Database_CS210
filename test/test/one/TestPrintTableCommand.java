package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.PrintTableCommand;

public class TestPrintTableCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new PrintTableCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "print dsdf ;";
		good[1] = "print asdf ;";
		bad[0] = "prin asdf;";
	}

}
