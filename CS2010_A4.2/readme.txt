ASSIGNMENT 4.2

1. Construct a EdgeWeightedGraph graph using in
2. Construct a LazyPrimMST prim using graph
3. Find edges using prim
4. Construct a int[] connections using graph.V(); Each index in connections represent a node of graph

4. for (edge in edges)
	- find start and end of edge
	- connection[start]++
	- connection[end]++
	
5. for (connection in connections)
	- if the node == 1: terminator
	- if the 4<node<20: switch
	- if node>=20: router