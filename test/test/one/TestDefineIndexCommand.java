package test.one;

import org.junit.Before;
import project.one.DefineIndexCommand;
import project.one.ICommand;

public class TestDefineIndexCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new DefineIndexCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "deFine index on  tasd;";
		good[1] = "define   iNDex  on asd ;";
		bad[0] = "define index asd;";
	}

}
