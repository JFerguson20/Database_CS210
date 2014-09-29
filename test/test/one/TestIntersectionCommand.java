package test.one;

import org.junit.Before;
import project.one.ICommand;
import project.one.IntersectionCommand;

public class TestIntersectionCommand extends AbstractTestClass {

	@Override
	public ICommand setCommand() {
		return new IntersectionCommand();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "intersect asd and asd;";
		good[1] = "INTERsect  asf AND  asdf ; ;";
		bad[0] = "intersect;";
	}

}
