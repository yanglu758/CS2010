/** Assignment 4 - CS2010 
 * "Fun with Graphs"
 * Part 2 - "Communication Network Construction and Cost"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: 
 * YOUR MATRICULATION #: 
 * 
 * COMMENTS:
 * You are not allowed to change any existing codes for printing. You
 * may add your own reading code or printing code to print your results of each part.
 * 
 * 
 * Assigned: Mar 25 (Friday), 2016
 * Due: Apr 15 (Friday by 11.59pm), 2016
 */
 
 
import java.util.Scanner;


public class A4Main2 {
    
    /**
     * @param args the command line arguments
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
        
        In in = new In(filename);
        EdgeWeightedGraph graph = new EdgeWeightedGraph(in);
        LazyPrimMST prim = new LazyPrimMST(graph);
        Iterable<Edge> edges = prim.edges();
        
        int numOfEdges = graph.V()-1;
        int cableCost = (int) prim.weight();
        int numOfTerminators = 0;
        int numOfSwitches = 0;
        int numOfRouters = 0;
        
        int[] connections = new int[graph.V()];
        
        for (Edge edge: edges) {
        	int v = edge.either();
        	int w = edge.other(v);
        	connections[v]++;
        	connections[w]++;
        }
        
        for (int connection: connections) {
        	if (connection==1) numOfTerminators++;
        	else if (connection>4 && connection<20) numOfSwitches++;
        	else if (connection>=20) numOfRouters++;
        }
        
        int terminatorCost = numOfTerminators * 5;
        int switchCost = numOfSwitches * 500;
        int routerCost = numOfRouters * 1000;

        int overallCost = cableCost + terminatorCost + switchCost + routerCost;
        
        System.out.format("Number of edges = %d; total cost = $%d\n", numOfEdges, cableCost);
        System.out.format("Number of terminal nodes = %d; total cost = $%d\n", numOfTerminators, terminatorCost);
        System.out.format("Number of switches = %d; total cost = $%d\n", numOfSwitches, switchCost);
        System.out.format("Number of routers = %d; total cost = $%d\n", numOfRouters, routerCost);
        System.out.format("Overall cost = $%d\n", overallCost);
    }
    
}
