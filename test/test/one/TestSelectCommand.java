package test.one;

import org.junit.Before;

import project.one.ICommand;
import project.one.SelectCommand;

public class TestSelectCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		ICommand c = new SelectCommand();
		return c;
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "select asjdfka   ;";
		good[1] = "select   aswe ;";
		bad[0] = "seleect asjdfka;";
	}

}
