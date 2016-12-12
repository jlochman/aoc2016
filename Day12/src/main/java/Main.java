import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final String INPUT_FILE = "input1.txt";

    public static void main(String[] args) throws IOException {
	try (Stream<String> stream = Files.lines(Paths.get(INPUT_FILE))) {
	    Instructions instructions = new Instructions(stream.collect(Collectors.toList()));
	    instructions.initPart1();
	    instructions.process();
	    System.out.println(instructions.getValue("a"));

	    instructions.initPart2();
	    instructions.process();
	    System.out.println(instructions.getValue("a"));
	}

    }

    public static class Instructions {

	private List<String> instructions;
	private Map<String, Integer> registers = new HashMap<>();
	private int pos = 0;

	public Instructions(List<String> instructions) {
	    this.instructions = instructions;
	}

	public void initPart1() {
	    pos = 0;
	    registers.put("a", 0);
	    registers.put("b", 0);
	    registers.put("c", 0);
	    registers.put("d", 0);
	}

	public void initPart2() {
	    initPart1();
	    registers.put("c", 1);
	}

	public void process() {
	    while (pos < instructions.size()) {
		processLine();
	    }
	}

	public int getValue(String register) {
	    return registers.get(register);
	}

	private void processLine() {
	    Scanner scanner = new Scanner(instructions.get(pos));
	    String instruction = scanner.next();
	    switch (instruction) {
	    case "cpy":
		cpy(scanner.next(), scanner.next());
		break;
	    case "inc":
		inc(scanner.next());
		break;
	    case "dec":
		dec(scanner.next());
		break;
	    case "jnz":
		jnz(scanner.next(), scanner.next());
		break;
	    }
	    scanner.close();
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
		pos += Integer.parseInt(jump);
	    }
	}

    }

}
