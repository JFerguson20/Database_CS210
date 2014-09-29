package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.JoinCommand;

public class TestJoinCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new JoinCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "join bletch and asdf;";
		good[1] = "joIN ear   AND  asdf   ;";
		bad[0] = "joing awer;";
	}

}
