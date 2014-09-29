package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.ProjectCommand;

public class TestProjectCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new ProjectCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "project asd over asd ;";
		good[1] = "PRoject  aswe OVer   dictionary ;";
		bad[0] = "project jasd over;";
	}

}
