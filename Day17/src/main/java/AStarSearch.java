import java.util.LinkedList;
import java.util.List;

public class AStarSearch {

    private int longestPath = Integer.MIN_VALUE;

    public static class PriorityList extends LinkedList<Object> {

	private static final long serialVersionUID = 1L;

	public void add(Comparable<Object> object) {
	    for (int i = 0; i < size(); i++) {
		if (object.compareTo(get(i)) <= 0) {
		    add(i, object);
		    return;
		}
	    }
	    addLast(object);
	}
    }

    protected List<AStarNode> constructPath(AStarNode node) {
	LinkedList<AStarNode> path = new LinkedList<>();
	while (node.pathParent != null) {
	    path.addFirst(node);
	    node = node.pathParent;
	}
	return path;
    }

    public List<AStarNode> findPath(AStarNode startNode, AStarNode goalNode) {
	PriorityList openList = new PriorityList();

	startNode.costFromStart = 0;
	startNode.estimatedCostToGoal = startNode.getEstimatedCost(goalNode);
	startNode.pathParent = null;
	openList.add(startNode);

	while (!openList.isEmpty()) {
	    AStarNode node = (AStarNode) openList.removeFirst();

	    if (node.getX() == goalNode.getX() && node.getY() == goalNode.getY()) {
		if (longestPath < 0) {
		    System.out.println("FINISH REACHED: " + node.getPath());
		}
		if (node.getPath().length() > longestPath) {
		    longestPath = node.getPath().length();
		    System.out.println("longestPath: " + longestPath + "   remaining: " + openList.size());
		}
		continue;
	    }

	    List<AStarNode> neighbors = node.getNeighbors();
	    for (int i = 0; i < neighbors.size(); i++) {
		AStarNode neighborNode = neighbors.get(i);
		boolean isOpen = openList.contains(neighborNode);
		float costFromStart = node.costFromStart + node.getCost(neighborNode);
		if ((!isOpen) || costFromStart < neighborNode.costFromStart) {
		    neighborNode.pathParent = node;
		    neighborNode.costFromStart = costFromStart;
		    neighborNode.estimatedCostToGoal = neighborNode.getEstimatedCost(goalNode);
		    if (!isOpen) {
			openList.add(neighborNode);
		    }
		}
	    }
	}

	System.out.println("LONGEST PATH: " + longestPath);
	return null;
    }

}
