import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    private static final String INPUT_FILE = "input1.txt";
    private static final int MAX_VALUE = 7;

    private static Maze maze = new Maze();
    private static Set<Distance> distances = new HashSet<>();

    public static void main(String[] args) throws IOException {
	try (Stream<String> stream = Files.lines(Paths.get(INPUT_FILE))) {

	    List<String> rows = stream.collect(Collectors.toList());
	    for (int row = 0; row < rows.size(); row++) {
		String sRow = rows.get(row);
		for (int col = 0; col < sRow.length(); col++) {
		    maze.add(sRow.charAt(col), row, col);
		}
	    }
	    maze.show();

	    getDistances();
	    System.out.println(distances.stream().map(d -> String.format("%d -> %d : %d", d.from, d.to, d.dist))
		    .collect(Collectors.joining("\n")));

	    List<Integer> intList = IntStream.range(0, MAX_VALUE + 1).boxed().collect(Collectors.toList());

	    int minDist = Integer.MAX_VALUE;
	    while (true) {
		int dist = 0;
		Collections.shuffle(intList);
		while (intList.get(0) != 0) {
		    Collections.shuffle(intList);
		}
		for (int i = 0; i < intList.size() - 1; i++) {
		    dist += getDistance(intList.get(i), intList.get(i + 1));
		}
		dist += getDistance(intList.get(intList.size() - 1), 0);

		if (dist < minDist) {
		    System.out.print(dist + ": ");
		    System.out
			    .println(intList.stream().map(i -> Integer.toString(i)).collect(Collectors.joining("->")));
		    minDist = dist;
		}
	    }
	}
    }

    private static void getDistances() {
	for (int i = 0; i <= MAX_VALUE; i++) {
	    for (int j = i + 1; j <= MAX_VALUE; j++) {
		distances.add(new Distance(i, j, maze.findPath(i, j)));
	    }
	}
    }

    private static int getDistance(int from, int to) {

	return distances.stream().filter(d -> d.from == Math.min(from, to) && d.to == Math.max(from, to)).findFirst()
		.get().dist;
    }

    private static class Distance {
	int from;
	int to;
	int dist;

	public Distance(int from, int to, int dist) {
	    this.from = from;
	    this.to = to;
	    this.dist = dist;
	}

	@Override
	public int hashCode() {
	    return 1000 * from + to;
	}
    }

}
