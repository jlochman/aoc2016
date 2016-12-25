import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final String INPUT_FILE = "input1.txt";
    private static Instructions instructions;
    private static final int NUM_TEST = 20;

    public static void main(String[] args) throws IOException {
	try (Stream<String> stream = Files.lines(Paths.get(INPUT_FILE))) {
	    instructions = new Instructions(stream.collect(Collectors.toList()));

	    int seed = 1;
	    instructions.init(seed);
	    while (!passed()) {
		seed++;
		System.out.println("\n trying seed: " + seed);
		instructions.init(seed);
	    }
	    System.out.println("\n result: " + seed);
	}
    }

    private static boolean passed() {
	int result = 1;
	for (int i = 0; i < NUM_TEST; i++) {
	    int newResult = instructions.process();
	    System.out.print(newResult);
	    if (!(newResult == 0 || newResult == 1)) {
		return false;
	    }
	    if (result == 1 && newResult != 0) {
		return false;
	    }
	    if (result == 0 && newResult != 1) {
		return false;
	    }
	    result = newResult;
	}
	return true;
    }

    public static class Instructions {

	private Map<Integer, List<String>> instructions = new HashMap<>();
	private Map<String, Integer> registers = new HashMap<>();
	private int pos;

	public Instructions(List<String> instructions) {
	    for (int i = 0; i < instructions.size(); i++) {
		Scanner scanner = new Scanner(instructions.get(i));
		List<String> list = new ArrayList<>();
		list.add(scanner.next());
		list.add(scanner.next());
		if (scanner.hasNext()) {
		    list.add(scanner.next());
		}
		this.instructions.put(i, list);
		scanner.close();
	    }
	}

	public void init(int seed) {
	    pos = 0;
	    registers.put("a", seed);
	    registers.put("b", 0);
	    registers.put("c", 0);
	    registers.put("d", 0);
	}

	public int getValue(String register) {
	    return registers.get(register);
	}

	public int process() {
		switch (instructions.get(pos).get(0)) {
		case "cpy":
		    cpy(instructions.get(pos).get(1), instructions.get(pos).get(2));
		    break;
		case "inc":
		    inc(instructions.get(pos).get(1));
		    break;
		case "dec":
		    dec(instructions.get(pos).get(1));
		    break;
		case "jnz":
		    jnz(instructions.get(pos).get(1), instructions.get(pos).get(2));
		    break;
		case "out":
		    pos++;
		    return registers.get(instructions.get(pos - 1).get(1));
		}
		return process();


	}

	private void cpy(String source, String dest) {
	    int value;
	    try {
		value = Integer.parseInt(source);
	    } catch (Exception e) {
		value = registers.get(source);
	    }
	    registers.put(dest, value);
	    pos++;
	}

	private void inc(String s) {
	    registers.put(s, registers.get(s) + 1);
	    pos++;
	}

	private void dec(String s) {
	    registers.put(s, registers.get(s) - 1);
	    pos++;
	}

	private void jnz(String s, String jump) {
	    int value;
	    try {
		value = Integer.parseInt(s);
	    } catch (Exception e) {
		value = registers.get(s);
	    }
	    if (value == 0) {
		pos++;
	    } else {
		int jmp = Integer.parseInt(jump);
		pos += (jmp != 0) ? jmp : 1;
	    }
	}

    }

}
