package com.appdevguy.cubepuzzle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Models {
	public static List<Piece> transform(Collection<Piece> in, int dx, int dy, int dz) {
		List<Piece> out = new ArrayList<>(in.size());
		for (Piece p : in)
			out.add(new Piece(p.pos.x + dx, p.pos.y + dy, p.pos.z + dz, p.normal));
		return out;
	}
	
	public static List<Piece> cube(int n) {
		return rectangularPrism(n, n, n);
	}
	
	public static List<Piece> rectangularPrism(int dx, int dy, int dz) {
		List<Piece> list = new ArrayList<>(dx*dy*2+dx*dz*2+dy*dz*2);
		list.addAll(rectangle(0, 0, 0, dx, dy, Basis.Z));
		list.addAll(rectangle(0, 0, dz*4, dx, dy, Basis.Z));
		list.addAll(rectangle(0, 0, 0, dx, dz, Basis.Y));
		list.addAll(rectangle(0, dy*4, 0, dx, dz, Basis.Y));
		list.addAll(rectangle(0, 0, 0, dy, dz, Basis.X));
		list.addAll(rectangle(dx*4, 0, 0, dy, dz, Basis.X));
		return list;
	}
	
	public static List<Piece> rectangle(int x, int y, int z, int w, int h, Basis o) {
		List<Piece> list = new ArrayList<>(w*h);
		if (w <= 0 || h <= 0)
			// we wouldn't handle this too good
			return list;
		int x2 = x + (o == Basis.X ? 1: w*4);
		int y2 = y + (o == Basis.Y ? 1 : (o == Basis.X ? w*4 : h*4));
		int z2 = z + (o == Basis.Z ? 1 : h*4);
		for (int xi = x; xi < x2; xi+=4)
			for (int yi = y; yi < y2; yi+=4)
				for (int zi = z; zi < z2; zi+=4)
					list.add(new Piece(xi, yi, zi, o));
		return list;
	}
	
	private Models() {}
}
