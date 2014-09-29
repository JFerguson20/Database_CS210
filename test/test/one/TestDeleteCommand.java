package test.one;

import org.junit.Before;
import project.one.DeleteCommand;
import project.one.ICommand;

public class TestDeleteCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new DeleteCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "delete as where sd;";
		good[1] = "delEte sdf;";
		bad[0] = "delet asd;";
	}

}
