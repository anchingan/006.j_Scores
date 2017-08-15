/*
 * Practice 006.j_Scores_level 1
 * Date 20170802
 */

import java.util.Scanner;
import java.util.Arrays;

public class scores {

	public static Scanner scanner = new Scanner(System.in);
	public static int[][] data;
	public static int status, count;
	
	public static void main(String[] args) {
		status = 1;
		count = 0;
		//data[i][0]=Student number, data[i][1]=English, data[i][2]=math, 
		//data[i][3]=average, data[i][4]=passOrNot, data[i][5] = rank
		data = new int [10][6]; 
		
		//Fill in all grades as -1.
		for (int i = 0; i < data.length; i++) {
			data[i][1] = -1;
			data[i][2] = -1;
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
					histogram(data, 1);
					status = 0;
				}
				else if (input == 2) {
					histogram(data, 2);
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
					rank(input);
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
			if (count >= data.length) {
				int[][] temp_array = new int [count * 2][6];
				for (int i = 0; i < count; i++)
					System.arraycopy(data[i], 0, temp_array[i], 0, data[i].length);
				data = null;
				data = temp_array;
			}
			
			for (int i = 0; i <= count; i++) {

				if (data[i][0] == sn) {
					System.out.printf("Data %d duplicated!\n", sn);
					break;
				}
				else if (i == count - 1 || count == 0) {
					data[count][0] = sn;
					data[count][1] = en;
					data[count][2] = math;
					data[count][3] = (int)((en + math) / 2);
					if (data[count][3] > 60)
						data[count][4] = 1;
					count++;
					break;
				}

			}
		}
	}
	
	public static void histogram(int[][] array, int index) {
		//Calculate amounts.
		int[] freq = new int[11];
		int temp;
		for (int i = 0; i < count; i++) {
			temp = array[i][index] / 10;
			freq[temp]++;
		}
		
		//Find maximum of count.
		int max = max(freq);
		
		//Print histogram.
		newLine();
		for (int i = 0; i <= 100; i += 10)
			System.out.printf("%3d ", i);
		newLine();
		for (int i = 0; i < 44; i++)
			System.out.print("-");
		newLine();
		for (int i = 0; i < max; i++) {
			for (int j = 0; j < freq.length; j++) {
				if (freq[j] == 0) 
					System.out.print("    ");
				else {
					System.out.print("  * ");
					freq[j]--;
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
		for (int i = 0; i < count; i++) {
			pass = passOrNot(data[i][4]);
			//Print only inputs.
			if (data[i][1] != -1) 
				System.out.printf("%4d%6d%7d%6d%8s\n", data[i][0], data[i][1], data[i][2], data[i][3], pass);
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
		for (int i = 0; i < count; i++) {
			if (data[i][item] > start && data[i][item] < end) {
				pass = passOrNot(data[i][4]);
				System.out.printf("%4d%6d%7d%6d%8s\n", data[i][0], data[i][1], data[i][2], data[i][3], pass);
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
	
	public static void rank(int item) {
		//Assort by item using bubble sort.
		int[] temp = new int[6];
		
		for (int i = 0; i < count - 1; i++) {
			for (int j = i; j < count; j++) {
				if (data[i][item] < data[j][item]) {
					temp = data[i];
					data[i] = data[j];
					data[j] = temp;
				}
			}
		}
		//Give ranks.
		int rank = 1;
		for (int i = 0; i < count; i++) {
			if (data[i][item] > data[i + 1][item])
				data[i][5] = rank++;
			else {
				int num = 0;
				for (int j = i; j < count - 1; j++) {
					if (data[j][item] == data[j + 1][item]) {
						num++;
					}
					else
						break;
				}
				for (int j = i; j <= i + num; j++) {
					data[j][5] = rank;
				}
				rank += num + 1;
				i += num;
			}
		}
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
		
		for (int i = 0; i < count; i++) {
			System.out.printf("%4d%6d%7d%6d%7d\n", data[i][0], data[i][1], data[i][2], data[i][3], data[i][5]);
		}
	}

}
