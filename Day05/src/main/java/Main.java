import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    private final static String INPUT = "abbhdwsy";

    public static void main(String[] args) throws NoSuchAlgorithmException {
	part2();
    }

    private static void part1() throws NoSuchAlgorithmException {
	MD5Generator generator = new MD5Generator();
	for (int i = 0; i < 8; i++) {
	    System.out.print(generator.findNext());
	}
    }

    private static void part2() throws NoSuchAlgorithmException {
	MD5Generator generator = new MD5Generator();
	System.out.println(generator.generatePart2());
    }

    private static class MD5Generator {

	private int counter = 0;
	private MessageDigest md5;

	public MD5Generator() throws NoSuchAlgorithmException {
	    md5 = MessageDigest.getInstance("MD5");
	}

	private String getNextHash() {
	    md5.update(StandardCharsets.UTF_8.encode(INPUT + counter));
	    counter++;
	    return String.format("%032x", new BigInteger(1, md5.digest()));
	}

	public String findNext() {
	    while (true) {
		String hash = getNextHash();
		if (hash.startsWith("00000")) {
		    return hash.substring(5, 6);
		}
	    }
	}

	public String generatePart2() {
	    String[] pwd = new String[8];
	    while (true) {
		String hash = getNextHash();
		if (hash.startsWith("00000") && hash.substring(5, 6).matches("[0-7]")) {
		    int pos = Integer.parseInt(hash.substring(5, 6));
		    if (pwd[pos] == null) {
			pwd[pos] = hash.substring(6, 7);
		    }
		    if (!Arrays.asList(pwd).contains(null)) {
			return Arrays.asList(pwd).stream().collect(Collectors.joining(""));
		    }
		}
	    }
	}
    }

}
