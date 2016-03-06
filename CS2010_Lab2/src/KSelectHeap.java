import java.util.*;

public class KSelectHeap {
	PriorityQueue<Integer> maxHeap;
	PriorityQueue<Integer> minHeap;
	int k;

	//comparators for PriorityQueue
	private static final maxHeapComparator myMaxHeapComparator = new maxHeapComparator();
	private static final minHeapComparator myMinHeapComparator = new minHeapComparator();

	/**
	 * Comparator for the minHeap, smallest number has the highest priority, natural ordering
	 */
	private static class minHeapComparator implements Comparator<Integer>{
		@Override
		public int compare(Integer i, Integer j) {
			return i>j ? 1 : i==j ? 0 : -1 ;
		}
	}

	/**
	 * Comparator for the maxHeap, largest number has the highest priority
	 */
	private static  class maxHeapComparator implements Comparator<Integer>{
		// opposite to minHeapComparator, invert the return values
		@Override
		public int compare(Integer i, Integer j) {
			return i>j ? -1 : i==j ? 0 : 1 ;
		}
	}

	public KSelectHeap(int k) {
		maxHeap = new PriorityQueue<Integer>(k, myMaxHeapComparator);
		minHeap = new PriorityQueue<Integer>(k, myMinHeapComparator);
		this.k = k;
	}

	public void insert(int element) {
		if (maxHeap.size()==k) {
			int root = maxHeap.peek();
			if (element<root) {
				int temp = maxHeap.poll();
				maxHeap.add(element);
				minHeap.add(temp);
			}
			else minHeap.add(element);
		}
		else maxHeap.add(element);
	}
	
	public Object peek() {
		if (maxHeap.size()<k) {
			return null;
		}
		return maxHeap.peek();

	}

	public Object delete() {
		if (maxHeap.size()<k) {
			return null;
		}
		int temp = maxHeap.poll();
		if (!minHeap.isEmpty()) {
			maxHeap.add(minHeap.poll());
		}
		return temp;
	}

	public boolean isEmpty() {
		return maxHeap.size() == 0;
	}
}
