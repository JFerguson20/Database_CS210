package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.UnionCommand;

public class TestUnionCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new UnionCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "union as and asjdfka   ;";
		good[1] = "UNIon aswe and   aswe;";
		bad[0] = "union asjdfka;";
	}

}
