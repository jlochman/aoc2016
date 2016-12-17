public class Main {

    protected final static String INPUT = "mmsxrhfx";

    public static void main(String[] args) {
	AStarNode startNode = new AStarNode(AStarNode.X_MIN, AStarNode.Y_MIN, "");
	AStarNode goalNode = new AStarNode(AStarNode.X_MAX, AStarNode.Y_MAX, "");
	AStarSearch search = new AStarSearch();
	search.findPath(startNode, goalNode);
    }

}
