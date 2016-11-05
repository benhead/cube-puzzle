package com.appdevguy.cubepuzzle;

public enum Square {
	SQ00(0, 0),
	SQ01(0, 1),
	SQ02(0, 2),
	SQ03(0, 3),
	SQ04(0, 4),
	SQ14(1, 4),
	SQ24(2, 4),
	SQ34(3, 4),
	SQ44(4, 4),
	SQ43(4, 3),
	SQ42(4, 2),
	SQ41(4, 1),
	SQ40(4, 0),
	SQ30(3, 0),
	SQ20(2, 0),
	SQ10(1, 0);
	
	public final int a;
	public final int b;
	
	public Square rotate() {
		Square[] vals = values();
		return vals[(ordinal() + 4) % vals.length];
	}
	
	public Square flip() {
		Square[] vals = values();
		return vals[(vals.length - ordinal()) % vals.length];
	}
	
	public static Square of(int a, int b) {
		return Square.valueOf(String.format("SQ%d%d", a, b));
	}
	
	private Square(int a, int b) {
		this.a = a;
		this.b = b;
	}
}
