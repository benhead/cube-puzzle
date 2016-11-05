package com.appdevguy.cubepuzzle;

public interface Column extends Node {
	@Override public Column getL();
	@Override public Column getR();
	public int getS();
	public void incS();
	public void decS();
	public Object getLabel();
	public void removeLR();
	public void restoreLR();
}
