package com.appdevguy.cubepuzzle;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class DLX {
	private final Column root;
	private final Node[] result;
	private final int[] kCounts;
	
	public DLX(Collection<Piece> model) {
		root = Matrix.create(model);
		result = new Node[model.size()];
		kCounts = new int[result.length+1];
	}
	
	public List<Node> go() {
		long start = System.nanoTime();
		//boolean found = search(0);
		boolean found = nonRecursiveSearch();
		long end = System.nanoTime();
		System.out.printf("%f seconds\n", (end - start)/1e9);
		System.out.printf("%s\n%d\n", Arrays.toString(kCounts), Arrays.stream(kCounts).asLongStream().sum());
		return found ? Arrays.asList(result) : null;
	}
	
	public static List<Node> go(Collection<Piece> model) {
		return new DLX(model).go();
	}
	
	public static List<Node> parallelGo(Collection<Piece> model) throws Exception {
		return parallelGo(model, Runtime.getRuntime().availableProcessors());
	}
	
	public static List<Node> parallelGo(final Collection<Piece> model, int parallelism) throws Exception {
		CompletionService<List<Node>> cs = new ExecutorCompletionService<>(
				Executors.newFixedThreadPool(parallelism, r->{
					Thread t = Executors.defaultThreadFactory().newThread(r);
					t.setDaemon(true);
					return t;
				}));
		final AtomicInteger nextRow = new AtomicInteger();
		long start = System.nanoTime();
		for (int i = 0; i < parallelism; i++)
			cs.submit(()->{
				DLX dlx = new DLX(model);
				Column c = dlx.minColumn();
				dlx.cover(c);
				while (true) {
					Node r = c;
					for (int row = nextRow.incrementAndGet(); row > 0; row--) {
						r = r.getD();
						if (r == c)
							return null;
					}
					for (Node j = r.getR(); j != r; j = j.getR()) {
						dlx.cover(j.getC());
					}
					if (dlx.nonRecursiveSearch()) {
						dlx.result[dlx.result.length - 1] = r;
						return Arrays.asList(dlx.result);
					}
					for (Node j = r.getL(); j != r; j = j.getL()) {
						dlx.uncover(j.getC());
					}
				}
			});
		List<Node> result = null;
		for (int i = 0; i < parallelism; i++) {
			result = cs.take().get();
			if (result != null) {
				break;
			}
		}
		long end = System.nanoTime();
		System.out.printf("%f seconds\n", (end - start)/1e9);
		return result;
	}
	
	private boolean nonRecursiveSearch() {
		Column[] columnStack = new Column[result.length+1];
		int k = 0;
		while (k >= 0) {
			if (root.getR() == root) {
				return true;
			}
			Column c = columnStack[k];
			Node r;
			if (c == null) {
				// first run-through at k; init c and r
				kCounts[k]++;
				c = minColumn();
				if (c.getS() == 0) {
					// shortcut backtrack (also safety if k=result.length)
					k--;
					continue;
				}
				r = columnStack[k] = c;
				cover(c);
			} else {
				// continuing with k; undo prior failed r
				r = result[k];
				for (Node j = r.getL(); j != r; j = j.getL()) {
					uncover(j.getC());
				}
			}
			r = r.getD();
			if (r == c) {
				// finished loop over column; backtrack
				uncover(c);
				columnStack[k--] = null;
				continue;
			}
			// try new r; 'recurse'
			//System.out.printf("k=%d\t", k); r.printRow();
			for (Node j = r.getR(); j != r; j = j.getR()) {
				cover(j.getC());
			}
			result[k++] = r;
		}
		return false;
	}
	
	private boolean search(int k) {
		kCounts[k]++;
		if (root.getR() == root) return true;
		//if (k == result.length) return false;
		Column c = minColumn();
		// s=0 check not required for correctness, but improves performance slightly
		if (c.getS() == 0) {
			return false;
		}
		cover(c);
		for (Node r = c.getD(); r != c; r = r.getD()) {
			for (Node j = r.getR(); j != r; j = j.getR()) {
				cover(j.getC());
			}
			if (search(k + 1)) {
				result[k] = r;
				return true;
			}
			for (Node j = r.getL(); j != r; j = j.getL()) {
				uncover(j.getC());
			}
		}
		uncover(c);
		return false;
	}
	
	private Column minColumn() {
		Column min = null;
		int minS = Integer.MAX_VALUE;
		for (Column c = root.getR(); c != root; c = c.getR()) {
			if (c.getS() < minS) {
				minS = c.getS();
				min = c;
			}
		}
		return min;
	}
	
	private void cover(Column c) {
		c.removeLR();
		for (Node i = c.getD(); i != c; i = i.getD()) {
			for (Node j = i.getR(); j != i; j = j.getR()) {
				j.removeUD();
				j.getC().decS();
			}
		}
	}
	
	private void uncover(Column c) {
		for (Node i = c.getU(); i != c; i = i.getU()) {
			for (Node j = i.getL(); j != i; j = j.getL()) {
				j.getC().incS();
				j.restoreUD();
			}
		}
		c.restoreLR();
	}
}
