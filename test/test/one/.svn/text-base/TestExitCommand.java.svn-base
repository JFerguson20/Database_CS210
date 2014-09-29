package test.one;

import org.junit.Before;
import project.one.ExitCommand;
import project.one.ICommand;

public class TestExitCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new ExitCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "exit;";
		good[1] = "eXit ;";
		bad[0] = "ext;";
	}

}
