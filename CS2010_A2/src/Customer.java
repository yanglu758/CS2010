import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Lu Yang
 * A0130684H
 */

public class Customer {
	String name;
	PriorityQueue<Transaction> maxHeap;
	int K;
	int numTransaction;
	double sum;
	double average;
	Transaction first;
	Transaction last;
	
	private static final maxHeapComparator comparator = new maxHeapComparator();
	private static final SimpleDateFormat ft = new SimpleDateFormat("d/M/YYYY");
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	private static class maxHeapComparator implements Comparator<Transaction> {
		@Override
		public int compare(Transaction t1, Transaction t2) {
			if (t1.getAmount()>t2.getAmount()) return -1;
			else if (t1.getAmount()==t2.getAmount()) return 0;
			else return 1;
		}
	}

	public Customer(String name, int K) {
		this.name = name;
		maxHeap = new PriorityQueue<Transaction>(K, comparator);
		this.K = K;
	}

	public void addTransaction(Transaction transaction) {
		if (maxHeap.size()==0) {
			this.first = transaction;
			this.last = transaction;
		}
		if (transaction.getDate().after(this.last.getDate())) {
			this.last = transaction;
		}
		maxHeap.offer(transaction);
		numTransaction++;
		sum+=transaction.getAmount();
		average = sum / numTransaction;
	}

	private double getAvg() {
		return this.average;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getAmount() {
		return this.sum;
	}
	
	public boolean isDoubleTransaction(Transaction transaction) {
		if (maxHeap.contains(transaction)) {
			return true;
		}
		else return false;
	}
	
	public boolean hasSuspiciousTransaction() {
		Transaction topTransaction = maxHeap.poll();
		Queue<Transaction> q = new LinkedList<Transaction>();
		int count = K-1;
		double topKSum = 0;
		
		while (count>0 && !maxHeap.isEmpty()) {
			Transaction temp = maxHeap.poll();
			topKSum += temp.getAmount();
			count--;
		}
		
		while (!q.isEmpty()) {
			maxHeap.offer(q.poll());
		}
		maxHeap.offer(topTransaction);
		
		double avg = topKSum / (K-1);
		if (topTransaction.getAmount() > (5*avg)) return true;
		else return false;
	}
	
	public Transaction getSuspiciousTransaction() {
		return maxHeap.peek();
	}
	
	@Override
	public String toString() {
		Queue<Transaction> q = new LinkedList<Transaction>();
		String output = name + " :";
		int count = K;
		

		while (!maxHeap.isEmpty() && count>0) {
			Transaction temp = maxHeap.poll();
			output = output + " " + df.format(temp.getAmount());
			q.offer(temp);
			count--;
		}

		while (!q.isEmpty()) {
			maxHeap.offer(q.poll());
		}
		output += " # of transactions = " + numTransaction + "; avg = $" + df.format(getAvg());
		return output;
	}
	
	public String printBigSpenders() {
		return name + " : " + "Total Amount = $" + df.format(sum) + "; " +
				"First Transaction on " + ft.format(first.getDate()) + "; " +
				"Last Transaction on " + ft.format(last.getDate());
	}
}
