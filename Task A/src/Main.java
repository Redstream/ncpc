import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int distance = sc.nextInt();
		int speedWithoutCoffee = sc.nextInt();
		int speedWithCoffee = sc.nextInt();
		int coolDownTime = sc.nextInt();
		int drinkingTime = sc.nextInt();
		int nrOfCoffeeShops = sc.nextInt();
		int cooldownDistance = coolDownTime * speedWithoutCoffee;
		int speedDistance = speedWithCoffee * drinkingTime;

		List<Integer> positions = new ArrayList<>();
		System.out.println("Entering loop");
		for (int i = 0; i < nrOfCoffeeShops; i++) {
			positions.add(sc.nextInt());
		}

		System.out.println("Running!");

		int position = 0;
		int coffeeShopsVisited = 0;
		List<Integer> visitedShops = new ArrayList<>();

		if (nrOfCoffeeShops == 0) { // If there are no coffee shops
			System.out.println("0"); // Simply walk to the end at normal pace
			return;
		}

		position += positions.get(0); 					// Position of first coffee shop
		coffeeShopsVisited++; 							// Visit it
		visitedShops.add(0);
		position += cooldownDistance; 	// Position after cooldown
		position += speedDistance; 		// Position after drinking coffee

		if (position >= distance) { 		// We've reached the goal
			System.out.println(1);
			System.out.println(0);
			return;
		}
		while (position < distance) {
			int nextShop = -1;		//Position of next shop
			int indexNext = -1;
			int prevShop = -1;		//Position of prev shop
			int indexPrev = -1;
			for (int i = 0; i < positions.size(); i++) {	//Iterate through all shops
				int cofPos = positions.get(i); 				// Get current shop
				if (cofPos > position) { 					// If it's the next one
					if (i > 0) {
						prevShop = positions.get(i - 1); // Store previous
						indexPrev = i - 1;
					}
					nextShop = cofPos; // Store next one
					indexNext = i;
					break;
				}

			}

			int distanceToPrev = -1;
			if (prevShop > 0 && !visitedShops.contains(indexPrev)) {	//If there was a prev shop and we haven't visited it
				distanceToPrev = (position - prevShop);					//Calculate distance to it
			}
			int distanceToNext = -1;
			if (nextShop > 0 && !visitedShops.contains(indexNext)) {	//If there is a next shop and we haven't visited it
				distanceToNext = (nextShop - position);					//Calculate distance to it
			}

			boolean goToNext = false;									//Variables saying what to choose
			boolean goToPrev = false;
			if (distanceToPrev != -1 && distanceToNext != -1) {			//If we're in the middle of two unvisited shops
				//If we go to next, how much of the speed will we utilize?
				int remainingFromNext = distance - nextShop - cooldownDistance;
				int distWSpeedWnext = (speedDistance < remainingFromNext) ? speedDistance : remainingFromNext;

				//If we go to prev, how much of the speed will we utilize?
				int remainingFromPrev = distance - prevShop - cooldownDistance;
				int distWSpeedWprev = (speedDistance < remainingFromPrev) ? speedDistance : remainingFromPrev;

				if(distWSpeedWprev == distWSpeedWnext) {	//If both can be utilized fully
					//Go to the closest one
					if(distanceToNext < distanceToPrev) {
						goToNext = true;
					}
					else {
						goToPrev = true;
					}
				}
				else {
					distWSpeedWprev -= position - prevShop;		//Take into account that we threw some away
					if(distWSpeedWnext > distWSpeedWprev) {		//If we can utilize next more, go there
						goToNext = true;
					}
					else {	//If we can utilize prev more
						goToPrev = true;
					}
				}


				//Take maximum
			}
			else if (distanceToPrev == -1 && distanceToNext != -1) {	//If there's no shop behind us but one in front of us
				goToNext = true;
				//System.out.println("Going to next");
			}
			else if (distanceToNext == -1 && distanceToPrev != -1) {	//If there's a shop behind us but not in front of us
				int distWSpeedWoRef = position - distanceToPrev;	//This is the distance we'll travel at high speed if we don't refresh

				int remaining = distance - prevShop - cooldownDistance;	//Distance from prev to goal is distance - prevShop.
																		// Then we subtract the distance we travel at cooldown.
																		//What's remaining now is the distance from the cooldowns
																		// end to the goal.
				//If the distance we travel after one mug fits in remaining that's how long we'll travel at high speed
				//Else we will travel the remaining distance at high speed
				int distWSpeedWRef = (speedDistance < remaining) ? speedDistance : remaining;

				if(distWSpeedWoRef >  distWSpeedWRef) {		//If we can travel at higher speed for longer without refreshing, go to the goal
					position = distance;
				}
				else {		//If we can travel at higher speed for longer when choosing prev, go to prev
					goToPrev = true;
				}
				//System.out.println("Going to prev");
			}

			if (goToPrev) {
				coffeeShopsVisited++;
				visitedShops.add(indexPrev);
				position -= distanceToPrev;
				position += cooldownDistance;
				position += speedDistance;
			}
			else if (goToNext) {
				coffeeShopsVisited++;
				visitedShops.add(indexNext);
				position += distanceToNext;
				position += cooldownDistance;
				position += speedDistance;
			}
			else {	//Just finish
				position = distance;
			}
		}
		System.out.println(nrOfCoffeeShops);
		for (Integer i : visitedShops) {
			System.out.print(i + " ");
		}
	}
}
