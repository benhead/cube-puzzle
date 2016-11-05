package com.appdevguy.cubepuzzle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class Piece {
	public final Position pos;
	public final Basis normal;
	public final Function<Square, Position> transform;
	public final Function<Position, Square> undo;
	
	public Piece(int x, int y, int z, Basis normal) {
		if(x%4!=0 || y%4!=0 || z%4!=0)
			System.err.println("WARNING!! Piece location not a multiple of 4");
		this.pos = new Position(x, y, z);
		this.normal = normal;
		switch(normal) {
		case X:
			transform = s->new Position(pos.x, pos.y + s.a, pos.z + s.b);
			undo = p->Square.of(p.y - pos.y, p.z - pos.z);
			break;
		case Y:
			transform = s->new Position(pos.x + s.a, pos.y, pos.z + s.b);
			undo = p->Square.of(p.x - pos.x, p.z - pos.z);
			break;
		case Z:
			transform = s->new Position(pos.x + s.a, pos.y + s.b, pos.z);
			undo = p->Square.of(p.x - pos.x, p.y - pos.y);
			break;
		default:
			transform = null;
			undo = null;
		}
	}
	
	public Collection<Position> squares(Collection<Square> squares) {
		List<Position> out = new ArrayList<>(squares.size());
		for (Square s : squares)
			out.add(transform.apply(s));
		return out;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Piece))
			return false;
		if (obj == this)
			return true;
		Piece that =  (Piece) obj;
		return (normal == that.normal) && pos.equals(that.pos);
	}
	
	@Override
	public int hashCode() {
		return pos.hashCode() ^ normal.ordinal();
	}
	
	@Override
	public String toString() {
		return String.format("%s.%s", pos, normal);
	}
}
