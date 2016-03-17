
public class Component {
	private String countries;
	private int nComponents;
	
	public Component(String countries, int nComponents) {
		this.countries = countries;
		this.nComponents = nComponents;
	}
	
	public String getCountries() { return this.countries; }
	public int getNComponents() { return this.nComponents; }
	
	@Override
	public String toString() {
		return nComponents + countries;
	}
}
