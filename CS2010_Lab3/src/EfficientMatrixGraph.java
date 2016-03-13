import java.util.ArrayList;

public class EfficientMatrixGraph {
	private int V;
	private int E;
	private boolean[] 
	
	public EfficientMatrixGraph(int V) {
		if (V<0) {
			throw new IllegalArgumentException("Number of vertices must be nonnegative");
		}
		this.V = V;
		this.E = 0;
		adj = (ArrayList<Boolean>[]) new ArrayList[V];
		for (int v=0; v<V; v++) {
			adj[v] = new ArrayList<Boolean>();
		}
	}
	public EfficientMatrixGraph(In in) {
		this(in.readInt());
	}
}
