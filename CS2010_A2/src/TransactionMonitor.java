import java.io.*;
import java.util.*;
import java.text.*;


/* Assignment 2 - CS2010 
 * "Transaction Monitoring"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: Lu Yang
 * YOUR MATRICULATION #: A0130684H
 * 
 * COMMENTS:
 * You can modify this code anyway you'd like.  You can add additional class files, etc.
 * This code prompts the user at the command line to enter a filename (e.g. Visa.log) and the size of K.
 * It then each line of the input file as a string and prints that string out to the console.
 * 
 * 
 * Assigned: Feb 2 (Tuesday), 2016
 * Due: Feb 19 (Friday by 11.59pm), 2016
 */

public class TransactionMonitor {
	public BST bst;
	public int topK;
	public String src;
	public String dest;
	private ArrayList<String> doubleTransactions;
	private ArrayList<String> suspiciousTransactions;
	private PriorityQueue<Customer> bigSpenders;
	
	private static final SimpleDateFormat ft = new SimpleDateFormat("d/M/yyyy H:m:s"); 
	private static final maxHeapComparator comparator = new maxHeapComparator();
	
	public TransactionMonitor() {
		bst = new BST<String, Customer>();
		doubleTransactions = new ArrayList<String>();
		suspiciousTransactions = new ArrayList<String>();
		bigSpenders = new PriorityQueue<Customer>(20, comparator);
	}
	
	private static class maxHeapComparator implements Comparator<Customer> {
		@Override
		public int compare(Customer a, Customer b) {
			if (a.getAmount()<b.getAmount()) return 1;
			else if (a.getAmount()==b.getAmount()) return 0;
			else return -1;
		}
	}
	
	public void process() {
		String filename;
		int K;
		Scanner scanner = new Scanner(System.in);
        
		// Input filename and "K"
		System.out.print("Enter log file to open? ");
		filename=scanner.next();
        System.out.print("Number of top transactions to find (K)? ");
        K = scanner.nextInt();
        this.topK = K;
        this.src = filename;
        this.dest = formatDestFileName(filename);
		
        // Read each line of the input file and write it to the console
		try {
			BufferedReader in = new BufferedReader(new FileReader(src));  
	        String inputFileStr;
	        while ((inputFileStr = in.readLine()) != null) {
	        	parser(inputFileStr);
	        }
	        in.close();
		} catch (IOException e) {
	        System.out.println("File read error for (" + filename + ")! ");
	        e.printStackTrace();
		}
		
		try {
			// Requirement 3.3 Double Transactions
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(dest, true)));
			if (doubleTransactions.size()>0) {
				pw.println("Double Transactions");
				for (int i=0; i<doubleTransactions.size(); i++) {
					pw.println(doubleTransactions.get(i));
				}
			}
			pw.println();
			
			// Requirement 3.1 Top K Transactions of All Users
			Queue<String> q = (Queue<String>) bst.keys();
			Queue<Customer> customerList = new LinkedList<Customer>();
			while (!q.isEmpty()) {
				String temp = q.poll();
				Customer customer = (Customer) bst.get(temp);
				customerList.offer(customer);
				pw.println(customer);
			}
			pw.println();
			
			// Requirement 3.4 & 3.5 Suspicious Transactions
			processSuspiciousTransactions(customerList);
			if (suspiciousTransactions.size()>0) {
				pw.println("Suspicious Transactions");
				for (int i=0; i<suspiciousTransactions.size(); i++) {
					pw.println(suspiciousTransactions.get(i));
				}
			}
			pw.println();
			
			// Requirement 3.6 Big Spenders
			pw.println("Top 20 Spenders");
			Queue<Customer> tempBigSpenders = processBigSpenders();
			while (!tempBigSpenders.isEmpty()) {
				Customer temp = tempBigSpenders.poll();
				pw.println(temp.printBigSpenders());
				bigSpenders.add(temp);
			}
			
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Report successfully completed! :)");
		scanner.close();
	}
	
	public void parser(String inputFileStr) {
		String[] inputs = inputFileStr.split(" ");
		String name = inputs[0];
		double amount = Double.parseDouble(inputs[1].substring(1));
		Date t = null;
		try {
			t = ft.parse(inputs[2] + " " + inputs[3]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		addCustomer(name, amount, t); 
	}
	
	public void addCustomer(String name, double amount, Date t) {
		Transaction transaction = new Transaction(t, amount);
		if (bst.contains(name)) {
			Customer customer = (Customer) bst.get(name);
			if (customer.isDoubleTransaction(transaction)) {
				processDoubleTransactions(transaction, name);
			}
			customer.addTransaction(transaction);
			if (bigSpenders.remove(customer)) {
				bigSpenders.add(customer);
			}
		}
		else {
			Customer customer = new Customer(name, topK);
			customer.addTransaction(transaction);
			bst.put(name, customer);
			bigSpenders.add(customer);
		}
	}
	
	public void processDoubleTransactions(Transaction transaction, String name) {
		ArrayList<Transaction> repeated = transaction.getDoubleTransactions();
		String string = name + " :";
		string += (" " + repeated.get(0));
		for (int i=1; i<repeated.size(); i++) {
			string += ("; " + repeated.get(i));
		}
		doubleTransactions.add(string);
		
	}
	
	public void processSuspiciousTransactions(Queue<Customer> customerList) {
		while (!customerList.isEmpty()) {
			Customer temp = customerList.poll();
			if (temp.hasSuspiciousTransaction()) {
				Transaction suspicious = temp.getSuspiciousTransaction();
				String string = temp.getName() + " : " + suspicious;
				suspiciousTransactions.add(string);
			}
		}
	}
	
	public Queue<Customer> processBigSpenders() {
		Queue<Customer> q = new LinkedList<Customer>();
		int count = 20;
		while (!bigSpenders.isEmpty() && count>0) {
			Customer temp = bigSpenders.poll();
			q.add(temp);
			count--;
		}
		return q;
	}
	
	private static String formatDestFileName(String input) {
		String[] str = input.split("\\.");
		return "Report_" + str[0] + ".txt";
	}
	
	public static void main(String[] args) {
		TransactionMonitor monitor = new TransactionMonitor();
		monitor.process();
	}/* end main */
	
} /* TransMonitor.java */
