package com.appdevguy.cubepuzzle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
	public static void main(String[] args) throws Exception {
		Set<Piece> model = new HashSet<>();
		model.addAll(Models.transform(Models.rectangularPrism(3, 1, 1), 0, 4, 4));
		model.addAll(Models.transform(Models.rectangularPrism(1, 3, 1), 4, 0, 4));
		model.addAll(Models.transform(Models.rectangularPrism(1, 1, 3), 4, 4, 0));
		model.removeAll(Models.transform(Models.cube(1), 4, 4, 4));
		
		List<Node> result = DLX.parallelGo(model);
		System.out.println(result != null);
		if (result != null)
			for (Node n : result)
				Output.graphicPrintRow(n.row());
	}
}
