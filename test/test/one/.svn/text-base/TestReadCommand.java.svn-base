package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.ReadCommand;

public class TestReadCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new ReadCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "read 'FILENAME' ;";
		good[1] = "read  'asdf ' ;";
		bad[0] = "read asd;";
	}

}
