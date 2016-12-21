import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    private static final String INPUT_FILE = "input1.txt";
    private static final String INPUT = "abcdefgh";
    private static final String OUTPUT = "fbgdceah";

    private static List<String> inputList = new ArrayList<>();

    static Scanner newInput() throws IOException {
	return new Scanner(new File(INPUT_FILE));
    }

    public static void main(String[] args) throws IOException {
	try (Scanner in = newInput()) {
	    while (in.hasNextLine()) {
		inputList.add(in.nextLine());
	    }
	}
	System.out.println(getOutput(INPUT));

	String scrumbledS = "";
	String shuffle = "";
	while (!scrumbledS.equals(OUTPUT)) {
	    shuffle = shuffle(INPUT);
	    scrumbledS = getOutput(shuffle);
	}
	System.out.println(shuffle);
    }

    private static String shuffle(String input) {
	List<Character> characters = new ArrayList<>();
	for (char c : input.toCharArray()) {
	    characters.add(c);
	}
	StringBuilder output = new StringBuilder(input.length());
	while (characters.size() != 0) {
	    int randPicker = (int) (Math.random() * characters.size());
	    output.append(characters.remove(randPicker));
	}
	return output.toString();
    }

    private static String getOutput(String input) throws IOException {
	Scrambler scrambler = new Scrambler(input);
	for (String s : inputList) {
	    Scanner row = new Scanner(s);
	    switch (row.next()) {
	    case "swap":
		if (row.hasNextInt()) {
		    scrambler.swap(row.nextInt(), row.nextInt());
		} else {
		    scrambler.swap(row.next().charAt(0), row.next().charAt(0));
		}
		break;
	    case "reverse":
		scrambler.reverse(row.nextInt(), row.nextInt());
		break;
	    case "rotate":
		switch (row.next()) {
		case "left":
		    scrambler.rotate(-row.nextInt());
		    break;
		case "right":
		    scrambler.rotate(row.nextInt());
		    break;
		case "based":
		    scrambler.rotate(row.next().charAt(0));
		    break;
		}
		break;
	    case "move":
		scrambler.move(row.nextInt(), row.nextInt());
		break;
	    }
	    row.close();
	}
	return scrambler.show();
    }

    private static class Scrambler {
	private Set<Letter> letters = new HashSet<>();

	public Scrambler(String input) {
	    for (int i = 0; i < input.length(); i++) {
		letters.add(new Letter(input.charAt(i), i));
	    }
	}

	public void move(int fromPos, int toPos) {
	    Letter movLetter = letters.stream().filter(l -> l.getPos() == fromPos).findFirst().get();
	    if (fromPos < toPos) {
		letters.stream().filter(l -> l.getPos() > fromPos && l.getPos() <= toPos)
			.forEach(l -> l.setPos(l.getPos() - 1));
	    } else if (fromPos > toPos) {
		show();
		letters.stream().filter(l -> l.getPos() >= toPos && l.getPos() < fromPos)
			.forEach(l -> l.setPos(l.getPos() + 1));
		show();
	    }
	    movLetter.setPos(toPos);
	}

	public void rotate(int by) {
	    letters.stream().forEach(l -> l.setPos((l.getPos() + by + letters.size()) % letters.size()));
	}

	public void rotate(char ch) {
	    int charPos = letters.stream().filter(l -> l.getCh() == ch).findFirst().get().getPos();
	    rotate(1 + charPos + ((charPos >= 4) ? 1 : 0));
	}

	public void reverse(int pos1, int pos2) {
	    letters.stream().filter(l -> l.getPos() >= pos1 && l.getPos() <= pos2)
		    .forEach(l -> l.setPos(pos1 + pos2 - l.getPos()));
	}

	public void swap(char ch1, char ch2) {
	    letters.stream().filter(l -> l.getCh() == ch1 || l.getCh() == ch2)
		    .forEach(l -> l.setCh(l.getCh() == ch1 ? ch2 : ch1));
	}

	public void swap(int pos1, int pos2) {
	    letters.stream().filter(l -> l.getPos() == pos1 || l.getPos() == pos2)
		    .forEach(l -> l.setPos(l.getPos() == pos1 ? pos2 : pos1));
	}

	public String show() {
	    return letters.stream().sorted((l1, l2) -> Integer.compare(l1.getPos(), l2.getPos()))
		    .map(l -> Character.toString(l.getCh())).collect(Collectors.joining(""));
	}

    }

    private static class Letter {
	private char ch;
	private int pos;

	public Letter(char ch, int pos) {
	    this.ch = ch;
	    this.pos = pos;
	}

	public char getCh() {
	    return ch;
	}

	public void setCh(char ch) {
	    this.ch = ch;
	}

	public int getPos() {
	    return pos;
	}

	public void setPos(int pos) {
	    this.pos = pos;
	}

    }

}
