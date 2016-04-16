import java.util.*;

public class A4Main1{
	/**
	 * Label houses by nearest hospitals.
	 */
	public static void main(String[] args) {
		String filename;
		Scanner scanner = new Scanner(System.in);  // for reading from console

		if(args.length > 1)
			filename = args[0];
		else {
			System.out.print("Please input filename: ");
			filename = scanner.nextLine();
			scanner.close();
		}

		In in = new In(filename);
		Digraph digraph = new Digraph(in);
		Topological topo = new Topological(digraph);
		ArrayList<Integer> sources = new ArrayList<Integer>();
		ArrayList<Integer> sinks = new ArrayList<Integer>();

		for (int v: topo.order()) {
			if (digraph.indegree(v)==0) {
				sources.add(v);
			}
			if (digraph.outdegree(v)==0) {
				sinks.add(v);
			}
		}

		BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(digraph, sources);
		int s = -1;
		int v = -1;
		int shortestPathLength = Integer.MAX_VALUE;
		Stack<Integer> pathFromStoV = new Stack<Integer>();
		for (int sink: sinks) {
			if (bfs.hasPathTo(sink)) {
				int dist = bfs.distTo(sink);
				if (dist < shortestPathLength) {
					shortestPathLength = dist;
					v = sink;
					pathFromStoV = (Stack<Integer>) bfs.pathTo(sink);
				}
			}
		}
		s = pathFromStoV.peek();
		
		// Print out length of the shortest path
		// Print out the corresponding path
		System.out.println("Lenght of shortest path is: " + shortestPathLength);
		StdOut.printf("%d to %d (%d):  ", s, v, shortestPathLength);
		for (int x : pathFromStoV) {
			if (x == s) StdOut.print(x);
			else        StdOut.print("->" + x);
		}
		StdOut.println();       
		
		
		in = new In(filename);
		int V = in.readInt();
		int E = in.readInt();
		EdgeWeightedDigraph weightedDigraph = new EdgeWeightedDigraph(V);
		
		for (int i=0; i<E; i++) {
			int x = in.readInt();
			int y = in.readInt();
			int weight = -1;
			DirectedEdge edge = new DirectedEdge(x, y, weight);
			weightedDigraph.addEdge(edge);
		}

		int longestPathLength = 0;
		Stack<DirectedEdge> edges = null;
		LinkedList<Integer> pathIndexes = new LinkedList<Integer>();
		for (int source: sources) {
			BellmanFordSP longestPath = new BellmanFordSP(weightedDigraph, source);
			for (int sink: sinks) {
				if (longestPath.hasPathTo(sink)) {
					int dist = (int) Math.abs(longestPath.distTo(sink));
					if (dist>longestPathLength) {
						longestPathLength = dist;
						v = sink;
						s = source;
						edges = (Stack<DirectedEdge>) longestPath.pathTo(sink);
					}
				}
			}
		}

		int next = s;
		pathIndexes.add(next);
		for (DirectedEdge e: edges) {
			pathIndexes.add(e.to());
		}


		// Print out lenght of the longest path
		// Print out the corresponding path
		System.out.println("Lenght of longest path is: " + longestPathLength);
		StdOut.printf("%d to %d (%d):  ", s, v, longestPathLength);
		for (int x : pathIndexes) {
			if (x == s) StdOut.print(x);
			else        StdOut.print("->" + x);
		}
		StdOut.println();         
	}
}