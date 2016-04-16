import java.util.*;

public class A4Main4{
	/**
	 * Label houses by nearest hospitals.
	 */
	public static void main(String[] args) {
		String filename;
		if(args.length > 1)
			filename = args[0];
		else {
			System.out.print("Please input filename: ");
			Scanner scanner = new Scanner(System.in);  // for reading from console
			filename = scanner.nextLine();
			scanner.close();
		}

		// Input the two nodes of graph.
		int s = Integer.parseInt("5");
		int v = Integer.parseInt("6");

		In in = new In(filename);
		EdgeWeightedGraph g = new EdgeWeightedGraph(in);
		LazyPrimMST mst = new LazyPrimMST(g);
		int minimumHeight = Integer.MAX_VALUE;
		
		LinkedList<Integer> pathFromStoV = new LinkedList<Integer>();
		EdgeWeightedGraph subgraph = new EdgeWeightedGraph(g.V());
		for (Edge e: mst.edges()) {
			subgraph.addEdge(e);
		}
		
		DijkstraUndirectedSP path = new DijkstraUndirectedSP(subgraph, s);
		int next = s;
		int height = 0;
		pathFromStoV.add(next);
		for (Edge e: path.pathTo(v)) {
			next = e.other(next);
			height = (int) e.weight();
			minimumHeight = Math.min(height, minimumHeight);
			pathFromStoV.add(next);
		}

		// Print out the corresponding path
		StdOut.printf("Path from %d to %d :  ", s, v);
		for (int x : pathFromStoV) {
			if (x == s) StdOut.print(x);
			else        StdOut.print("-" + x);
		}
		StdOut.println();
		StdOut.printf("height = %d\n", minimumHeight);
	}
}