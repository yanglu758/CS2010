ASSIGNMENT 4.1 SHORTEST AND LONGEST PROCESS CHAIN

SHORTEST PATH

1. Construct Digraph digraph
2. Find Topological topo based on digraph
3. for (vertice in topo.order()), find sources and sinks

4. Construct BFS bfs using digraph and sources
5. for (sink in sinks)
	a) find dist from sink to nearest source in sources
	b) compare dist with shortestPathLength
		- if dist is shorter
		- make dist the shortestPathLength,
		- mark this sink as v, and
		- assign pathFromStoV to bfs.pathTo(sink)

6. Print pathFromStoV


LONGEST PATH

1. Construct a EdgeWeightedDigraph weightedDigraph using In
	- In reads in x, y from given text file
	- Construct a int weight = -1
	- Construct DirectedEdge edge using x, y and weight

2. Initialise
	- int s = source of longest path
	- int v = sink of longest path
	- Stack<DirectedEdge> edges = path
	
3. For (source in sources)
	a) Construct a BellmanFordSP longestPath using weightedDigraph and source
	b) for (sink in sinks)
		- if there exists a path from sink to source using longestPath
		- find dist of the path
		- if dist > existing longestPathLength
			- longestPathLength = dist
			- source of longestPath s = source
			- sink of longestPath v = sink
			- edges = (Stack<DirectedEdge>) longestPath.pathTo(sink);
			
4. Convert Stack<DirectedEdge> to 