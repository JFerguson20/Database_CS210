package test.one;

import org.junit.Before;
import project.one.DefineTableCommand;
import project.one.ICommand;

public class TestDefineTableCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new DefineTableCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "deFine taBLe  tasd Having  Fields  as;";
		good[1] = "define   table asd   having fields as     ;";
		bad[0] = "define table asd;";
	}

}
