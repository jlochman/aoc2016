import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String INPUT_FILE = "input1.txt";

    static Scanner newInput() throws IOException {
	return new Scanner(new File(INPUT_FILE));
    }

    public static void main(String[] args) throws IOException {
	try (Scanner in = newInput()) {
	    List<String> cols = new ArrayList<>();
	    in.nextLine().chars().mapToObj(i -> String.valueOf((char) i)).forEach(s -> cols.add(s));
	    in.forEachRemaining(s -> {
		for (int i = 0; i < s.length(); i++) {
		    cols.set(i, cols.get(i) + String.valueOf((char) s.charAt(i)));
		}
	    });
	    for (String s : cols) {
		//System.out.print(getMostFrequentLetter(s));
		System.out.print(getLeastFrequentLetter(s));
	    }
	}
    }

    private static String getMostFrequentLetter(String s) {
	String result = "";
	long maxCount = Long.MIN_VALUE;
	long count = 0;
	for (int i = s.chars().min().getAsInt(); i <= s.chars().max().getAsInt(); i++) {
	    count = countCharInString(s, i);
	    if (count > maxCount) {
		maxCount = count;
		result = String.valueOf((char) i);
	    }
	}
	return result;
    }

    private static String getLeastFrequentLetter(String s) {
	String result = "";
	long minCount = Long.MAX_VALUE;
	long count = 0;
	for (int i = s.chars().min().getAsInt(); i <= s.chars().max().getAsInt(); i++) {
	    count = countCharInString(s, i);
	    if (count < minCount && count > 0) {
		minCount = count;
		result = String.valueOf((char) i);
	    }
	}
	return result;
    }

    private static long countCharInString(String s, int ch) {
	return s.chars().filter(c -> c == ch).summaryStatistics().getCount();
    }

}
