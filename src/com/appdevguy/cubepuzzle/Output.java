package com.appdevguy.cubepuzzle;

import java.util.ArrayList;
import java.util.List;

public class Output {
	public static void printRow(Iterable<Node> row) {
		List<String> strs = new ArrayList<>();
		for (Node n : row)
			strs.add(n.getC().getLabel().toString());
		System.out.println(strs);
	}
	
	public static void graphicPrintRow(Iterable<Node> row) {
		char box = '\u2588';
		char[][] visual = new char[][] {
			new char[] {' ', ' ', ' ', ' ', ' '},
			new char[] {' ', box, box, box, ' '},
			new char[] {' ', box, box, box, ' '},
			new char[] {' ', box, box, box, ' '},
			new char[] {' ', ' ', ' ', ' ', ' '},
		};
		Piece p = null;
		List<Position> posList = new ArrayList<>();
		for (Node n : row) {
			Object label = n.getC().getLabel();
			if (label instanceof Piece)
				p = (Piece) label;
			else
				posList.add((Position) label);
		}
		for (Position pos : posList) {
			Square s = p.undo.apply(pos);
			visual[4 - s.b][s.a] = box;
		}
		System.out.println(p);
		for (char[] line : visual)
			System.out.println(new String(line));
		System.out.println();
	}
	
	private Output() {}
}
