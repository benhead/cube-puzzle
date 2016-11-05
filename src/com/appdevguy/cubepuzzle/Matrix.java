package com.appdevguy.cubepuzzle;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Matrix {
	public static Column create(Collection<Piece> model) {
		System.out.printf("%,d pieces\n%,d rows\n", model.size(), model.size() * Shapes.ALL.size());
		EnumSet<Square> ALL_SQUARES = EnumSet.allOf(Square.class);
		ColumnImpl root = new ColumnImpl("root");
		Map<Object, ColumnImpl> columns = new HashMap<>();
		for (Piece piece : model) {
			ColumnImpl pieceCol = createIfNew(piece, root, columns);
			for (Position pos : piece.squares(ALL_SQUARES)) {
				createIfNew(pos, root, columns);
			}
			for (Set<Square> shape : Shapes.ALL) {
				NodeImpl pieceNode = new NodeImpl(pieceCol);
				pieceCol.insertU(pieceNode);
				for (Position pos : piece.squares(shape)) {
					ColumnImpl posCol = columns.get(pos);
					NodeImpl posNode = new NodeImpl(posCol);
					posCol.insertU(posNode);
					pieceNode.insertL(posNode);
				}
			}
		}
		int n = nodeCount(root);
		System.out.printf("%,d columns\n%,d nodes\n%,d bytes (est)\n", columns.size(), n, n*32);
		return root;
	}
	
	public static int nodeCount(Column start) {
		int i = start.getS() + 1;
		for (Column c = start.getR(); c != start; c = c.getR())
			i += c.getS() + 1;
		return i;
	}
	
	private static ColumnImpl createIfNew(Object label, ColumnImpl root, Map<Object, ColumnImpl> columns) {
		ColumnImpl col = columns.get(label);
		if (col != null)
			return col;
		col = new ColumnImpl(label);
		columns.put(label, col);
		root.insertL(col);
		return col;
	}
	
	private static abstract class BaseImpl implements Node {
		BaseImpl u = this;
		BaseImpl d = this;
		
		@Override public Node getU() { return u; }
		@Override public Node getD() { return d; }
		
		@Override public void removeUD() {
			u.d = d;
			d.u = u;
		}
		
		@Override public void restoreUD() {
			u.d = this;
			d.u = this;
		}
		
		void insertU(BaseImpl ins) {
			ins.u = u;
			ins.d = this;
			u.d = ins;
			u = ins;
		}
	}
	
	private static class NodeImpl extends BaseImpl {
		NodeImpl l = this;
		NodeImpl r = this;
		final Column c;
		
		NodeImpl(Column c) {
			this.c = c;
		}
		
		@Override public Node getL() { return l; }
		@Override public Node getR() { return r; }
		@Override public Column getC() { return c; }
		
		void insertL(NodeImpl ins) {
			ins.l = l;
			ins.r = this;
			l.r = ins;
			l = ins;
		}
	}
	
	private static class ColumnImpl extends BaseImpl implements Column {
		ColumnImpl l = this;
		ColumnImpl r = this;
		int s = 0;
		final Object label;
		
		ColumnImpl(Object label) {
			this.label = label;
		}
		
		@Override public Column getL() { return l; }
		@Override public Column getR() { return r; }
		@Override public Column getC() { return this; }
		@Override public int getS() { return s; }
		@Override public void incS() { s++; }
		@Override public void decS() { s--; }
		@Override public Object getLabel() { return label; }
		
		@Override public void removeLR() {
			l.r = r;
			r.l = l;
		}
		
		@Override public void restoreLR() {
			l.r = this;
			r.l = this;
		}
		
		@Override void insertU(BaseImpl ins) {
			super.insertU(ins);
			incS();
		}
		
		void insertL(ColumnImpl ins) {
			ins.l = l;
			ins.r = this;
			l.r = ins;
			l = ins;
		}
	}
	
	private Matrix() {}
}
