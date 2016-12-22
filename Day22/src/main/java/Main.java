import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {

    static final String INPUT_FILE = "input1.txt";
    static final int GRID_SIZE_X = 38;
    static final int GRID_SIZE_Y = 26;
    
    private static Set<DiskNode> nodes = new HashSet<>();

    static Scanner newInput() throws IOException {
	return new Scanner(new File(INPUT_FILE));
    }

    public static void main(String[] args) throws IOException {
	try (Scanner in = newInput()) {

	    in.nextLine();
	    in.nextLine();
	    while (in.hasNext()) {
		String line = in.nextLine().replaceAll("/dev/grid/node-x", "").replaceAll("-y", " ").replaceAll("T", "")
			.replaceAll("%", "");
		Scanner s = new Scanner(line);
		nodes.add(new DiskNode(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt()));
		s.close();
	    }

	    // part1
	    Supplier<Stream<DiskNode>> nodeSupplier = () -> nodes.stream();
	    System.out.println(nodeSupplier.get().filter(a -> a.used > 0)
		    .flatMap(a -> nodeSupplier.get().filter(b -> (!b.equals(a)) && b.avail >= a.used)).count());

	    // part2
	    AStarSearch search = new AStarSearch();
	    search.findPath(new AStarNode(nodes));
	}
    }

}
