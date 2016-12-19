import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String INPUT = ".^^^.^.^^^^^..^^^..^..^..^^..^.^.^.^^.^^....^.^...^.^^.^^.^^..^^..^.^..^^^.^^...^...^^....^^.^^^^^^^";
    private static final int NUM_ROWS_1 = 40;
    private static final int NUM_ROWS_2 = 400000;

    private static List<List<Boolean>> tiles = new ArrayList<>();

    public static void main(String[] args) {
	tiles.add(INPUT.chars().mapToObj(ch -> (char) ch).map(ch -> ch == '^').collect(Collectors.toList()));
	genRows(NUM_ROWS_1);
	genRows(NUM_ROWS_2);
    }

    private static void genRows(int totalRows) {
	for (int i = tiles.size(); i < totalRows; i++) {
	    tiles.add(getNextRow(tiles.get(i - 1)));
	}
	System.out.println(tiles.stream().mapToInt(row -> (int) row.stream().filter(b -> b == false).count()).sum());
    }

    private static List<Boolean> getNextRow(List<Boolean> prevRow) {
	List<Boolean> thisRow = new ArrayList<>();
	for (int i = 0; i < prevRow.size(); i++) {
	    thisRow.add(isTrap(i == 0 ? false : prevRow.get(i - 1), prevRow.get(0),
		    i == prevRow.size() - 1 ? false : prevRow.get(i + 1)));
	}
	return thisRow;
    }

    private static boolean isTrap(boolean left, boolean center, boolean right) {
	return (left && center && !right) || (!left && center && right) || (left && !center && !right)
		|| (!left && !center && right);
    }

}
