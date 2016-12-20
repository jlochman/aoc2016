import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String INPUT_FILE = "input1.txt";
    private static final Long MAX_IP = 4294967295L;
    private static List<Range> ranges = new ArrayList<>();

    static Scanner newInput() throws IOException {
	return new Scanner(new File(INPUT_FILE));
    }

    public static void main(String[] args) throws IOException {
	try (Scanner in = newInput()) {
	    while (in.hasNext()) {
		ranges.add(new Range(in.nextLong(), in.nextLong()));
	    }
	}

	int ipCount = 0;
	for (long i = 0; i <= MAX_IP; i++) {
	    final long ip = i;
	    Optional<Range> range = ranges.stream().filter(r -> r.from <= ip && r.to >= ip).findFirst();
	    if (range.isPresent()) {
		i = range.get().to;
	    } else {
		if (ipCount == 0) {
		    System.out.println(i);
		}
		ipCount++;
	    }
	}
	System.out.println(ipCount);
    }

    private static class Range {
	private long from;
	private long to;

	public Range(long from, long to) {
	    this.from = from;
	    this.to = to;
	}
    }

}
