import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private final static String INPUT = "zpqevtbw";
    private final static int HASH_LIMIT = 5000;
    private final static int xTH_PAD = 64;

    private static Supplier<Stream<Hash>> stream3;
    private static Supplier<Stream<Hash>> stream5;
    private static Supplier<Stream<Hash>> pads;

    public static void main(String[] args) throws NoSuchAlgorithmException {
	System.out.println(getResult(true));
	System.out.println(getResult(false));
    }

    private static String getResult(boolean part1) throws NoSuchAlgorithmException {
	MD5Generator generator = new MD5Generator();
	List<Hash> hash3List = new ArrayList<>();
	for (int i = 0; i < HASH_LIMIT; i++) {
	    hash3List.add(generator.getNextHash(part1 ? 0 : 2016));
	    if (i % 100 == 0) {
		stream3 = () -> hash3List.stream();
		stream5 = () -> stream3.get().filter(h3 -> h3.contains5());
		pads = () -> stream5.get()
			.flatMap(h5 -> stream3.get()
				.filter(h3 -> h5.getPosition() - h3.getPosition() > 0
					&& h5.getPosition() - h3.getPosition() <= 1000
					&& h5.getHash().contains(h3.getString5())));
		if (pads.get().count() >= xTH_PAD) {
		    return pads.get().sorted((h1, h2) -> Integer.compare(h1.getPosition(), h2.getPosition()))
			    .limit(xTH_PAD).collect(Collectors.toList()).get(xTH_PAD - 1).asString();
		}
	    }
	}
	return "found only " + pads.get().count() + " pads";
    }

    private static class MD5Generator {
	private MessageDigest md5;
	private int counter = -1;
	private static Pattern pattern3 = Pattern.compile("([a-f\\d])\\1{2}");

	public MD5Generator() throws NoSuchAlgorithmException {
	    md5 = MessageDigest.getInstance("MD5");
	}

	public Hash getNextHash(int recursion) {
	    counter++;
	    String hash = getHash(INPUT + counter);
	    for (int i = 0; i < recursion; i++) {
		hash = getHash(hash);
	    }
	    Matcher m = pattern3.matcher(hash);
	    return m.find() ? new Hash(hash, counter, m.group(1).charAt(0)) : getNextHash(recursion);
	}

	public String getHash(String s) {
	    md5.update(StandardCharsets.UTF_8.encode(s));
	    return String.format("%032x", new BigInteger(1, md5.digest()));
	}

    }

    private static class Hash {
	private String hash;
	private int position;
	private char ch;
	private static Pattern pattern5 = Pattern.compile("([a-f\\d])\\1{4}");

	public Hash(String hash, int position, char ch) {
	    this.hash = hash;
	    this.position = position;
	    this.ch = ch;
	}

	public String asString() {
	    return "" + position + ": " + ch + ": " + hash;
	}

	public String getHash() {
	    return hash;
	}

	public int getPosition() {
	    return position;
	}

	public String getString5() {
	    return "" + ch + ch + ch + ch + ch;
	}

	public boolean contains5() {
	    return pattern5.matcher(hash).find();
	}

	@Override
	public int hashCode() {
	    return position;
	}

	@Override
	public boolean equals(Object obj) {
	    return ((Hash) obj).hashCode() == this.hashCode();
	}

    }

}
