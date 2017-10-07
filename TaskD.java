import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskD {

	
	static List<String>  combinations;
	static List<String>  playerslist = new ArrayList<>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int players = sc.nextInt();
		int features = sc.nextInt();
		combinations = createList(features);
		for(int i = 0; i < players; i++) {
			playerslist.add(sc.next());
		}
		
		int bestScore = 0;
		String bestString = "";
		
		for(int i = 0; i < combinations.size(); i++) {
			int worst = Integer.MAX_VALUE;
			for(int j = 0; j < players; j++) {
				int score;
				if((score = uniqueScore(combinations.get(i), playerslist.get(j))) < worst) {
					worst = score;
				}
			}
			if(worst >= bestScore) {
				bestScore = worst;
				bestString = combinations.get(i);
			}
		}
		
		System.out.println(bestString);
		
	}
	
	
	static int uniqueScore(String s1, String s2) {
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();
		int score = 0;
		for(int i = 0; i < s1.length(); i++) {
			if(c1[i] != c2[i]) {
				score ++;
			}
		}
		return score;
	}

	
	static List<String> createList(int size) {
		if(size == 1) {
			List<String> list = new ArrayList<>();
			list.add("0");
			list.add("1");
			return list;
		} else {
			List<String> list = new ArrayList<>();
			for(String s: createList(size - 1)) {
				list.add("0" + s);
				list.add("1" + s);
			}
			return list;
		}
	}
}
