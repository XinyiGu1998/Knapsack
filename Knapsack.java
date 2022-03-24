package SATProblem;

import java.util.ArrayList;



public class Knapsack {

	public Pair Maximum01(ArrayList<Item> itemList, int capacity) {
		ArrayList<Item> finalList = new ArrayList<Item>();


		int[][] table = new int[itemList.size() + 1][capacity +1];
		boolean[][] take = new boolean[itemList.size()+1][capacity+1];


		for (int index = 0; index < table.length; index++ ) {
			for (int count = 0; count < table[index].length; count++) {
				table[index][count] = -1;
				take[index][count] = false;
			}
		}


		for (int index = 0; index < table[0].length; index++) {
			table[itemList.size()][index] = 0;
		}


		for (int index = table.length - 2; index >= 0; index--) {
			for (int count = 0; count < table[index].length; count++) {
				if (itemList.get(index).getWeight() <= count) {
					table[index][count] = Math.max(table[index + 1][count - itemList.get(index).getWeight()] + itemList.get(index).getValue() , table[index +1][count]);
					take[index][count] = true;
				} else {
					table[index][count] = table[index+1][count];
					take[index][count] = false;
				}
			}
		}


		return new Pair(table, take);
	}






	public Pair MinCost(ArrayList<Item> itemList) {
		ArrayList<Item> finalList = new ArrayList<Item>();

		Item aMax = findMax(itemList);


		int table[][] = new int[itemList.size()][(aMax.getValue())*itemList.size() +1];
		boolean take[][] = new boolean[itemList.size()][(aMax.getValue())*itemList.size() +1];

		for (int index = 0; index < table.length; index++) {
			for (int count = 0; count < table[index].length; count++) {
				table[index][count] = -1;
				take[index][count] = false;
				}
		}

		for (int index = 0; index < table.length; index++) {
			table[index][0] = 0;
		}


		for (int index = 1; index < itemList.get(0).getValue()+1; index ++) {
			table[0][index] = itemList.get(0).getWeight();
			take[0][index] = true;
		}

		for (int index = itemList.get(0).getValue()+1; index < table[0].length; index ++) {
			table[0][index] = 1000000000;
			take[0][index] = false;
		}



		int nextT = 0;

		for (int index = 1; index < table.length; index++) {
			for (int count = 1; count < table[index].length; count++) {
				nextT = Math.max(0, count - itemList.get(index).getValue());
				if (table[index -1][count] <= itemList.get(index).getWeight() + table[index-1][nextT]) {

					table[index][count] = table[index-1][count];
					take[index][count] = false;

				} else {
					table[index][count] = itemList.get(index).getWeight()+ table[index-1][nextT];
					take[index][count] = true;
				}


			}

		}


		return new Pair(table, take);
	}


	public ArrayList<Item> Greedy2Approx(ArrayList<Item> itemList, int capacity) {
		ArrayList<Item> finalList = new ArrayList<Item>();
		double[][] table = new double[2][itemList.size()];
		double[][] tableSorted = new double[2][itemList.size()];


		for (int index = 0; index < itemList.size(); index++) {
			table[1][index] = ((double) itemList.get(index).getValue()) / ((double) itemList.get(index).getWeight());
			table[0][index] = itemList.get(index).getIndex()-1;
 		}

		tableSorted = sort(table);


		int capacityLeft = capacity;
		for (int index = 0; index < tableSorted[1].length && capacityLeft >0; index ++) {

				if (itemList.get((int) tableSorted[0][index]).getWeight() <= capacityLeft) {
					finalList.add(itemList.get((int) tableSorted[0][index]));
					capacityLeft = capacityLeft - itemList.get((int) tableSorted[0][index]).getWeight();
				}

		}

		Item aMax = itemList.get((int) tableSorted[0][0]);
		int value = 0;
		for (int index = 0; index < finalList.size(); index++) {
			value += itemList.get(index).getValue();
		}

		return finalList;
	}




	public Item findMax (ArrayList<Item> itemList) {

		Item aMax = itemList.get(0);
		for (int index = 1; index < itemList.size(); index++) {
			if (itemList.get(index).getValue() > aMax.getValue()) {
				aMax = itemList.get(index);
			}
		}

		return aMax;
	}






	public double[][] sort(double[][] table) {
		double[][] sorted = new double[table.length][table[1].length];

		for (int index = 0; index < table[1].length; index++) {
			sorted[0][index] = table[0][index];
			sorted[1][index] = table[1][index];
		}

		for (int i = 1; i < sorted.length; i++) {
			for (int j = i; j > 0 && sorted[1][j] > sorted[1][j-1]; j--) {
				double temp = sorted[1][j];
				sorted[1][j] = sorted[1][j-1];
				sorted[1][j-1] = temp;
				temp = sorted[0][j];
				sorted[0][j] = sorted[0][j-1];
				sorted[0][j-1] = temp;
			}
		}


		return sorted;
	}





	public Pair FPTAS(ArrayList<Item> itemList, double errorT) {
		Item aMax = findMax(itemList);
		double floor = (1/ ((double) aMax.getValue())) * (itemList.size() / errorT);
		ArrayList<Item> finalList = new ArrayList<Item>();



		for (int index = 0; index < itemList.size(); index++) {
			Item current = itemList.get(index);
			Item temp = new Item((int)(current.getValue() * floor), current.getWeight(), current.getIndex());
			finalList.add(temp);
		}



		Pair result = MinCost(finalList);

		//This is not entirely correct.
		return result;

	}




}

