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
		}

		In in = new In(filename);
		Digraph digraph = new Digraph(in);
		Topological topo = new Topological(digraph);
		ArrayList<Integer> sources = new ArrayList<Integer>();
		ArrayList<Integer> sinks = new ArrayList<Integer>();

		Iterable<Integer> vertices = topo.order();
		for (int v: vertices) {
			if (digraph.indegree(v)==0) {
				sources.add(v);
			}
			if (digraph.outdegree(v)==0) {
				sinks.add(v);
			}
		}
		System.out.println(sources);
		System.out.println(sinks);

		BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(digraph, sources);
		int s = -1;
		int v = -1;
		int shortestPathLength = Integer.MAX_VALUE;
		for (int sink: sinks) {
			if (bfs.hasPathTo(sink)) {
				int dist = bfs.distTo(sink);
				if (dist < shortestPathLength) {
					shortestPathLength = dist;
					v = sink;
				}
			}
		}
		Iterable<Integer> path = bfs.pathTo(v);
		System.out.println(path);

		// Print out length of the shortest path
		// Print out the corresponding path
        System.out.println("Lenght of shortest path is: " + shortestPathLength);
		StdOut.printf("%d to %d (%d):  ", s, v, shortestPathLength);
		for (int x : path) {
			if (x == s) StdOut.print(x);
			else        StdOut.print("->" + x);
		}
		StdOut.println();       
		
		
		for (int source: sources) {
			Iterable<Integer> adj = digraph.adj(source);
		}
        // Print out lenght of the longest path
		// Print out the corresponding path
       /* System.out.println("Lenght of longest path is: " + longestPathLenght);
		StdOut.printf("%d to %d (%d):  ", s, v, longestPathLenght);
		for (int x : pathFromStoV) {
			if (x == s) StdOut.print(x);
			else        StdOut.print("->" + x);
		}*/
		StdOut.println();         
	}
}