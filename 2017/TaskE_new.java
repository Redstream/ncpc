import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class TaskE_new {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int w = sc.nextInt();
        int h = sc.nextInt();

        int[][] map = new int[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // [y][x]
                map[j][i] = sc.nextInt();
            }
        }

        int x = sc.nextInt();
        int y = sc.nextInt();

        int depth;
        int water = 0;

        //// INIT DONE /////

        PriorityQueue<Square> queue = new PriorityQueue<>(new Cmp());
        queue.add(new Square(x, y, map[y][x]));

        while (!queue.isEmpty()) {
            Square sq = queue.poll();
            if (map[sq.y][sq.x] < 0) {
                depth = sq.depth;
                water += sq.depth;

                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        if (k == 0 && l == 0) {
                            // Don't add the square that we are on.
                            continue;
                        }
                        if ((sq.y + k) >= h || (sq.x + l) >= w || (sq.y + k) < 0 || (sq.x + l) < 0) {
                            // Don't add squares outside the map.
                            continue;
                        }

                        if (Math.abs(map[sq.y + k][sq.x + l]) <= depth) {
                            // Only add if depth of square is at least as deep as we can take.
                            queue.add(new Square(sq.x + l, sq.y + k, Math.abs(map[sq.y][sq.x])));
                        }
                    }
                }
            }
        }

        System.out.println(water);
    }


    static class Square {
        int x, y, depth;

        Square(int x, int y, int depth) {
            this.x = x;
            this.y = y;
            this.depth = depth;
        }
    }

    static class  Cmp implements Comparator<Square> {
        @Override
        public int compare(Square s1, Square s2) {
            return s1.depth - s2.depth;
        }
    }
}
