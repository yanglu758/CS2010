/** Assignment 3 - CS2010 
 * "Undirected Graphs"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: Lu Yang
 * YOUR MATRICULATION #: A0130684H
 * 
 * COMMENTS:
 * You are not allowed to change any existing codes for reading and writing. You
 * may add your own reading code or printing code to print your results of each part.
 * 
 * 
 * Assigned: Feb 29 (Monday), 2016
 * Due: Mar 18 (Friday by 11.59pm), 2016
 */

import java.io.*;
import java.util.*;

import Graph.*;
import Graph.Stack;

public class A3Main {
	private static TreeMap<Integer, Country> tree1;
	private static TreeMap<String, Integer> tree2;
	private static TreeMap<String, Double> tree3;
	private static PriorityQueue<Country> maxHeap;
	private static PriorityQueue<Component> componentHeap;
	private static ArrayList<Integer> blocked;

	private static final MaxHeapComparator comparator = new MaxHeapComparator();
	private static final ComponentComparator componentComparator = new ComponentComparator();

	private static class MaxHeapComparator implements Comparator<Country>{
		// opposite to minHeapComparator, invert the return values
		@Override
		public int compare(Country a, Country b) {
			double i = a.getBorder();
			double j = b.getBorder();
			return i>j ? -1 : i==j ? 0 : 1 ;
		}
	}

	private static class ComponentComparator implements Comparator<Component> {
		@Override
		public int compare(Component a, Component b) {
			int i = a.getNComponents();
			int j = b.getNComponents();
			return i>j ? -1 : i==j ? 0 : 1;
		}
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);  // for reading from console

		/*
		 * Your code: read and parse the file "countries_borders.dat"
		 */
		Graph graph = parse("countries_borders.dat");
		blocked = new ArrayList<Integer>();

		boolean exit = false;
		String menuInfo = 
				"================ Assignment 3 ==================\n"
						+ "1. List all countries and bordering countries \n"
						+ "2. Find shortest path \n"
						+ "3. List all countries with N neighbors \n"
						+ "4. Block a country \n"
						+ "5. Find countries with borders larger than X km \n"
						+ "6. Show all connected components (largest to smallest) \n"
						+ "7. Exit \n> ";

		while (!exit) {
			System.out.print(menuInfo);
			int menu = scanner.nextInt();
			System.out.println("================================================");

			switch (menu) {
			/*************************************TASK 3.1*************************************/
			case 1:
				// List all countries and bordering countries
				for (int i=0; i<graph.V(); i++) {
					String origin = tree1.get(i).getName();
					String output = origin + " (" + i + "): ";
					Bag<Integer> adj =  (Bag<Integer>) graph.adj(i);
					int count =0;
					for (Integer j: adj) {
						String country = tree1.get(j).getName();
						output += (country + " (" + j + ")");
						if (count!=(adj.size()-1)) output += ", ";
						count++;
					}
					System.out.println(output);
				}
				break;

				/*************************************TASK 3.2*************************************/
			case 2: // find shortest path
				System.out.println(">>> 3.1(b) Find shortest path");
				while (true) {
					System.out.format(">>> Enter source and destination pair (or -1 to escape): ");
					int source = scanner.nextInt();
					if (source == -1) // -1 to escape
						break;
					int destination = scanner.nextInt();  // destination id

					/*
					 * Your code: find shortest path and print the result
					 * ** For requirement 3.6, you can compute and print 
					 * in this part
					 */

					BreadthFirstPaths bfs = new BreadthFirstPaths(graph, source);
					// list all existing blocked countries
					String blockedStr = "Blocked Country: ";	
					for (int i=0; i<blocked.size(); i++) {
						int blockedId = blocked.get(i);
						bfs.setBlockedCountries(blockedId);
						blockedStr += tree1.get(blockedId).getName() + " (" + blockedId + ") ";
					}
					System.out.println(blockedStr);

					double pathBorder = 0;
					String output = "";
					if (bfs.hasPathTo(destination)) {
						Stack stack = (Stack) bfs.pathTo(destination);
						int size = stack.size();
						output = size + " : ";
						int prev = 0;
						boolean first = true;
						while (!stack.isEmpty()) {
							int id = (int) stack.pop();
							Country ctry = tree1.get(id);
							String country = ctry.getName();
							output += country + " (" + id + ")" + " -> ";
							pathBorder += ctry.getBorder();
							if (first) {
								first = false;
								prev = id;
								continue;
							}
							String border = id + "-" + prev;
							Double length = tree3.get(border);
							pathBorder -= 2*length;
							prev = id;
						}
						output = output.substring(0, output.length()-4);
						System.out.println(output);
						System.out.println("length of border around this path = " + pathBorder + "km");
					}
					else System.out.println("no path");
				} // end while
				System.out.println();
				break;

				/*************************************TASK 3.3*************************************/
			case 3:
				System.out.println("\n>>> 3.2 List all countries with N neighbors");
				while (true) {
					System.out.print(">>> Enter N (or -1 to escape): ");
					int n = scanner.nextInt();
					if (n == -1) // -1 to escape
						break;
					/*
					 * Your code: find countries with n neighbors and print
					 * the result
					 */
					ArrayList<String> list = new ArrayList<String>();
					for (int i=0; i<graph.V(); i++) {
						Bag<Integer> adj =  (Bag<Integer>) graph.adj(i);
						int size = adj.size();
						if (size==n) {
							list.add(tree1.get(i).getName());
						}
					}
					String output = "";
					for (int i=0; i<list.size(); i++) {
						output += list.get(i);
						if (i!=list.size()-1) output += ", ";
					}
					System.out.println(output);
				} // end while
				System.out.println();
				break;

				/*************************************TASK 3.4*************************************/
			case 4:
				System.out.println("\n>>> 3.3 Block countries");
				while (true) {
					System.out.format(">>> Enter ID of the country you want to block (or -1 to escape): ");
					int toBlock = scanner.nextInt();
					if (toBlock == -1) // -1 to escape
						break;
					/*
					 * Your code: 
					 * (1) you can find shortest path here OR
					 * (2) 
					 * list and finish this part by modifying your code in case 2
					 * You are allowed to jump to other part of switch branch
					 * to help you finish this part
					 */
					blocked.add(toBlock);
				} // end while
				System.out.println();
				break;

			case 5:
				System.out.println("\n>>> 3.4 Find country with borders larger than X");
				while (true) {
					System.out.print(">>> Enter border length X (or -1 to escape): ");
					double x = scanner.nextDouble();
					if (x < 0) // -1 to escape
						break;
					/*
					 * Your code: find country with borders larger than X
					 * and print the result
					 */
					String output = "";
					Country country = maxHeap.poll();
					while (country.getBorder()>x && !maxHeap.isEmpty()) {
						output = country + " -> ";
						int id = country.getId();
						Bag<Integer> adj = (Bag<Integer>) graph.adj(id);
						for (Integer i: adj) {
							Country neighbour = tree1.get(i);
							String edge = id + "-" + i;
							double distance = 0;
							if (tree3.get(edge)!=null) {
								distance = tree3.get(edge);
							}
							if (distance!=0) output += neighbour.getName() + " " + distance + " km, ";
							else output += neighbour.getName() + ", ";
						}
						output = output.trim().substring(0, output.length()-2);
						System.out.println(output);
						country = maxHeap.poll();
					}
				} // end while
				break;

			case 6:
				System.out.println("\n>>> 3.5 Show all connected components");
				/*
				 * Your code: do connected components analysis and print the result
				 */
				componentHeap = new PriorityQueue<Component>(1, componentComparator);
				CC cc = new CC(graph);
				for (int i=0; i<cc.count(); i++) {
					int connected = 0;
					String countries = "  ";
					for (int j=0; j<graph.V(); j++) {
						if (cc.id(j)==i) {
							connected++;
							countries += tree1.get(j).getName() + ", ";
						}
					}
					countries = countries.substring(0, countries.length()-2);
					Component component = new Component(countries, connected);
					componentHeap.offer(component);
				}
				int i=0;
				while (!componentHeap.isEmpty()) {
					Component component = componentHeap.poll();
					System.out.println("Component " + i + ": " + component.getNComponents());
					System.out.println(component.getCountries());
					i++;
				}
				break;

			case 7:
				// exit
				exit = true;
				System.out.println(">>> Exit");
				break;

			default:
				System.out.println("** Wrong command **");
				break;

			} // end switch (menu)

		} // end while

	} // public static void main(String[] args)

	public static Graph parse(String filename) {
		tree1 = new TreeMap<Integer, Country>();
		tree2 = new TreeMap<String, Integer>();
		tree3 = new TreeMap<String, Double>();
		maxHeap = new PriorityQueue<Country>(1, comparator);
		BufferedReader br;
		Graph graph = null;
		int id = 0;

		try {
			br = new BufferedReader(new FileReader(filename));
			String element = br.readLine();
			int v = 0;
			while (element!=null) {
				String[] temp = element.trim().split(">>");
				String origin = temp[0].trim();
				tree2.put(origin, v);
				element = br.readLine();
				v++;
			}
			System.out.println("parse/v: " + v);
			br.close();

			int current = 0;
			graph = new Graph(v);
			br = new BufferedReader(new FileReader(filename));
			element = br.readLine();
			while (element!=null) {
				String[] temp = element.trim().split(">>");
				String origin = temp[0].trim();
				String[] neighbours = temp[1].trim().split(";"); // neighour countries
				double total = 0;

				// the country has neighbours
				for (int i=0; i<neighbours.length; i++) {
					String[] neighbourStr = neighbours[i].split(":");
					int length = neighbourStr.length;
					String neighbourName = neighbourStr[0].trim();
					double distance = 0;
					int neighbourId = tree2.get(neighbourName);
					graph.addEdge(current, neighbourId);

					// there is a border between these two countries
					// there is a double distance value to parse
					if (neighbourStr.length>1) {
						String distanceStr = neighbourStr[1].trim();
						String[] distanceStrTemp = distanceStr.split(" ");
						distanceStrTemp[0] = distanceStrTemp[0].replaceAll(",", "");
						distance = Double.parseDouble(distanceStrTemp[0]);
						total += distance;
						String edge = current + "-" + neighbourId;
						tree3.put(edge, distance);
					}
				}

				Country country = new Country(origin, total, current);
				tree1.put(current, country);
				maxHeap.add(country);
				element = br.readLine();
				current++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return graph;
	}
} // class A3Main
