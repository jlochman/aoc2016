import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final String INPUT_FILE = "input1.txt";

    static Scanner newInput() throws IOException {
	return new Scanner(new File(INPUT_FILE));
    }

    public static void main(String[] args) throws IOException {
	part1();
	part2();
    }

    private static void part2() throws IOException {
	try (Scanner in = newInput()) {
	    while (in.hasNextLine()) {
		String[] parts = in.nextLine().split("-");

		int key = Integer.parseInt(parts[parts.length - 1].replaceAll("[^\\d.]", ""));

		for (int i = 0; i < parts.length - 1; i++) {
		    if (shiftString(parts[i], key).contains("NORTHPOLE")) {
			System.out.println(key);
		    }
		}
	    }
	}
    }

    private static String shiftString(String s, int shift) {
	s = s.toUpperCase();
	char[] chars = s.toCharArray();
	for (int i = 0; i < chars.length; i++) {
	    chars[i] = (char) (((int) chars[i] + shift - 65) % 26 + 65);
	}
	return String.valueOf(chars);
    }

    private static void part1() throws IOException {
	try (Scanner in = newInput()) {
	    int result = 0;
	    while (in.hasNextLine()) {
		String[] parts = in.nextLine().split("-");

		Map<Integer, Integer> charMap = new HashMap<>();
		for (int i = 0; i < parts.length - 1; i++) {
		    parts[i].chars().forEach(ch -> {
			if (charMap.containsKey(ch)) {
			    charMap.replace(ch, charMap.get(ch) + 1);
			} else {
			    charMap.put(ch, 1);
			}
		    });
		}
		String checkSum = getCheckSum(charMap);

		String finalPart = parts[parts.length - 1];
		if (finalPart.contains(checkSum)) {
		    finalPart = finalPart.replaceAll("[^\\d.]", "");
		    result += Integer.parseInt(finalPart);
		}
	    }
	    System.out.println(result);
	}
    }

    private static String getCheckSum(Map<Integer, Integer> charMap) {
	Map<Integer, Integer> sortedMap = charMap.entrySet().stream().sorted((e1, e2) -> {
	    int result = Integer.compare(e1.getValue(), e2.getValue());
	    if (result != 0)
		return result;
	    return Integer.compare(e1.getKey(), e2.getKey());
	}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

	String s = "";
	int count = 0;
	Iterator<Entry<Integer, Integer>> iter = sortedMap.entrySet().iterator();
	while (iter.hasNext() && count < 5) {
	    Entry<Integer, Integer> entry = iter.next();
	    s += Character.toChars(entry.getKey())[0];
	    count++;
	}
	return s;
    }

}
