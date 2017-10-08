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

		List<Integer> positions = new ArrayList<>();
		int cooldownDistance = coolDownTime * speedWithoutCoffee;
		int speedDistance = speedWithCoffee * drinkingTime;
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

		position += positions.get(0); // Position of first coffee shop
		coffeeShopsVisited++; // Visit it
		visitedShops.add(0);
		position += speedWithoutCoffee * coolDownTime; // Position after first
														// coffee shop and
														// cooldown
		position += speedWithCoffee * drinkingTime; // Position after drinking
													// first coffee

		if (position > distance) { // We've reached the goal
			System.out.println(0);
			System.out.println(0);
			return;
		} else {
			while (true) {
				int nextShop = -1;
				int indexNext = -1;
				int prevShop = -1;
				int indexPrev = -1;
				for (int i = 0; i < positions.size(); i++) {
					int cofPos = positions.get(i); // Get curent shop
					if (cofPos > position) { // If it's the next one
						if (i > 0) {
							prevShop = positions.get(i - 1); // Store previous
																// one
							indexPrev = i - 1;
						}
						nextShop = cofPos; // Store next one
						indexNext = i;
						break;
					}

				}

				int distanceToPrev = -1;
				if (prevShop > 0 && !visitedShops.contains(indexPrev)) {
					distanceToPrev = (position - prevShop);
				}
				int distanceToNext = -1;
				if (nextShop > 0 && !visitedShops.contains(indexNext)) {
					distanceToNext = (nextShop - position);
				}

				boolean goToNext = false;
				boolean goToPrev = false;
				if (distanceToPrev != -1 && distanceToNext != -1) {
					if (distanceToPrev < distanceToNext) {
						goToPrev = true;
						System.out.println("Going to next");
					} else {
						goToNext = true;
						System.out.println("Going to prev");
					}
				} else if (distanceToPrev == -1 && distanceToNext != -1) {
					goToNext = true;
					System.out.println("Going to next");
				} else if (distanceToNext == -1 && distanceToPrev != -1) {
					int distanceToGoal = distance - position;
					int timeFromPrevToGoalWithoutRefresh = (distanceToGoal * speedWithoutCoffee)
							+ (distanceToPrev * speedWithCoffee);
					int distanceToGoalAfterCooldown = (distance - prevShop) - cooldownDistance;
					int timeFromPrevToGoalWithRefresh = (distanceToGoalAfterCooldown * speedWithCoffee) + coolDownTime;
					if (distanceToGoalAfterCooldown > 0) {
						if (timeFromPrevToGoalWithRefresh < timeFromPrevToGoalWithoutRefresh) {
							goToPrev = true;
						}
					}
					System.out.println("Going to prev");
				}

				if (goToPrev) {
					coffeeShopsVisited++;
					visitedShops.add(indexPrev);
					position -= distanceToPrev;
					position += cooldownDistance;
					position += speedDistance;
				} else if (goToNext) {
					coffeeShopsVisited++;
					visitedShops.add(indexNext);
					position += distanceToNext;
					position += cooldownDistance;
					position += speedDistance;
				} else {
					System.out.println(nrOfCoffeeShops);
					for(Integer i : visitedShops) {
						System.out.print(i + " ");
					}
					break;
				}

			}
		}

	}
}
