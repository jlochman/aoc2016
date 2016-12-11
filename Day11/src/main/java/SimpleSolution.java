
public class SimpleSolution {

    public static void main(String[] args) {
	System.out.println(count(4, 5, 1));
	System.out.println(count(8, 5, 1));
    }

    private static int count(int inFirstFloor, int inSecondFloor, int inThirdFloor) {
	return (2 * inFirstFloor - 3) * 3 + 2 * inSecondFloor * 2 + 2 * inThirdFloor;
    }

}
