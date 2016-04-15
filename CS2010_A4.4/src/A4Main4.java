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
        }
        
		// Input the two nodes of graph.
        int s = Integer.parseInt("5");
        int v = Integer.parseInt("6");
		
        In in = new In(filename);
        EdgeWeightedGraph g = new EdgeWeightedGraph(in);
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(g, s);
        Iterable<Edge> path = sp.pathTo(v);
        LinkedList<Integer> pathFromStoV = new LinkedList<Integer>();
        
        for (Edge edge: path) {
        	int from = edge.either();
        	int to = edge.other(from);
        	if (!pathFromStoV.contains(from)) pathFromStoV.add(from);
        	if (!pathFromStoV.contains(to)) pathFromStoV.add(to);
        }
        
		// Print out the corresponding path
		StdOut.printf("Path from %d to %d :  ", s, v);
		for (int x : pathFromStoV) {
			if (x == s) StdOut.print(x);
			else        StdOut.print("-" + x);
		}
		StdOut.println();
		int minimumHeight = 0;
		StdOut.printf("height = %d\n", minimumHeight);
    }

}