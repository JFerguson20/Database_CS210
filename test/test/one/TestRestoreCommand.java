package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.RestoreCommand;

public class TestRestoreCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new RestoreCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "restore from 'asdf' ;";
		good[1] = "rEstore  frOM  'asf';";
		bad[0] = "restore from as";
	}
}
