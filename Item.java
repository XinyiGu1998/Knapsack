package SATProblem;

public class Item {
	
	private int value;
	private int weight;
	private int index;
	
	
	public Item(int value, int weight, int index) {
		this.value = value;
		this.weight = weight;
		this.index = index;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		String output = "{Item" + index + ": (v = " + value + ", w = " + weight + ")}";
		return output;
	}

}