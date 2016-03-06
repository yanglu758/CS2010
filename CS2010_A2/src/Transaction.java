import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Lu Yang
 * A0130684H
 */

public class Transaction {
	private Date date;
	private double amount;
	private ArrayList<Transaction> doubleTransactions;
	
	private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("d/M/YYYY H:m:s"); 
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("d/M/YYYY");
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	public Transaction(Date date, double amount) {
		this.date = date;
		this.amount = amount;
		doubleTransactions = new ArrayList<Transaction>();
	}
	
	public Date getDate() {
		return date;
	}
	
	public double getAmount() {
		return amount;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Transaction) {
			Transaction transaction = (Transaction) obj;
			if (transaction.getAmount()==this.amount && isWithinFiveMins(this.date, transaction.getDate())) {
				doubleTransactions.add(transaction);
				doubleTransactions.add(new Transaction(date, amount));
				return true;
			}
			else return false;
		}
		else return false;
	}
	
	private boolean isWithinFiveMins(Date date1, Date date2) {
		String dateStr1 = dateFormatter.format(date1);
		String dateStr2 = dateFormatter.format(date2);
		if (!dateStr1.equals(dateStr2)) return false;
		double interval = ((double) date2.getTime() - (double) date1.getTime()) / 1000 / 60;
		if ((Math.abs(interval)<=5)) return true;
		else return false;
	}
	
	public ArrayList<Transaction> getDoubleTransactions() {
		return doubleTransactions;
	}
	
	@Override
	public String toString() {
		String output = "";
		output = output + "$" + df.format(amount) + "; " + dateTimeFormatter.format(date);
		return output;
	}
}
