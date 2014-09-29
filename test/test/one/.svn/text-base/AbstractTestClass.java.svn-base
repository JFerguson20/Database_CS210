package test.one;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import project.one.*;

public abstract class AbstractTestClass {

	protected String[] good = new String[2];
	protected String[] bad = new String[1];

	public abstract void setUp() throws Exception;

	public abstract ICommand setCommand();

	public AbstractTestClass() {
		super();
	}

	@Test
	public void test() {

		for (int i = 0; i < good.length; i++) {
			assertTrue(setCommand().matches(good[i]));
		}
		for (int i = 0; i < bad.length; i++) {
			assertFalse(setCommand().matches(bad[i]));
		}

	}

}