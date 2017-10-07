import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class TaskE {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int w = sc.nextInt();
		int h = sc.nextInt();

		int[][] map = new int[w][h];
		int[][] volume = new int[w][h];

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				// [y][x]
				map[j][i] = sc.nextInt();
				volume[j][i] = Math.abs(map[j][i]);
			}
		}

		int x = sc.nextInt();
		int y = sc.nextInt();

		int water = 0;

		//// INIT DONE /////
		
		Stack<Square> queue = new Stack<>();
		queue.add(new Square(x, y, Math.abs(map[y][x])));

		while (!queue.isEmpty()) {
			Square sq = queue.pop();
			if (map[sq.y][sq.x] < 0) {
				int deltaLevel = sq.neighborlevel - Math.abs(map[sq.y][sq.x]);
				int deltaWater = 0;
				if(deltaLevel < 0 ) {
					deltaWater = Math.max(0, sq.neighborlevel - Math.abs(map[sq.y][sq.x] + volume[sq.y][sq.x]));
				} else {
					deltaWater =  Math.max(0,volume[sq.y][sq.x] - Math.abs(map[sq.y][sq.x] + volume[sq.y][sq.x]));
				}
				
				water += deltaWater;
				volume[sq.y][sq.x] -= deltaWater;

				if (deltaWater > 0) {
					for (int k = -1; k < 2; k++) {
						for (int l = -1; l < 2; l++) {
							if (k == 0 && l == 0)
								continue;
							if ((sq.y + k) >= h || (sq.x + l) >= w || (sq.y + k) < 0 || (sq.x + l) < 0)
								continue;
							if (map[sq.y + k][sq.x + l] < 0) {
								queue.add(new Square(sq.x + l, sq.y + k, Math.abs(map[sq.y][sq.x] + volume[sq.y][sq.x])));
							}
						}
					}
				}
			}
		}

		System.out.println(water);

	}
	
	

	static class Square {
		int x, y, neighborlevel;

		Square(int x, int y, int neighborlevel) {
			this.x = x;
			this.y = y;
			this.neighborlevel = neighborlevel;
		}
	}
}
