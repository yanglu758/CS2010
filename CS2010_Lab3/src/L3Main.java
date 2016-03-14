/** Lab 3 - CS2010 
 * "Space analysis of graphs"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: Lu Yang
 * YOUR MATRICULATION #: A0130684H
 * 
 * COMMENTS:
 * You are not allowed to change any existing codes for reading and writing. You
 * may add your own printing code to print your results of each part.
 * 
 * 
 * Assigned: Mar 2 (Wednesday), 2016
 * Due: Mar 18 (Friday by 11.59pm), 2016
 */


public class L3Main {
    
    public static void main(String[] args) {
    
        /* 
         * Step 1: Your code here
         *
         * read text file and build adjacency list graph (AdjListGraph.java) 
         * and adjacency matrix graph (AdjMatrixGraph.java)
        */
    	In in;
    	String filename = "./data/g1.txt";
    	
    	AdjListGraph adjListGraph = null;
        AdjMatrixGraph adjMatGraph = null;
        AdjMatrixEfficient adjMatEfficient = null;
    	
    	try {
    		in = new In(filename);
    		adjListGraph = new AdjListGraph(in);
    		in.close();
    		
    		in = new In(filename);
    		adjMatGraph = new AdjMatrixGraph(in);
    		in.close();
    		
    		in = new In(filename);
    		adjMatEfficient = new AdjMatrixEfficient(in);
    		in.close();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        
        
        
        /*
         * Step 2
         * get the size of graph of each representation
         *    AdjListGraph.java: dadj
         *    AdjMatrixGraph.java: adj
		 * Please use adjListGraph.sizeOfGraph() to get the memory usage of the graph with list implementation. 
		 * Please use adjMatGraph.sizeOfGraph() to get the memory usage of the graph with matrix implementation. 
        */
    	/***********************************************************************************************/
    	int nVertices = adjListGraph.V();
    	int nEdges = adjListGraph.E();
    	int memEdges = adjListGraph.getMemEdges();
    	int memTotal = adjListGraph.sizeOfGraph();
    	double efficiency = (double) memEdges / memTotal * 100;
    	
        System.out.format("1. Number of vertices = %d \n", nVertices);
        System.out.format("2. Number of edges = %d \n", nEdges);
        System.out.format("3. Output of the graph using adjacency list:");
        System.out.format("4. Adjacency list\n (a) Memory needed to record edges = %d\n", memEdges);
        System.out.format(" (b) Total amount of memory used  = %d\n", memTotal);
        System.out.format(" (c) Efficiency  = %f\n", efficiency);
        /***********************************************************************************************/
        
        memEdges = adjMatGraph.getMemEdges();
        memTotal = adjMatGraph.sizeOfGraph();
        efficiency = (double) memEdges / memTotal * 100;
        
        System.out.format("5. Output of the graph using matrix:" + adjMatGraph);
        System.out.format("6. Adjacency matrix\n (a) Memory needed to record edges = %d\n", memEdges);
        System.out.format(" (b) Total amount of memory used  = %d\n", memTotal);
        System.out.format(" (c) Efficiency  = %f\n", efficiency);
        /***********************************************************************************************/
        
        memEdges = adjMatEfficient.getMemEdges();
        memTotal = adjMatEfficient.sizeOfGraph();
        efficiency = (double) memEdges / memTotal * 100;
        System.out.format("Additional task: Efficient Adjacency matrix\n (a) Memory needed to record edges = %d\n", memEdges);
        System.out.format(" (b) Total amount of memory used  = %d\n", memTotal);
        System.out.format(" (c) Efficiency  = %f\n", efficiency);
        adjMatEfficient.print();
        /***********************************************************************************************/
    }
    
}
