package project.collections;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import project.field.AbstractField;
import project.one.MyException;
import project.value.AbstractValue;

public class Node {

	private Long parent;
	private Long leftChild;
	private Long rightChild;
	private	Long value;
	private long positionInFile;
	
	public Node (Long position, Long parent, Long leftChild, Long rightChild, Long value){
		this.positionInFile = position;
		this.parent = parent;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.value = value;
	}
	
	public Node getParent(RandomAccessFile RAF){
		return getNode(RAF, parent);
	}
	public Node getLeftChild(RandomAccessFile RAF){
		return getNode(RAF, leftChild);
	}
	public Node getRightChild(RandomAccessFile RAF){
		return getNode(RAF, rightChild);
	}
	public AbstractValue getValue(AbstractField field, RandomAccessFile RAFtable) throws MyException{
		return getAbstractValue(field, RAFtable);
	}
	
	public long getPositionInFile(){
		return positionInFile;
	}
	public void setParent(long parent){
		this.parent = parent;
	}
	public void setLeftChild(long leftChild){
		this.leftChild = leftChild;
	}
	public void setRightChild(long rightChild){
		this.rightChild = rightChild;
	}
	public void setValue(long value){
		this.value = value;
	}
	
	
	private Node getNode(RandomAccessFile RAF, Long pointer){
			try {
				RAF.seek(pointer);
				return new Node(pointer, RAF.readLong(), RAF.readLong(),RAF.readLong(), RAF.readLong());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}

	
	private AbstractValue getAbstractValue(AbstractField field, RandomAccessFile RAF) throws MyException{
		return field.readBinary(RAF,  value);
	}
	
	
}
