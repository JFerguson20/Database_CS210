package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.MinusCommand;

public class TestMinusCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new MinusCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "minus asdf and querylist;";
		good[1] = "MinUS   asdf And gasd ;";
		bad[0] = "eminus asdf;";
	}

}
