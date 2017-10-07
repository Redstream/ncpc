import java.util.Scanner;

public class TaskJ {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int left = sc.nextInt();
		int right = sc.nextInt();
		
		if(left == 0 && right == 0) {
			System.out.println("Not a moose");
		} else if(left == right) {
			System.out.println("Even " + (left*2));
		} else {
			System.out.println("Odd " + (Math.max(left, right)*2));
		}
	}
}
