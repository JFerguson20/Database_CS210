package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.InsertCommand;

public class TestInsertCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new InsertCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "insert asd into fasd  ;";
		good[1] = "insERt   asd   into    asd;";
		bad[0] = "insert as ;";
	}

}
