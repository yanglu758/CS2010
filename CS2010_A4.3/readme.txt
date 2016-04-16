ASSIGNMENT 4.3

1. Construct a EdgeWeightedGraph g
2. Construct DijkstraUndirectedSP sp

Noted that I modified DijkstraUndirectedSP code, such that now, it can takes in a constructor with multiple sources.
Due to the nature that if a house is equally far from more than 1 hospital, i create a method to dest, to trace the source of any vertice.

3. for (vertice in graph)
	find the root of each vertex using sp.dest(vertice)
	add to corresponding hospital arraylist

4. print