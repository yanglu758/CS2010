/******************************************************************************
 *  Compilation:  javac DijkstraUndirectedSP.java
 *  Execution:    java DijkstraUndirectedSP input.txt s
 *  Dependencies: EdgeWeightedGraph.java IndexMinPQ.java Stack.java Edge.java
 *  Data files:   http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are nonnegative.
 *
 *  % java DijkstraUndirectedSP tinyEWG.txt 6
 *  6 to 0 (0.58)  6-0 0.58000
 *  6 to 1 (0.76)  6-2 0.40000   1-2 0.36000
 *  6 to 2 (0.40)  6-2 0.40000
 *  6 to 3 (0.52)  3-6 0.52000
 *  6 to 4 (0.93)  6-4 0.93000
 *  6 to 5 (1.02)  6-2 0.40000   2-7 0.34000   5-7 0.28000
 *  6 to 6 (0.00)
 *  6 to 7 (0.74)  6-2 0.40000   2-7 0.34000
 *
 *  % java DijkstraUndirectedSP mediumEWG.txt 0
 *  0 to 0 (0.00)
 *  0 to 1 (0.71)  0-44 0.06471   44-93  0.06793  ...   1-107 0.07484
 *  0 to 2 (0.65)  0-44 0.06471   44-231 0.10384  ...   2-42  0.11456
 *  0 to 3 (0.46)  0-97 0.07705   97-248 0.08598  ...   3-45  0.11902
 *  ...
 *
 *  % java DijkstraUndirectedSP largeEWG.txt 0
 *  0 to 0 (0.00)  
 *  0 to 1 (0.78)  0-460790 0.00190  460790-696678 0.00173   ...   1-826350 0.00191
 *  0 to 2 (0.61)  0-15786  0.00130  15786-53370   0.00113   ...   2-793420 0.00040
 *  0 to 3 (0.31)  0-460790 0.00190  460790-752483 0.00194   ...   3-698373 0.00172
 *
 ******************************************************************************/

/**
 *  The <tt>DijkstraUndirectedSP</tt> class represents a data type for solving
 *  the single-source shortest paths problem in edge-weighted graphs
 *  where the edge weights are nonnegative.
 *  <p>
 *  This implementation uses Dijkstra's algorithm with a binary heap.
 *  The constructor takes time proportional to <em>E</em> log <em>V</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the <tt>distTo()</tt> and <tt>hasPathTo()</tt> methods take
 *  constant time and the <tt>pathTo()</tt> method takes time proportional to the
 *  number of edges in the shortest path returned.
 *  <p>
 *  For additional documentation,    
 *  see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of    
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne. 
 *  See {@link DijkstraSP} for a version on edge-weighted digraphs.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Nate Liu
 */
public class DijkstraUndirectedSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    /**
     * Computes a shortest-paths tree from the source vertex <tt>s</tt> to every
     * other vertex in the edge-weighted graph <tt>G</tt>.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless 0 &le; <tt>s</tt> &le; <tt>V</tt> - 1
     */
    public DijkstraUndirectedSP(EdgeWeightedGraph G, int s) {
        for (Edge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : G.adj(v))
                relax(e, v);
        }

        // check optimality conditions
        assert check(G, s);
    }
    
    public DijkstraUndirectedSP(EdgeWeightedGraph G, Iterable<Integer> sources) {
    	for (Edge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        
        for (Integer source: sources) {
        	distTo[source] = 0.0;
        }
        
        pq = new IndexMinPQ<Double>(G.V());
        
        // reverse iterables
        Stack<Integer> stack = new Stack<Integer>();
        for (Integer source: sources) {
        	stack.push(source);
        }
        while (!stack.isEmpty()) {
        	int source = stack.pop();
        	pq.insert(source, distTo[source]);
        }
        
        while (!pq.isEmpty()) {
        	int v = pq.delMin();
        	for (Edge e: G.adj(v))
        		relax(e, v);
        }
    }

    // relax edge e and update pq if changed
    private void relax(Edge e, int v) {
        int w = e.other(v);
        if (distTo[w] == distTo[v] + e.weight()) {
        	int rootW = this.dest(w);
        	int rootV = this.dest(v);
        	if (rootV>rootW) {
        		distTo[w] = distTo[v] + e.weight();
        		edgeTo[w] = e;
        	}
        }
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    /**
     * Returns the length of a shortest path between the source vertex <tt>s</tt> and
     * vertex <tt>v</tt>.
     *
     * @param  v the destination vertex
     * @return the length of a shortest path between the source vertex <tt>s</tt> and
     *         the vertex <tt>v</tt>; <tt>Double.POSITIVE_INFINITY</tt> if no such path
     */
    public double distTo(int v) {
        return distTo[v];
    }

    /**
     * Returns true if there is a path between the source vertex <tt>s</tt> and
     * vertex <tt>v</tt>.
     *
     * @param  v the destination vertex
     * @return <tt>true</tt> if there is a path between the source vertex
     *         <tt>s</tt> to vertex <tt>v</tt>; <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path between the source vertex <tt>s</tt> and vertex <tt>v</tt>.
     *
     * @param  v the destination vertex
     * @return a shortest path between the source vertex <tt>s</tt> and vertex <tt>v</tt>;
     *         <tt>null</tt> if no such path
     */
    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        int x = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            path.push(e);
            x = e.other(x);
        }
        return path;
    }
    
    public int dest(int v) {
    	if (!hasPathTo(v)) return -1;
    	int x = v;
    	for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
             x = e.other(x);
         }
    	return x;
    }


    // check optimality conditions:
    // (i) for all edges e = v-w:            distTo[w] <= distTo[v] + e.weight()
    // (ii) for all edge e = v-w on the SPT: distTo[w] == distTo[v] + e.weight()
    private boolean check(EdgeWeightedGraph G, int s) {

        // check that edge weights are nonnegative
        for (Edge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v-w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            Edge e = edgeTo[w];
            if (w != e.either() && w != e.other(e.either())) return false;
            int v = e.other(w);
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }


    /**
     * Unit tests the <tt>DijkstraUndirectedSP</tt> data type.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        int s = Integer.parseInt(args[1]);

        // compute shortest paths
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, s);


        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (Edge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }

}

/******************************************************************************
 *  Copyright 2002-2015, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
