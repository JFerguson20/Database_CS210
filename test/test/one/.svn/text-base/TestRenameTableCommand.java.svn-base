package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.RenameTableCommand;

public class TestRenameTableCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new RenameTableCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "rename taBLE asdf  to asdf;";
		good[1] = "REName   table   asdf to  asd  ;";
		bad[0] = "rname table asd;";
	}
}
