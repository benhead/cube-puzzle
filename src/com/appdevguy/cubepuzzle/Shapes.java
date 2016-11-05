package com.appdevguy.cubepuzzle;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Shapes {
	// A shape is a Set<Square>
	public static final Set<Set<Square>> ALL;
	private static final List<Set<Square>> BASE = Arrays.asList(
			EnumSet.of(Square.SQ20, Square.SQ01, Square.SQ02, Square.SQ24, Square.SQ42),
			EnumSet.of(Square.SQ00, Square.SQ01, Square.SQ03, Square.SQ20, Square.SQ24, Square.SQ43),
			EnumSet.of(Square.SQ00, Square.SQ01, Square.SQ03, Square.SQ20, Square.SQ24, Square.SQ43, Square.SQ41),
			EnumSet.of(Square.SQ00, Square.SQ10, Square.SQ30, Square.SQ02, Square.SQ42, Square.SQ24),
			EnumSet.of(Square.SQ02, Square.SQ20, Square.SQ42, Square.SQ24),
			EnumSet.of(Square.SQ01, Square.SQ03, Square.SQ04, Square.SQ10, Square.SQ30, Square.SQ40, Square.SQ41, Square.SQ43, Square.SQ24),
			EnumSet.of(Square.SQ20, Square.SQ30, Square.SQ40, Square.SQ02, Square.SQ42, Square.SQ04, Square.SQ14, Square.SQ34),
			EnumSet.of(Square.SQ20, Square.SQ01, Square.SQ41, Square.SQ43, Square.SQ44, Square.SQ24),
			EnumSet.of(Square.SQ10, Square.SQ30, Square.SQ02, Square.SQ42, Square.SQ04, Square.SQ14, Square.SQ34, Square.SQ44),
			EnumSet.of(Square.SQ10, Square.SQ01, Square.SQ03, Square.SQ14, Square.SQ34, Square.SQ42),
			EnumSet.of(Square.SQ01, Square.SQ20, Square.SQ24, Square.SQ40, Square.SQ41, Square.SQ43, Square.SQ44),
			EnumSet.of(Square.SQ00, Square.SQ10, Square.SQ02, Square.SQ24, Square.SQ41, Square.SQ43),
			EnumSet.of(Square.SQ00, Square.SQ10, Square.SQ30, Square.SQ40, Square.SQ01, Square.SQ03, Square.SQ24, Square.SQ42),
			EnumSet.of(Square.SQ10, Square.SQ30, Square.SQ02, Square.SQ42, Square.SQ24, Square.SQ34),
			EnumSet.of(Square.SQ20, Square.SQ01, Square.SQ02, Square.SQ14, Square.SQ34, Square.SQ42),
			EnumSet.of(Square.SQ01, Square.SQ03, Square.SQ20, Square.SQ40, Square.SQ41, Square.SQ14, Square.SQ34, Square.SQ44, Square.SQ43),
			EnumSet.of(Square.SQ00, Square.SQ01, Square.SQ20, Square.SQ40, Square.SQ41, Square.SQ03, Square.SQ04, Square.SQ14, Square.SQ34, Square.SQ43),
			EnumSet.of(Square.SQ00, Square.SQ10, Square.SQ01, Square.SQ30, Square.SQ40, Square.SQ42, Square.SQ24, Square.SQ03, Square.SQ04),
			EnumSet.of(Square.SQ20, Square.SQ41, Square.SQ43, Square.SQ02, Square.SQ04, Square.SQ14, Square.SQ34),
			EnumSet.of(Square.SQ10, Square.SQ30, Square.SQ41, Square.SQ43, Square.SQ02, Square.SQ04, Square.SQ14, Square.SQ34));
	
	static {
		ALL = new HashSet<>();
		for (Set<Square> shape : BASE) {
			ALL.add(shape);
			shape = rotate(shape);
			ALL.add(shape);
			shape = rotate(shape);
			ALL.add(shape);
			shape = rotate(shape);
			ALL.add(shape);
			shape = flip(shape);
			ALL.add(shape);
			shape = rotate(shape);
			ALL.add(shape);
			shape = rotate(shape);
			ALL.add(shape);
			shape = rotate(shape);
			ALL.add(shape);
		}
	}
	
	private static Set<Square> rotate(Set<Square> in) {
		Set<Square> out = EnumSet.noneOf(Square.class);
		for (Square s : in)
			out.add(s.rotate());
		return out;
	}
	
	private static Set<Square> flip(Set<Square> in) {
		Set<Square> out = EnumSet.noneOf(Square.class);
		for (Square s : in)
			out.add(s.flip());
		return out;
	}
	
	private Shapes() {}
}
