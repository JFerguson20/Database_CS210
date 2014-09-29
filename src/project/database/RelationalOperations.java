package project.database;

import project.collections.Dataset;
import project.one.MyException;

public interface RelationalOperations {

	/**
	 * Prints entire table if whereClause is "0" if not prints only the rows
	 * where the where clause is true.
	 * 
	 * @param whereClause
	 * @throws MyException
	 */
	public abstract Dataset select(String whereClause) throws MyException;

	public abstract Dataset project(String[] projectClause) throws MyException;

	public abstract Dataset join(RelationalOperations tableB)
			throws MyException;

	public abstract Dataset union(RelationalOperations tableB)
			throws MyException;

	public abstract Dataset intersect(RelationalOperations tableB)
			throws MyException;

	public abstract Dataset minus(RelationalOperations tableB)
			throws MyException;

	public abstract Dataset makeDataset() throws MyException;

}