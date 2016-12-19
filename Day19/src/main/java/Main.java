import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static final int INPUT = 3014603;

    public static void main(String[] args) {
	System.out.println(part1());
	System.out.println(part2());
    }

    private static int part1() {
	List<Integer> list = fill(new LinkedList<>());
	int shift = 1;
	while (list.size() > 1) {
	    Iterator<Integer> iterator = list.iterator();
	    if (shift == 1) {
		iterator.next();
		shift = 0;
	    }
	    while (iterator.hasNext()) {
		iterator.next();
		iterator.remove();
		if (iterator.hasNext()) {
		    iterator.next();
		} else {
		    shift = 1;
		}
	    }
	}
	return list.get(0);
    }

    private static int part2() {
	List<Integer> list = fill(new ArrayList<>());
	int elfIndex = 0;
	while (list.size() > 1) {
	    int elfRemove = (elfIndex + (int) (list.size() / 2)) % list.size();
	    list.remove(elfRemove);
	    elfIndex = ((elfRemove > elfIndex) ? elfIndex + 1 : elfIndex) % list.size();
	}
	return list.get(0);
    }

    private static List<Integer> fill(List<Integer> list) {
	for (int i = 1; i <= INPUT; i++) {
	    list.add(i);
	}
	return list;
    }

}
