import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String INPUT_FILE = "input1.txt";

    static Scanner newInput() throws IOException {
	return new Scanner(new File(INPUT_FILE));
    }

    public static void main(String[] args) throws IOException {
	int triangleCount = 0;
	try (Scanner in = newInput()) {
	    while (in.hasNextInt()) {
		// if (isTriangle(in.nextInt(), in.nextInt(), in.nextInt()))
		// triangleCount++;
		triangleCount += numOfTriangles(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(),
			in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
	    }
	}
	System.out.println(triangleCount);
    }

    private static int numOfTriangles(int... sides) {
	int result = 0;
	if (sides.length == 9) {
	    for (int i = 0; i < sides.length / 3; i++) {
		if (isTriangle(sides[i], sides[3 + i], sides[6 + i]))
		    result++;
	    }
	} else {
	    System.out.println("I require 9 inputs.");
	}
	return result;
    };

    private static boolean isTriangle(int a, int b, int c) {
	if (a + b <= c)
	    return false;
	if (b + c <= a)
	    return false;
	if (c + a <= b)
	    return false;
	return true;
    }

}
