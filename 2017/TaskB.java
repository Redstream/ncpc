import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TaskB {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int players = sc.nextInt();
		
		PriorityQueue<Player> snd = new PriorityQueue<>(new SndComparator());
		PriorityQueue<Player> fst = new PriorityQueue<>(new FstComparator());
		List<Player> fastestFst = new ArrayList<>();
		List<Player> fastestSnd = new ArrayList<>();
		
		for(int i = 0; i < players; i++) {
			String name = sc.next();
			double f = sc.nextDouble();
			double s = sc.nextDouble();
			Player p = new Player(name,f,s);
			snd.add(p);
			fst.add(p);
		}
		
		for(int i = 0; (i < 4) && (i < players); i++) {
			fastestFst.add(fst.poll());
			fastestSnd.add(snd.poll());
		}
		
		double bestScore = Double.MAX_VALUE;
		Player bestFstRunner = null;
		List<Player> bestSnds = null;
		
		for(int i = 0; i < fastestFst.size(); i++) {
			Player fstRunner = fastestFst.get(i);
			List<Player> team = getFastestThree(fastestSnd, fstRunner);
			double score;
			if( (score = calculateTime(fstRunner, team)) <= bestScore) {
				bestScore = score;
				bestFstRunner = fstRunner;
				bestSnds = team;
			}
		}
		
		System.out.println(bestScore);
		System.out.println(bestFstRunner.name);
		for(Player p : bestSnds) {
			System.out.println(p.name);
		}
	}
	
	static List<Player> getFastestThree(List<Player> sndRunners, Player fstRunner) {
		List<Player> team = new ArrayList<>();
		for(Player p : sndRunners) {
			if(p == fstRunner) {
				continue;
			}
			if(team.size() < 3) {
				team.add(p);
			}
		}
		return team;
	}
	
	static double calculateTime(Player fstRunner, List<Player> snds) {
		double time = fstRunner.fst;
		for(Player p: snds) {
			time += p.snd;
		}
		return time;
	}
	
	static class  Player {
		String name;
		double fst,snd;
		Player(String name, double fst, double snd) {
			this.name = name;
			this.fst = fst;
			this.snd = snd;
		}
		
	}
	
	static class  FstComparator implements Comparator<Player> {
		@Override
		public int compare(Player o1, Player o2) {
			if(o1.fst - o2.fst > 0) {
				return 1;
			} else {
				return -1;
			}
		}
	}	
	
	static class SndComparator implements Comparator<Player> {
		@Override
		public int compare(Player o1, Player o2) {
			if(o1.snd - o2.snd > 0) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	
}
