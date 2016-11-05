package com.appdevguy.cubepuzzle;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface Node {
	public Node getL();
	public Node getR();
	public Node getU();
	public Node getD();
	public Column getC();
	public void removeUD();
	public void restoreUD();
	
	public default Iterable<Node> row() {
		return ()->new Iterator<Node>() {
			private Node next = Node.this;
			public boolean hasNext() { return next != null; }
			public Node next() {
				if (!hasNext()) throw new NoSuchElementException();
				Node out = next;
				next = next.getR();
				if (next == Node.this) next = null;
				return out;
			}
		};
	}
}
