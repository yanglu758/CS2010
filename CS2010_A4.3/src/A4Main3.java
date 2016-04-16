/** Assignment 4 - CS2010 
 * "Fun with Graphs"
 * Part 3 - "Vertex Labeling Based on Distance"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: Lu Yang
 * YOUR MATRICULATION #: A0130684H
 * 
 * COMMENTS:
 * 
 * 
 * Assigned: Mar 25 (Friday), 2016
 * Due: Apr 15 (Friday by 11.59pm), 2016
 */

import java.util.*;

public class A4Main3{
    /**
     * Label houses by nearest hospitals.
     */
    public static void main(String[] args) {
        // Please hardcode your fileName here
        String fileName = "g2.txt";
        
        //This G stores the information on the graph and may be used by other Classes in your algorithm
        In in = new In(fileName);
        EdgeWeightedGraph g = new EdgeWeightedGraph(in);
        
        //Now ask the user for input of hospital vertices
        //User are expected to input up to five valid non-repetitive Integers 
        System.out.printf("User Input: ");
        Scanner sc = new Scanner(System.in);
        String inputLine = sc.nextLine();
        sc.close();
        String[] inputs = inputLine.split(" ");
        LinkedList<Integer>[] hospitalsList = (LinkedList<Integer>[]) new LinkedList[g.V()];
        ArrayList<Integer> hospitals = new ArrayList<Integer>();
        int i = 0;
        for (String input: inputs) {
        	try {
        		int hospital = Integer.parseInt(input);
        		hospitalsList[hospital] = new LinkedList<Integer>();
        		hospitals.add(hospital);
        	} catch (NumberFormatException e) {
        	}
        }
        
        //Run you program with User Input as hospitals and rest of vertices as houses
        //Code here for your main algorithm
        
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(g, hospitals);
        ArrayList<Integer> houses = new ArrayList<Integer>();
        for (i=0; i<g.V(); i++) {
        	if (hospitals.contains(i)) continue;
        	int hospital = sp.dest(i);
        	hospitalsList[hospital].add(i);
        	houses.add(i);
        }
        
        //Print out hospital and houses number and indexs
        System.out.printf("There are %d hospitals and %d houses.\n", hospitals.size(), houses.size());
        System.out.printf("Hospitals are: ");
        System.out.print(hospitals);
        System.out.printf("\n");
        System.out.printf("Houses are: ");
        System.out.print(houses);
        System.out.printf("\n\n");
        
        //Printing the result for each hospital
        for (int hospital: hospitals) { 
        	LinkedList<Integer> housesUnderHospital = hospitalsList[hospital];
            System.out.printf("Following %d houses visit hospital %d:\n\n", housesUnderHospital.size(), hospital);
            for (int house: housesUnderHospital) {
            	int distance = (int) sp.distTo(house);
            	System.out.printf("House %d visit with shortest distance %d\n", house, distance);
            }
            System.out.println();
        }
    }

}