import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

	private static final String INPUT_FILE = "input1.txt";

	static Scanner newInput() throws IOException {
		return new Scanner(new File(INPUT_FILE));
	}

	public static void main(String[] args) throws IOException {
		Agent a = new Agent();
		try (Scanner in = newInput()) {
			Arrays.stream(in.nextLine().split(",")).forEach(s -> a.process(s));
		}
		System.out.println(a.getDistance());
	}

	private static class Agent {

		private int xPos;
		private int yPos;
		
		// 0 = N, 1 = E, 2 = S, 3 = W
		private int heading;

		private Set<Agent> agents = new HashSet<>();

		public void process(String input) {
			if (input.contains("L")) {
				turnLeft();
			} else if (input.contains("R")) {
				turnRight();
			}
			moveBy(Integer.parseInt(input.replaceAll("[^\\d.]", "")));
		}

		public int getDistance() {
			return Math.abs(xPos) + Math.abs(yPos);
		}

		private void moveBy(int i) {
			if (i == 0) {
				return;
			}
			switch (heading) {
			case 0:
				yPos += 1;
				break;
			case 1:
				xPos += 1;
				break;
			case 2:
				yPos -= 1;
				break;
			case 3:
				xPos -= 1;
				break;
			}
			addVisitedPos();
			moveBy(i - 1);
		}

		private void addVisitedPos() {
			if (agents.contains(this)) {
				System.out.println("I have found place visited twice: ");
				System.out.println(getDistance());
				System.exit(1);
			} else {
				agents.add(this);
			}
		}

		private void turnLeft() {
			heading = (heading + 1) % 4;
		}

		private void turnRight() {
			heading = (heading + 3) % 4;
		}

		@Override
		public int hashCode() {
			return 1000 * xPos + yPos;
		}

	}

}
