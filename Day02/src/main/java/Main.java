import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	private static final String INPUT_FILE = "input1.txt";

	static Scanner newInput() throws IOException {
		return new Scanner(new File(INPUT_FILE));
	}

	private static void part1() throws IOException {
		Keyboard keyboard = new Keyboard();
		try (Scanner in = newInput()) {
			in.forEachRemaining(s -> {
				s.codePoints().forEach(codePoint -> keyboard.process(Character.toChars(codePoint)[0]));
				System.out.println("____");
				keyboard.print();
			});

		}
	}

	public static void main(String[] args) throws IOException {
		AdvancedKeyboard keyboard = new AdvancedKeyboard();
		try (Scanner in = newInput()) {
			in.forEachRemaining(s -> {
				s.codePoints().forEach(codePoint -> keyboard.process(Character.toChars(codePoint)[0]));
				keyboard.print();
				System.out.println();
			});

		}

	}

	private static class AdvancedKeyboard {
		private int xPos = -2;
		private int yPos;

		public void process(char ch) {
			switch (ch) {
			case 'U':
				moveUp();
				break;
			case 'R':
				moveRight();
				break;
			case 'D':
				moveDown();
				break;
			case 'L':
				moveLeft();
				break;
			}
		}

		private boolean inRange(int x, int y) {
			return (Math.abs(x) + Math.abs(y)) <= 2;
		}

		private void moveUp() {
			int yNew = yPos + 1;
			if (inRange(xPos, yNew)) {
				yPos = yNew;
			}
		}

		private void moveRight() {
			int xNew = xPos + 1;
			if (inRange(xNew, yPos)) {
				xPos = xNew;
			}
		}

		private void moveDown() {
			int yNew = yPos - 1;
			if (inRange(xPos, yNew)) {
				yPos = yNew;
			}
		}

		private void moveLeft() {
			int xNew = xPos - 1;
			if (inRange(xNew, yPos)) {
				xPos = xNew;
			}
		}

		public void print() {
			System.out.format("[%d,%d]", xPos, yPos);
		}
	}

	private static class Keyboard {

		/*
		 * 1 2 3 4 5 6 7 8 9
		 * 
		 * 5 is on [0,0]
		 */

		private int xPos;
		private int yPos;

		public void process(char ch) {
			switch (ch) {
			case 'U':
				moveUp();
				break;
			case 'R':
				moveRight();
				break;
			case 'D':
				moveDown();
				break;
			case 'L':
				moveLeft();
				break;
			}
		}

		private void moveUp() {
			yPos = Math.max(yPos - 1, -1);
		}

		private void moveRight() {
			xPos = Math.min(xPos + 1, 1);
		}

		private void moveDown() {
			yPos = Math.min(yPos + 1, 1);
		}

		private void moveLeft() {
			xPos = Math.max(xPos - 1, -1);
		}

		public void print() {
			System.out.println((yPos + 1) * 3 + (xPos + 1) + 1);
		}

	}

}
