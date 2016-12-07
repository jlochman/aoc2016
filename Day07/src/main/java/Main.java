import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    private static void part1() throws IOException {
	int count = 0;
	try (Scanner in = newInput()) {
	    while (in.hasNext()) {
		String sLine = in.nextLine();
		if (getBracketInsides(sLine).stream().map(s -> containsABBA(s)).collect(Collectors.toSet())
			.contains(true)) {
		    continue;
		}
		if (getBracketOutsides(sLine).stream().map(s -> containsABBA(s)).collect(Collectors.toSet())
			.contains(true)) {
		    count++;
		}
	    }
	}
	System.out.println(count);
    }

    private static void part2() throws IOException {
	int count = 0;
	try (Scanner in = newInput()) {
	    while (in.hasNext()) {
		String sLine = in.nextLine();
		Set<String> insides = getBracketInsides(sLine).stream().flatMap(s -> getABAs(s).stream())
			.collect(Collectors.toSet());
		Set<String> outsides = getBracketOutsides(sLine).stream().flatMap(s -> getABAs(s).stream())
			.collect(Collectors.toSet());
		if (isSSLcompatible(insides, outsides)) {
		    count++;
		}
	    }
	}
	System.out.println(count);
    }

    private static boolean isSSLcompatible(Set<String> insides, Set<String> outsides) {
	for (String out : outsides) {
	    for (String in : insides) {
		if (isABAComplement(in, out)) {
		    return true;
		}
	    }
	}
	return false;
    }

    private static List<String> getBracketInsides(String line) {
	Matcher m = Pattern.compile("\\[(.*?)\\]").matcher(line);
	List<String> insideBrackets = new ArrayList<>();
	while (m.find()) {
	    insideBrackets.add(m.group(1));
	}
	return insideBrackets;
    }

    private static List<String> getBracketOutsides(String line) {
	return Arrays.asList(line.split("\\[(.*?)\\]"));
    }

    private static boolean containsABBA(String s) {
	for (int i = 0; i < s.length() - 3; i++) {
	    if (isABBA(s.substring(i, i + 4))) {
		return true;
	    }
	}
	return false;
    }

    private static Set<String> getABAs(String s) {
	Set<String> abas = new HashSet<>();
	for (int i = 0; i < s.length() - 2; i++) {
	    String aba = s.substring(i, i + 3);
	    if (isABA(aba)) {
		abas.add(aba);
	    }
	}
	return abas;
    }

    private static boolean isABBA(String s) {
	return (s.charAt(0) == s.charAt(3)) && (s.charAt(1) == s.charAt(2)) && (s.charAt(0) != s.charAt(1));
    }

    private static boolean isABA(String s) {
	return (s.charAt(0) == s.charAt(2)) && (s.charAt(0) != s.charAt(1));
    }

    private static boolean isABAComplement(String aba, String bab) {
	return (aba.charAt(0) == bab.charAt(1)) && (aba.charAt(1) == bab.charAt(0));
    }

}
