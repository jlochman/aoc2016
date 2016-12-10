import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    private static final String INPUT_FILE = "input1.txt";

    public static void main(String[] args) throws IOException {
	try (Stream<String> stream = Files.lines(Paths.get(INPUT_FILE))) {
	    String s = stream.findFirst().get();
	    System.out.println(process(s, false));
	    System.out.println(process(s, true));
	}
    }

    private static Long process(String s, boolean recursiveProcessing) {
	long finalLength = 0;
	while (s.length() > 0) {
	    String[] parts = s.split("\\(|x|\\)", 4);
	    if (parts.length == 1) {
		finalLength += parts[0].length();
		break;
	    } else if (parts.length > 1) {
		int copyLength = Integer.parseInt(parts[1]);
		int copyCount = Integer.parseInt(parts[2]);
		finalLength += parts[0].length() + copyCount
			* (recursiveProcessing ? process(parts[3].substring(0, copyLength), true) : copyLength);
		s = parts[3].substring(copyLength);
	    }
	}
	return finalLength;
    }

}
