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

	private Map<Integer, Instruction> instructions = new HashMap<>();
	private Map<String, Integer> registers = new HashMap<>();
	private int pos = 0;

	public Instructions(List<String> instructions) {
	    for (int i = 0; i < instructions.size(); i++) {
		Scanner scanner = new Scanner(instructions.get(i));
		this.instructions.put(i, new Instruction(InstructionType.valueOf(scanner.next()), scanner.next(),
			scanner.hasNext() ? scanner.next() : ""));
		scanner.close();
	    }
	}

	public void initPart1() {
	    pos = 0;
	    instructions.values().stream().forEach(i -> i.setToggled(false));
	    registers.put("a", 7);
	    registers.put("b", 0);
	    registers.put("c", 0);
	    registers.put("d", 0);
	}

	public void initPart2() {
	    initPart1();
	    registers.put("a", 12);
	}

	public void process() {
	    while (pos < instructions.size()) {
		// System.out.println(instructions.get(pos).asString());
		processLine();
		// System.out.println(getValue("a"));
	    }
	}

	public int getValue(String register) {
	    return registers.get(register);
	}

	private void processLine() {
	    switch (instructions.get(pos).getType()) {
	    default:
		instructions.get(pos).setToggled(false);
	    case cpy:
		cpy(instructions.get(pos).getPar1(), instructions.get(pos).getPar2());
		break;
	    case inc:
		inc(instructions.get(pos).getPar1());
		break;
	    case dec:
		dec(instructions.get(pos).getPar1());
		break;
	    case jnz:
		jnz(instructions.get(pos).getPar1(), instructions.get(pos).getPar2());
		break;
	    case tgl:
		tgl(instructions.get(pos).getPar1());
		break;
	    }
	}

	private void cpy(String source, String dest) {
	    try {
		Integer.parseInt(dest);
		pos++;
		return;
	    } catch (Exception e) {
		// proceed
	    }
	    int src;
	    try {
		src = Integer.parseInt(source);
	    } catch (Exception e) {
		src = registers.get(source);
	    }
	    registers.put(dest, src);
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
	    int jmp;
	    try {
		jmp = Integer.parseInt(jump);
	    } catch (Exception e) {
		jmp = registers.get(jump);
	    }
	    if (value == 0) {
		pos++;
	    } else {
		pos += jmp;
	    }
	}

	private void tgl(String s) {
	    int value;
	    try {
		value = Integer.parseInt(s);
	    } catch (Exception e) {
		value = registers.get(s);
	    }
	    if (pos + value < instructions.size()) {
		instructions.get(pos + value).setToggled(true);
	    }
	    pos++;
	}

    }

}
