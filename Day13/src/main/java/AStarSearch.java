import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The AStarSearch class, along with the AStarNode class, implements a generic
 * A* search algorithm. The AStarNode class should be subclassed to provide
 * searching capability.
 */
public class AStarSearch {

    private Set<AStarNode> visitedNodes = new HashSet<>();

    public Set<AStarNode> getVisitedNodes() {
	return visitedNodes;
    }

    /**
     * A simple priority list, also called a priority queue. Objects in the list
     * are ordered by their priority, determined by the object's Comparable
     * interface. The highest priority item is first in the list.
     */
    public static class PriorityList extends LinkedList<Object> {

	/**
	 * 
	 */
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

    /**
     * Construct the path, not including the start node.
     */
    protected List<AStarNode> constructPath(AStarNode node) {
	LinkedList<AStarNode> path = new LinkedList<>();
	while (node.pathParent != null) {
	    path.addFirst(node);
	    node = node.pathParent;
	}
	return path;
    }

    /**
     * Find the path from the start node to the end node. A list of AStarNodes
     * is returned, or null if the path is not found.
     */
    public List<AStarNode> findPath(AStarNode startNode, AStarNode goalNode, boolean isPart2) {

	PriorityList openList = new PriorityList();
	LinkedList<AStarNode> closedList = new LinkedList<>();

	startNode.costFromStart = 0;
	startNode.estimatedCostToGoal = isPart2 ? 1 : startNode.getEstimatedCost(goalNode);
	startNode.pathParent = null;
	openList.add(startNode);

	while (!openList.isEmpty()) {
	    AStarNode node = (AStarNode) openList.removeFirst();
	    if (node.costFromStart <= 50) {
		visitedNodes.add(node);
	    }

	    if (node.equals(goalNode)) {
		System.out.println("shortest path: " + (int) node.costFromStart);
		System.out.println("# visited in 50 steps: " + visitedNodes.size());
		System.out.println("_______________");
		return constructPath(goalNode);
	    }

	    List<AStarNode> neighbors = node.getNeighbors();
	    for (int i = 0; i < neighbors.size(); i++) {
		AStarNode neighborNode = neighbors.get(i);
		boolean isOpen = openList.contains(neighborNode);
		boolean isClosed = closedList.contains(neighborNode);
		float costFromStart = node.costFromStart + node.getCost(neighborNode);

		// check if the neighbor node has not been
		// traversed or if a shorter path to this
		// neighbor node is found.
		if ((!isOpen && !isClosed) || costFromStart < neighborNode.costFromStart) {
		    neighborNode.pathParent = node;
		    neighborNode.costFromStart = costFromStart;
		    neighborNode.estimatedCostToGoal = isPart2 ? 1 : neighborNode.getEstimatedCost(goalNode);
		    if (isClosed) {
			closedList.remove(neighborNode);
		    }
		    if (!isOpen) {
			openList.add(neighborNode);
		    }
		}
	    }
	    closedList.add(node);
	}

	// no path found
	return null;
    }

}
