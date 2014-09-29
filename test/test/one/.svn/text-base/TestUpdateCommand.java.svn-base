package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.UpdateCommand;

public class TestUpdateCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new UpdateCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "UPDate asdf Set asd=asd where 12 ;";
		good[1] = "update aas set asd=as;";
		bad[0] = "update asdf;";
	}
}
