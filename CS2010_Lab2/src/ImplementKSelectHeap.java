import java.io.*;

public class ImplementKSelectHeap {

	public static void main(String[] args) throws IOException {
		String filename = "input4.txt";
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		String input = reader.readLine();
		input = input.substring(1, input.length()-1);
		String[] inputs = input.split(", ");
		KSelectHeap heap = new KSelectHeap(Integer.parseInt(inputs[0]));
		for (int i=1; i<inputs.length; i++) {
			if (inputs[i].equals("+")) System.out.println(heap.peek() + " - peek");
			else if (inputs[i].equals("-")) System.out.println(heap.delete() + " - delete");
			else heap.insert(Integer.parseInt(inputs[i]));
		}
		
		reader.close();
	}

}

/*
 * BUGS
 * Test case 5: Null Pointer Exception why??
 * 
 */