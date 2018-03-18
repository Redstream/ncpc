import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskD {

	public static void main(String[] args) {
	    List<Player> playersAsArrays = new ArrayList<>();	//Holds all players
		Scanner sc = new Scanner(System.in);
		int players = sc.nextInt();							//Nr of players
		int features = sc.nextInt();						//Nr of features
		for(int i = 0; i < players; i++) {                    //Iterate all players
			char[] nextPlayerC = sc.next().toCharArray();    //Read as char[]
			int[] nextPlayerI = new int[features];            //Create new array for this player
			for (int j = 0; j < features; j++) {                //Parse features of player as ints
				nextPlayerI[j] = Character.getNumericValue(nextPlayerC[j]);    //Read feature as int and store it
			}
			playersAsArrays.add(new Player(i, nextPlayerI));                //Save array to list of players
		}

        int[] tirasPlayer = calculate(playersAsArrays, features);




		/*int[] tirasPlayer = new int[features];

		for(int i = 0; i < features; i++) {
		    tirasPlayer[i] = 0;
            int worstZSim = 0;

            for(Player p: playersAsArrays) {
                int sim = 0;
                for (int j = 0; j <= i; j++) {
                    if (p.fs[j] == tirasPlayer[j]) {
                        sim++;
                    }
                }
                if(sim > worstZSim) {
                    worstZSim = sim;
                }
            }

            tirasPlayer[i] = 1;
            int worstOSim = 0;

            for(Player p: playersAsArrays) {
                int sim = 0;
                for (int j = 0; j <= i; j++) {
                    if (p.fs[j] == tirasPlayer[j]) {
                        sim++;
                    }
                }
                if(sim > worstOSim) {
                    worstOSim = sim;
                }
            }

            if(worstOSim < worstZSim) {         //If placing a one here is the least bad thing to do, do it
                tirasPlayer[i] = 1;
            }
            else if (worstZSim < worstOSim){    //If placing a zero here is the least bad thing to do, do it
                tirasPlayer[i] = 0;
            }
            else {  //Equally bad => look at overall picture
                tirasPlayer[i] = 0;
                worstZSim = 0;

                for(Player p : playersAsArrays) {
                    for(int j = 0; j <= i; j++) {
                        if(p.fs[j] == tirasPlayer[j]) {
                            worstZSim++;
                        }
                    }
                }

                tirasPlayer[i] = 1;
                worstOSim = 0;

                for(Player p : playersAsArrays) {
                    for(int j = 0; j <= i; j++) {
                        if(p.fs[j] == tirasPlayer[j]) {
                            worstOSim++;
                        }
                    }
                }

                if(worstOSim < worstZSim) {
                    tirasPlayer[i] = 1;
                }
                else {
                    tirasPlayer[i] = 0;
                }
            }
        }*/

		for(int i : tirasPlayer) {
			System.out.print(i);
		}
		System.out.println("");
	}

	static class Player {
		int id;
		int[] fs;

		Player(int id, int[] fs) {
			this.id = id;
			this.fs = fs;
		}
	}

	static class Possibility {
	    int[] fs;
	    int sim;

	    Possibility(int[] fs, int sim) {
	        this.fs = fs;
	        this.sim = sim;
        }
    }

	static int[] calculate (List<Player> players, int features) {
        Integer minSimilarity = Integer.MAX_VALUE;

        return calculateHelp(players, new int[features],0, minSimilarity).fs;
    }


    static Possibility calculateHelp (List<Player> players, int[] possibility, int index, Integer minSimilarity) {
	    int similarity = 0;
	    if(index > 0 ) {                    //Calculate my similarity
            for (Player p : players) {
                int tempSim = 0;
                for(int i = 0; i < index; i++) {
                    if(p.fs[i] == possibility[i]) {
                        tempSim++;
                    }
                }
                if(tempSim > similarity) {
                    similarity = tempSim;
                }
            }
        }

        if(similarity >= minSimilarity) {    //If my similarity is already higher than the smallest one, return null
            return null;                    //Indicating that this option leads nowhere
        }

        if(index == possibility.length) {       //We're done
            if(similarity < minSimilarity) {    //If my similarity is the best one yet, set it and return
                minSimilarity = similarity;
                return new Possibility(possibility, similarity);
            }
            else {                              //If it's not better, return null
                return null;
            }
        }


	    boolean one = false;
	    boolean zero = false;

	    for(Player p : players) {
	        if(p.fs[index] == 1) {
	            one = true;
            }
            if(p.fs[index] == 0) {
	            zero = true;
            }
        }

        int[] pOne = possibility.clone();
        pOne[index] = 1;

        int[] pZero = possibility.clone();
        pZero[index] = 0;

        index++;

        if(one && zero) {
            Possibility retOne = calculateHelp(players, pOne, index, minSimilarity);
            Possibility retZero = calculateHelp(players, pZero, index, minSimilarity);

            if(retOne == null && retZero == null) {
                return null;
            }
            else if(retOne == null) {
                return retZero;
            }
            else if(retZero == null) {
                return retOne;
            }
            else {
                if(retOne.sim < retZero.sim) {
                    return retOne;
                }
                else {
                    return retZero;
                }
            }
        }
        else if(one) {
            return calculateHelp(players, pZero, index, minSimilarity);

        }
        else if(zero) {
            return calculateHelp(players, pOne, index, minSimilarity);
        }
        else {
            throw new IllegalArgumentException();
        }
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
