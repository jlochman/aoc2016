import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class Main {

    private static final String INPUT_FILE = "input1.txt";

    public static void main(String[] args) throws IOException {
	try (Stream<String> stream = Files.lines(Paths.get(INPUT_FILE))) {
	    Squadron squadron = new Squadron();
	    stream.forEach(s -> squadron.process(s));
	    squadron.calculate();
	}

    }

    private static class Squadron {

	private Set<SimpleInput> simpleInputs = new HashSet<>();
	private Map<Integer, Bot> bots = new HashMap<>();
	private Map<Integer, Output> outputs = new HashMap<>();

	public void process(String s) {
	    Scanner scanner = new Scanner(s);
	    String firstWord = scanner.next();
	    if (firstWord.equals("value")) {
		createSimpleInput(scanner.nextInt(), scanner.nextInt());
	    } else if (firstWord.equals("bot")) {
		int giverID = scanner.nextInt();
		if (scanner.next().equals("bot")) {
		    giveLowToBot(giverID, scanner.nextInt());
		} else {
		    giveLowToOutput(giverID, scanner.nextInt());
		}
		if (scanner.next().equals("bot")) {
		    giveHighToBot(giverID, scanner.nextInt());
		} else {
		    giveHighToOutput(giverID, scanner.nextInt());
		}
	    }
	    scanner.close();
	}

	public void calculate() {
	    simpleInputs.stream().forEach(s -> s.process());

	    while (bots.values().stream().filter(s -> !s.isProcessed()).count() > 0) {
		bots.values().stream().filter(s -> !s.isProcessed()).filter(s -> s.canProcess())
			.forEach(s -> s.process());
	    }

	    // part1
	    bots.values().stream().filter(b -> b.containsChip(61) && b.containsChip(17))
		    .forEach(b -> System.out.println(b.getId()));

	    // part2
	    System.out.println(outputs.values().stream().filter(o -> o.getId() <= 2)
		    .map(o -> o.getChips().stream().reduce(1, Math::multiplyExact)).reduce(1, Math::multiplyExact));

	}

	private void createSimpleInput(int chip, int toBotID) {
	    SimpleInput simpleInput = new SimpleInput();
	    simpleInput.addChip(chip);
	    simpleInput.setToStorage(getBot(toBotID));
	    simpleInputs.add(simpleInput);
	}

	private void giveLowToBot(int giverId, int recieverId) {
	    getBot(giverId).setLowStorage(getBot(recieverId));
	}

	private void giveLowToOutput(int giverId, int recieverId) {
	    getBot(giverId).setLowStorage(getOutput(recieverId));
	}

	private void giveHighToBot(int giverId, int recieverId) {
	    getBot(giverId).setHighStorage(getBot(recieverId));
	}

	private void giveHighToOutput(int giverId, int recieverId) {
	    getBot(giverId).setHighStorage(getOutput(recieverId));
	}

	private Bot getBot(int id) {
	    Bot bot = bots.get(id);
	    if (bot == null) {
		bot = new Bot(id);
		bots.put(id, bot);
	    }
	    return bot;
	}

	private Storage getOutput(int id) {
	    Output output = outputs.get(id);
	    if (output == null) {
		output = new Output(id);
		outputs.put(id, output);
	    }
	    return output;
	}

    }

    private static class Storage {

	protected Set<Integer> chips = new HashSet<>();

	public void addChip(int chip) {
	    chips.add(chip);
	}

	public boolean containsChip(int chip) {
	    return chips.contains(chip);
	}

	public Set<Integer> getChips() {
	    return chips;
	}

    }

    private static class SimpleInput extends Storage {

	private Storage toStorage;

	public void process() {
	    toStorage.addChip(chips.iterator().next());
	}

	public void setToStorage(Storage toStorage) {
	    this.toStorage = toStorage;
	}

    }

    private static class Bot extends Storage {

	private Storage lowStorage;
	private Storage highStorage;
	private boolean processed;
	private int id;

	public Bot(int id) {
	    this.id = id;
	}

	public boolean canProcess() {
	    return chips.size() == 2;
	}

	public void process() {
	    lowStorage.addChip(chips.stream().min((i1, i2) -> Integer.compare(i1, i2)).get());
	    highStorage.addChip(chips.stream().max((i1, i2) -> Integer.compare(i1, i2)).get());
	    processed = true;
	}

	public void setLowStorage(Storage lowStorage) {
	    this.lowStorage = lowStorage;
	}

	public void setHighStorage(Storage highStorage) {
	    this.highStorage = highStorage;
	}

	public boolean isProcessed() {
	    return processed;
	}

	public int getId() {
	    return id;
	}

    }

    private static class Output extends Storage {

	private int id;

	public Output(int id) {
	    this.id = id;
	}

	public int getId() {
	    return id;
	}

    }

}
