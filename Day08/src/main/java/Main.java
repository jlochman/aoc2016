import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class Main {

    private static final String INPUT_FILE = "input1.txt";

    private final static int NUM_COLS = 50;
    private final static int NUM_ROWS = 6;

    public static void main(String[] args) throws IOException {
	try (Stream<String> stream = Files.lines(Paths.get(INPUT_FILE))) {
	    Display display = new Display();
	    stream.forEach(s -> display.process(s));
	    display.showOnCount();
	    display.show();
	}
    }

    private static class Display {
	private Set<Pixel> pixels = new HashSet<>();

	public Display() {
	    for (int row = 0; row < NUM_ROWS; row++) {
		for (int col = 0; col < NUM_COLS; col++) {
		    pixels.add(new Pixel(row, col));
		}
	    }
	}

	public void process(String s) {
	    Scanner scanner = new Scanner(s);
	    String firstWord = scanner.next();
	    if (firstWord.equals("rect")) {
		rect(scanner.nextInt(), scanner.nextInt());
	    } else if (firstWord.equals("rotate")) {
		String secondWord = scanner.next();
		if (secondWord.equals("row")) {
		    rotateRow(scanner.nextInt(), scanner.nextInt());
		} else if (secondWord.equals("column")) {
		    rotateCol(scanner.nextInt(), scanner.nextInt());
		}
	    }
	    scanner.close();
	}

	public void show() {
	    String[][] visual = new String[NUM_ROWS][NUM_COLS];
	    pixels.stream().forEach(p -> visual[p.getRow()][p.getCol()] = p.isOn() ? "x" : " ");
	    for (int i = 0; i < NUM_ROWS; i++) {
		System.out.println(String.join("", visual[i]));
	    }
	}

	public void showOnCount() {
	    System.out.println(pixels.stream().filter(p -> p.isOn()).count());
	}

	private void rect(int cols, int rows) {
	    pixels.stream().filter(p -> (p.getCol() < cols) && (p.getRow() < rows)).forEach(p -> p.turnOn());
	}

	private void rotateRow(int row, int by) {
	    pixels.stream().filter(p -> (p.getRow() == row)).forEach(p -> p.setCol(p.getCol() + by));
	}

	private void rotateCol(int col, int by) {
	    pixels.stream().filter(p -> (p.getCol() == col)).forEach(p -> p.setRow(p.getRow() + by));
	}

    }

    private static class Pixel {
	private int row;
	private int col;
	private boolean on;

	public Pixel(int row, int col) {
	    setRow(row);
	    setCol(col);
	}

	public int getRow() {
	    return row;
	}

	public int getCol() {
	    return col;
	}

	public boolean isOn() {
	    return on;
	}

	public void turnOn() {
	    this.on = true;
	}

	public void setRow(int row) {
	    this.row = row % NUM_ROWS;
	}

	public void setCol(int col) {
	    this.col = col % NUM_COLS;
	}

    }

}
