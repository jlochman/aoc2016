import java.util.ArrayList;
import java.util.List;

/**
 * The AStarNode class, along with the AStarSearch class, implements a generic
 * A* search algorithm. The AStarNode class should be subclassed to provide
 * searching capability.
 */
public class AStarNode implements Comparable<Object> {

    AStarNode pathParent;
    float costFromStart;
    float estimatedCostToGoal;

    private int x;
    private int y;

    public AStarNode(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public float getCost() {
	return costFromStart + estimatedCostToGoal;
    }

    public int compareTo(Object other) {
	float thisValue = this.getCost();
	float otherValue = ((AStarNode) other).getCost();

	float v = thisValue - otherValue;
	return (v > 0) ? 1 : (v < 0) ? -1 : 0; // sign function
    }

    /**
     * Gets the cost between this node and the specified adjacent (AKA
     * "neighbor" or "child") node.
     */
    public float getCost(AStarNode node) {
	return 1;
    };

    /**
     * Gets the estimated cost between this node and the specified node. The
     * estimated cost should never exceed the true cost. The better the
     * estimate, the more effecient the search.
     */
    public float getEstimatedCost(AStarNode node) {
	/*
	float result = 0; 
	result += Math.abs(node.getX() - this.getX());
	result += Math.abs(node.getY() - this.getY());
	return (float) result;
	*/
	// kvuli casti 2 jsou vsechy nody stejne daleko (neexistuje preference)
	return 1;
    };

    /**
     * Gets the children (AKA "neighbors" or "adjacent nodes") of this node.
     */
    public List<AStarNode> getNeighbors() {
	List<AStarNode> nodes = new ArrayList<>();
	if (isPossible(x - 1, y)) {
	    nodes.add(new AStarNode(x - 1, y));
	}
	if (isPossible(x + 1, y)) {
	    nodes.add(new AStarNode(x + 1, y));
	}
	if (isPossible(x, y - 1)) {
	    nodes.add(new AStarNode(x, y - 1));
	}
	if (isPossible(x, y + 1)) {
	    nodes.add(new AStarNode(x, y + 1));
	}
	return nodes;
    }

    private boolean isPossible(int xNew, int yNew) {
	if (xNew < 0 || xNew >= Main.MAZE_SIZE) {
	    return false;
	}
	if (yNew < 0 || yNew >= Main.MAZE_SIZE) {
	    return false;
	}
	return Main.maze[xNew][yNew];
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    @Override
    public int hashCode() {
	return ("" + x + "," + y).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof AStarNode))
	    return false;
	else
	    return obj.hashCode() == this.hashCode();
    }

}
