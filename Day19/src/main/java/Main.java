import java.util.List;

import org.magicwerk.brownies.collections.GapList;

public class Main {

    private static final int INPUT = 3014603;

    public static void main(String[] args) {
	Calculator.calculate((index, listSize) -> (index + 1) % listSize);
	Calculator.calculate((index, listSize) -> (int) (index + listSize / 2) % listSize);
    }

    private static abstract class Calculator {

	public static void calculate(CalcInterface calc) {
	    long startTime = System.currentTimeMillis();
	    List<Integer> list = new GapList<>(INPUT);
	    for (int i = 1; i <= INPUT; i++) {
		list.add(i);
	    }
	    int elfIndex = 0;
	    while (list.size() > 1) {
		int elfRemove = calc.getNextRemove(elfIndex, list.size());
		list.remove(elfRemove);
		elfIndex = ((elfRemove > elfIndex) ? elfIndex + 1 : elfIndex) % list.size();
	    }
	    System.out.printf("in: %d ms... RESULT: %d \n", System.currentTimeMillis() - startTime, list.get(0));
	}

    }

    private static interface CalcInterface {
	public int getNextRemove(int elfIndex, int listSize);
    }

}
