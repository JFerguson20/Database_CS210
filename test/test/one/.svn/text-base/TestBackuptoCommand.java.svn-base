package test.one;

import org.junit.Before;

import project.one.BackuptoCommand;
import project.one.ICommand;
import project.one.SelectCommand;

public class TestBackuptoCommand extends AbstractTestClass {

	@Override
	@Before
	public void setUp() throws Exception {
		good[0] = "backup to 'asjdfka'   ;";
		good[1] = "backup  to   'aswe' ;";
		bad[0] = "backup to asf;";
	}

	@Override
	public ICommand setCommand() {
		ICommand c = new BackuptoCommand();
		return c;
	}

}
