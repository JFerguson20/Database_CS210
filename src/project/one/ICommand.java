package project.one;

/**
 * The Interface ICommand.
 */
public interface ICommand {

	/**
	 * Tests if the input if matches the valid grammar for this command.
	 * 
	 * @param input
	 *            - Any size of a String
	 * @return true, if matches grammar. false, if does not match the proper
	 *         grammar.
	 */
	public boolean matches(String input);

	/**
	 * Prints out the type of command and that it is valid.
	 * 
	 * @throws MyException
	 *             the my exception
	 * 
	 */
	public void execute() throws MyException;

}