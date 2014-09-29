package project.collections;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import project.field.AbstractField;
import project.one.MyException;
import project.value.AbstractValue;


//Didnt have time to finish this.
public class Tree {

	private String tableName;
	private AbstractField field;
	private RandomAccessFile RAFtree;
	private Node head;
	
	public Tree(String tableName, AbstractField field, RandomAccessFile RAFtree){
		this.RAFtree = RAFtree;
		this.tableName = tableName;
		this.field = field;	
		head = null;
	}
	
	public AbstractValue select(AbstractValue v, Node root) throws MyException{
		return root.getValue(field, RAFtree);
		
	}
	
	public AbstractValue max(Node root) throws MyException{
		if(root.getRightChild(RAFtree) == null){
			return root.getValue(field, RAFtree);
		}else{
			return max(root.getRightChild(RAFtree));
		}
	}
	
	public AbstractValue min(Node root) throws MyException{
		if(root.getLeftChild(RAFtree) == null){
			return root.getValue(field, RAFtree);
		}else{
			return min(root.getLeftChild(RAFtree));
		}
	}
	
	public void inOrderWalk(Node root){
		
		
	}
	
	public void reverseInOrderWalk(Node root){
		
	}
	
	public AbstractValue successor(Node root) throws MyException{
		return min(root.getRightChild(RAFtree));
	}
	
	public AbstractValue predecessor(Node root) throws MyException{
		return max(root.getLeftChild(RAFtree));
	}
	
	public void delete(Node toBeDeleted, Node root){
		
	}
	
	public void insert(AbstractValue value, Node root) throws MyException {
		try {
			Node temp = new Node(RAFtree.length(), null, null, null, null);
			if(root.getValue(field, RAFtree).compareTo(value) > 0){
				root.setLeftChild(RAFtree.length());
				temp.setParent(root.getParent(RAFtree).getPositionInFile());
			}
		} catch (IOException e) {
			throw new MyException("Error inserting");
		}

		
	}
}
