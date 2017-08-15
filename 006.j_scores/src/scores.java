/*
 * Practice 006.j_Scores_level 1
 * Date 20170802
 */

import java.util.Scanner;
import java.util.Arrays;

public class scores {

	public static Scanner scanner = new Scanner(System.in);
	public static int[][] data;
	public static int status;
	
	public static void main(String[] args) {
		status = 1;
		//data[0][i]=english, data[1][i]=math, data[2][i]=average, 
		//data[3][i]=passOrNot, data[4][i]=rank
		data = new int [5][10]; 
		
		//Fill in all grades as -1.
		for (int i = 0; i < data[0].length; i++) {
			data[0][i] = -1;
			data[1][i] = -1;
		}
		
		//Run process
		do {
			switch (status){
			case 0: //Main options.
				System.out.print("Option: 1) Add. 2) Histogram. 3) Output. 4) Query. 5) Rank. -1) Quit:");
				int input = scanner.nextInt();
				if (input == 1)
					status = 1;
				else if (input == 2)
					status = 2;
				else if (input == 3)
					status = 3;
				else if (input == 4)
					status = 4;
				else if (input == 5)
					status = 5;
				else if (input == -1)
					status = -1;
				else
					System.out.println("Wrong input!");
				break;
			
			case 1: //Add scores.
				input();
				break;
				
			case 2: //Print histogram.
				System.out.print("1) English. 2) Math. -1) Back:");
				input = scanner.nextInt();
				if (input == -1)
					status = 0;
				else if (input == 1) {
					histogram(data[0]);
					status = 0;
				}
				else if (input == 2) {
					histogram(data[1]);
					status = 0;
				}
				else
					System.out.println("Wrong input!");
				break;
				
			case 3: //Output table.
				printTable();
				status = 0;
				break;
				
			case 4: //Query.
				System.out.print("1) English. 2) Math. 3) Average. -1) Back: ");
				input = scanner.nextInt();
				if (input == -1)
					status = 0;
				else if (input > 0 && input <4) {
					query(input);
					status = 0;
				}
				else 
					System.out.println("Wrong input!");
				break;
				
			case 5: //Rank.
				System.out.print("1) English. 2) Math. 3) Average. -1) Back:");
				input = scanner.nextInt();
				if (input == -1)
					status = 0;
				else if (input > 0 && input <4) {
					data[4] = rank(input);
					status = 0;
				}
				else 
					System.out.println("Wrong input!");
				printRankTable();
				status = 0;
				break;
			}
		} while (status != -1);
		
		System.out.print("Program terminate.");

	}
	
	public static void input() {
		System.out.print("Insert new data (SN/ENGLISH/MATH):");
		int sn = scanner.nextInt();
		int en = scanner.nextInt();
		int math = scanner.nextInt();
		
		if (sn == -1 && en == -1 && math == -1)
			status = 0;
		else if (en < 0 || en > 100 || math < 0 || math > 100)
			System.out.println("Scores input is out of boundary.");
		else {
			//If SN is more than array size, resize array.
			if (sn >= data[0].length) {
				int[][] temp_array = new int [4][sn + 10];
				for (int i = 0; i < 4; i++)
					System.arraycopy(data[i], 0, temp_array[i], 0, data[i].length);
				
				for (int i = data[0].length; i < (sn + 10); i++) {
					for (int j = 0; j < 4; j++)
						temp_array[j][i] = -1;
				}
				data = temp_array;
			}
			
			for (int i = 0; i <= sn; i++) {
				if (data[0][sn] != -1) {
					System.out.printf("Data %d duplicated!\n", sn);
					break;
				}
				if (i == sn ) {
					data[0][sn] = en;
					data[1][sn] = math;
					data[2][sn] = (int)((en + math) / 2);
					if (data[2][sn] > 60)
						data[3][sn] = 1;
				}
			}
		}
	}
	
	public static void histogram(int[] array) {
		//Calculate amounts.
		int[] count = new int[11];
		int temp;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != -1) {
				temp = array[i] / 10;
				count[temp]++;
			}
		}
		
		//Find maximum of count.
		int max = max(count);
		
		//Print histogram.
		newLine();
		for (int i = 0; i <= 100; i += 10)
			System.out.printf("%3d ", i);
		newLine();
		for (int i = 0; i < 44; i++)
			System.out.print("-");
		newLine();
		for (int i = 0; i < max; i++) {
			for (int j = 0; j < count.length; j++) {
				if (count[j] == 0) 
					System.out.print("    ");
				else {
					System.out.print("  * ");
					count[j]--;
				}
			}
			newLine();
		}
	}
	
	public static int max(int[] array) {
		int max_value = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max_value)
				max_value = array[i];
		}
		return max_value;
	}
	
	public static void printTable() {
		printTitle();
		
		String pass;
		for (int i = 0; i < data[0].length; i++) {
			pass = passOrNot(data[3][i]);
			//Print only inputs.
			if (data[0][i] != -1) 
				System.out.printf("%4d%6d%7d%6d%8s\n", i, data[0][i], data[1][i], data[2][i], pass);
		}
	}
	
	public static void newLine() {
		System.out.print("\n");
	}
	
	public static String passOrNot(int grade) {
		String result;
		if (grade == 1)
			result = "y";
		else 
			result = "n";
		return result;
	}
	
	public static void query(int item) {
		System.out.print("Start:");
		int start = scanner.nextInt();
		System.out.print("End:");
		int end = scanner.nextInt();
		
		printTitle();
		
		String pass;
		int total = 0;
		for (int i = 0; i < data[0].length; i++) {
			if (data[item - 1][i] > start && data[item - 1][i] < end) {
				pass = passOrNot(data[3][i]);
				System.out.printf("%4d%6d%7d%6d%8s\n", i, data[0][i], data[1][i], data[2][i], pass);
				total++;
			}
		}
		for (int i = 0; i < 33; i++)
			System.out.print("-");
		newLine();
		System.out.println("Total:" + total);
		newLine();
	}
	
	public static void printTitle() {
		newLine();
		String a = "SN";
		String b = "ENG.";
		String c = "MATH.";
		String d = "AVG.";
		String e = "PASSED";
		System.out.printf("%4s%7s%7s%6s%8s\n", a, b, c, d, e);
		for (int i = 0; i < 33; i++)
			System.out.print("-");
		newLine();
	}
	
	public static int[] rank(int item) {
		//temp[0][i]:Student number, temp[1][i]:item, temp[2][i]:rank
		int[][] temp = new int [3][data[0].length];
		for (int i = 0; i < temp[0].length; i++) {
			temp[0][i] = i;
			temp[1][i] = data[item - 1][i];
		}
		
		//Sort by item using bubble sorting.
		for (int i = 0; i < temp[0].length; i++) {
			for (int j = 0; j < temp[0].length - i - 1; j++) {
				if (temp[1][j] < temp[1][j + 1]) {
					int temp_0 = temp[0][j];
					int temp_1 = temp[1][j];
					temp[0][j] = temp[0][j + 1];
					temp[1][j] = temp[1][j + 1];
					temp[0][j + 1] = temp_0;
					temp[1][j + 1] = temp_1;
				}
			}
		}
		
		//Give ranks.
		int rank = 1;
		for (int i = 0; i < temp[0].length - 1; i++) {
			if (temp[1][i] > temp[1][i + 1])
				temp[2][i] = rank++;
			else {
				int count = 0;
				for (int j = i; j < temp[0].length - 1; j++) {
					if (temp[1][i] == temp[1][i + 1]) {
						count++;
						if (j == temp[0].length - 2)
							count++;
					}
					else
						break;
				}
				for (int j = i; j < i + count; j++) {
					temp[2][j] = rank;
				}
				rank += count;
				i += count - 1;
			}
		}
		
		//Sort by SN using bubble sorting.
		boolean tf = false;
		for (int i = 0; i < temp[0].length; i++) {
			for (int j = 0; j < temp[0].length - i - 1; j++) {
				tf = (temp[0][j] < temp[0][j + 1]);
				System.out.println(i +" "+j+" "+tf);
				if (temp[0][j] < temp[0][j + 1]) {
					int temp_0 = temp[0][j];
					int temp_1 = temp[1][j];
					int temp_2 = temp[2][j];
					temp[0][j] = temp[0][j + 1];
					temp[1][j] = temp[1][j + 1];
					temp[2][j] = temp[2][j + 1];
					temp[0][j + 1] = temp_0;
					temp[1][j + 1] = temp_1;
					temp[2][j + 1] = temp_2;
				}
			}
		}	
		
		//Return result array.
		return temp[2];
	}
	
	public static void printRankTitle() {
		newLine();
		String a = "SN";
		String b = "ENG.";
		String c = "MATH.";
		String d = "AVG.";
		String e = "Rank";
		System.out.printf("%4s%7s%7s%6s%7s\n", a, b, c, d, e);
		for (int i = 0; i < 33; i++)
			System.out.print("-");
		newLine();
	}
	
	public static void printRankTable() {
		printRankTitle();
		
		for (int i = 0; i < data[0].length; i++) {
			//Print only inputs.
			if (data[0][i] != -1) 
				System.out.printf("%4d%6d%7d%6d%7d\n", i, data[0][i], data[1][i], data[2][i], data[4][i]);
		}
	}

}
