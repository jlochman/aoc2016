import java.util.Set;

public class Main {

    public final static int FAV_NUMBER = 1352;
    public final static int MAZE_SIZE = 50;

    public static boolean[][] maze = new boolean[MAZE_SIZE][MAZE_SIZE];

    public static void main(String[] args) {
	createMaze();

	AStarSearch search = new AStarSearch();
	search.findPath(new AStarNode(1, 1), new AStarNode(31, 39));
	// printMaze(search.getVisitedNodes());
    }

    private static void createMaze() {
	for (int x = 0; x < MAZE_SIZE; x++) {
	    for (int y = 0; y < MAZE_SIZE; y++) {
		maze[x][y] = isOpenSpace(x, y);
	    }
	}
    }

    private static boolean isOpenSpace(int x, int y) {
	int value = x * x + 3 * x + 2 * x * y + y + y * y;
	value += FAV_NUMBER;
	return (Integer.toBinaryString(value).replaceAll("0", "").length() % 2 == 0);
    }

    public static void printMaze(Set<AStarNode> visitedNodes) {
	for (int y = 0; y < MAZE_SIZE; y++) {
	    String s = "";
	    for (int x = 0; x < MAZE_SIZE; x++) {
		final int xPos = x;
		final int yPos = y;
		if (visitedNodes.stream().filter(n -> (n.getX() == xPos) && (n.getY() == yPos)).count() > 0) {
		    s += "o";
		} else {
		    s += maze[x][y] ? "." : "#";
		}
	    }
	    System.out.println(s);
	}
    }

}
