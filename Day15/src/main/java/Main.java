import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static int time;
    private static List<Disc> discs = new ArrayList<>();

    public static void main(String[] args) {
	part1();
	part2();
    }

    private static void part1() {
	time = 0;
	discs = Arrays.asList(new Disc(1, 7, 0), new Disc(2, 13, 0), new Disc(3, 3, 2), new Disc(4, 5, 2),
		new Disc(5, 17, 0), new Disc(6, 19, 7));
	process();
    }

    private static void part2() {
	discs = new ArrayList<>(discs);
	discs.add(new Disc(7, 11, 0));
	process();
    }

    private static void process() {
	while (discs.stream().mapToInt(d -> d.getPosition(time)).sum() != 0) {
	    time++;
	}
	System.out.println(time);
    }

    private static class Disc {
	int tDelay;
	int numPositions;
	int initPosition;

	public Disc(int tDelay, int numPositions, int initPosition) {
	    super();
	    this.tDelay = tDelay;
	    this.numPositions = numPositions;
	    this.initPosition = initPosition;
	}

	public int getPosition(int time) {
	    return (time + tDelay + initPosition) % numPositions;
	}
    }

}
