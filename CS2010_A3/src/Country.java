import java.util.ArrayList;

public class Country {
	private String name;
	private double border;
	private int id;
	
	public Country(String name, double border, int id) {
		this.name = name;
		this.border = border;
		this.id = id;
	}
	
	public String getName() { return name; }
	public double getBorder() { return border; }
	public int getId() { return id; }
	
	@Override
	public String toString() {
		return this.name + " " + this.border;
	}
}
