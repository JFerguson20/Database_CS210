package test.one;

import org.junit.Before;
import project.one.DropCommand;
import project.one.ICommand;

public class TestDropCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new DropCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "drop table asd;";
		good[1] = "dROp   table  as   ;";
		bad[0] = "drop asd ;";
	}

}
