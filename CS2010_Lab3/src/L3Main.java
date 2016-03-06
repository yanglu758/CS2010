/** Lab 3 - CS2010 
 * "Space analysis of graphs"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: 
 * YOUR MATRICULATION #: 
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
        AdjListGraph adjListGraph = null;
        AdjMatrixGraph adjMatGraph = null;
        
        
        /*
         * Step 2
         * get the size of graph of each representation
         *    AdjListGraph.java: adj
         *    AdjMatrixGraph.java: adj
		 * Please use adjListGraph.sizeOfGraph() to get the memory usage of the graph with list implementation. 
		 * Please use adjMatGraph.sizeOfGraph() to get the memory usage of the graph with matrix implementation. 
        */
                
        System.out.format("1. Number of vertices = %d \n");
        System.out.format("2. Number of edges = %d \n");
        System.out.format("3. Output of the graph using adjacency list:");
        System.out.format("4. Adjacency list\n (a) Memory needed to record edges = %d\n");
        System.out.format(" (b) Total amount of memory used  = %d\n");
        System.out.format(" (c) Efficiency  = %f\n");
        System.out.format("5. Output of the graph using matrix:");
        System.out.format("6. Adjacency matrix\n (a) Memory needed to record edges = %d\n");
        System.out.format(" (b) Total amount of memory used  = %d\n");
        System.out.format(" (c) Efficiency  = %f\n");
        System.out.format("Additional task: Efficient Adjacency matrix\n (a) Memory needed to record edges = %d\n");
        System.out.format(" (b) Total amount of memory used  = %d\n");
        System.out.format(" (c) Efficiency  = %f\n");
        
    
    
    }
    
}
