package com.appdevguy.cubepuzzle;

public class Position {
	public final int x;
	public final int y;
	public final int z;
	
	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Position))
			return false;
		if (obj == this)
			return true;
		Position that = (Position) obj;
		return x == that.x && y == that.y && z == that.z;
	}
	
	@Override
	public int hashCode() {
		return x ^ Integer.rotateLeft(y, 11) ^ Integer.rotateRight(z, 11);
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d, %d)", x, y, z);
	}
}
