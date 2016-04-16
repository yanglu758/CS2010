1. Construct EdgeWeightedGraph g
2. Construct LazyPrimMST mst using g

Note that i modifed LazyPrimMST
MaxPQ is used, so that edge with maximum height will always be considered first

3. Find edges of mst using mst.edges()
4. Construct a DijkstraUndirectedSP path
5. Find the shortest path between s to v using path
6. Print the path