import java.util.LinkedList;
import java.util.List;

/**
 * The AStarSearch class, along with the AStarNode class, implements a generic
 * A* search algorithm. The AStarNode class should be subclassed to provide
 * searching capability.
 */
public class AStarSearch {

    float actualCost  = 0;
    
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
    public List<AStarNode> findPath(AStarNode startNode) {

	PriorityList openList = new PriorityList();
	LinkedList<AStarNode> closedList = new LinkedList<>();

	startNode.costFromStart = 0;
	startNode.estimatedCostToGoal = startNode.getEstimatedCostToGoal();
	startNode.pathParent = null;
	openList.add(startNode);

	while (!openList.isEmpty()) {
	    AStarNode node = (AStarNode) openList.removeFirst();
	    
	    if (node.costFromStart > actualCost) {
		actualCost = node.costFromStart;
		System.out.printf("cost: %d, branching: %d \n", (int) node.costFromStart, openList.size());
	    }
	    if (node.dataNode.x == 0 && node.dataNode.y == 0) {
		System.out.println((int) node.costFromStart);
		return constructPath(node);
	    }

	    List<AStarNode> neighbors = node.getNeighbors();
	    for (int i = 0; i < neighbors.size(); i++) {
		AStarNode neighborNode = (AStarNode) neighbors.get(i);
		boolean isOpen = openList.contains(neighborNode);
		boolean isClosed = closedList.contains(neighborNode);
		float costFromStart = node.costFromStart + node.getCost(neighborNode);

		// check if the neighbor node has not been
		// traversed or if a shorter path to this
		// neighbor node is found.
		if ((!isOpen && !isClosed) || costFromStart < neighborNode.costFromStart) {
		    neighborNode.pathParent = node;
		    neighborNode.costFromStart = costFromStart;
		    neighborNode.estimatedCostToGoal = neighborNode.getEstimatedCostToGoal();
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