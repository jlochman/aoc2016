import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The AStarNode class, along with the AStarSearch class, implements a generic
 * A* search algorithm. The AStarNode class should be subclassed to provide
 * searching capability.
 */
public class AStarNode implements Comparable<Object> {

    AStarNode pathParent;
    float costFromStart;
    float estimatedCostToGoal;

    Set<DiskNode> diskNodes;
    DiskNode emptyNode;
    DiskNode dataNode;

    public AStarNode(Set<DiskNode> diskNodes) {
	this.diskNodes = diskNodes;
	this.emptyNode = diskNodes.stream().filter(n -> n.used == 0).findFirst().get();
	this.dataNode = diskNodes.stream().filter(n -> n.x == Main.GRID_SIZE_X - 1 && n.y == 0).findFirst().get();
    }

    public AStarNode(Set<DiskNode> diskNodes, DiskNode emptyNode, DiskNode dataNode) {
	this.diskNodes = diskNodes;
	this.emptyNode = emptyNode;
	this.dataNode = dataNode;
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
    public float getEstimatedCostToGoal() {
	float result = 0;
	// vzdalenost prazdneho nodu od nodu, kde jsou zadana data
	int xDist = Math.abs(emptyNode.x - dataNode.x);
	int yDist = Math.abs(emptyNode.y + dataNode.y);
	result += xDist + yDist - 1;
	if (xDist > 1)
	    result += 5 * xDist;
	if (yDist > 1)
	    result += 5 * yDist;
	// vzdalenost datoveho nodu od startu
	result += dataNode.x + dataNode.y;
	return result;
    };

    /**
     * Gets the children (AKA "neighbors" or "adjacent nodes") of this node.
     */
    public List<AStarNode> getNeighbors() {
	List<AStarNode> starNodes = new ArrayList<>();

	Optional<DiskNode> optDiskNode;
	optDiskNode = getDisk(emptyNode.x, emptyNode.y + 1);
	if (optDiskNode.isPresent() && optDiskNode.get().used < emptyNode.avail) {
	    starNodes.add(getStarNode(optDiskNode.get(), emptyNode));
	}

	optDiskNode = getDisk(emptyNode.x, emptyNode.y - 1);
	if (optDiskNode.isPresent() && optDiskNode.get().used < emptyNode.avail) {
	    starNodes.add(getStarNode(optDiskNode.get(), emptyNode));
	}

	optDiskNode = getDisk(emptyNode.x + 1, emptyNode.y);
	if (optDiskNode.isPresent() && optDiskNode.get().used < emptyNode.avail) {
	    starNodes.add(getStarNode(optDiskNode.get(), emptyNode));
	}

	optDiskNode = getDisk(emptyNode.x - 1, emptyNode.y);
	if (optDiskNode.isPresent() && optDiskNode.get().used < emptyNode.avail) {
	    starNodes.add(getStarNode(optDiskNode.get(), emptyNode));
	}

	starNodes.stream().forEach(n -> n.pathParent = this);
	return starNodes;
    }

    private Optional<DiskNode> getDisk(int x, int y) {
	return diskNodes.stream().filter(n -> n.x == x && n.y == y).findFirst();
    }

    private AStarNode getStarNode(DiskNode copyFrom, DiskNode copyTo) {
	Set<DiskNode> disks = new HashSet<>();
	diskNodes.stream().filter(n -> !n.equals(copyFrom)).filter(n -> !n.equals(copyTo))
		.forEach(n -> disks.add(new DiskNode(n.x, n.y, n.size, n.used)));
	DiskNode emptyDisk = new DiskNode(copyFrom.x, copyFrom.y, copyFrom.size, 0);
	DiskNode filledDisk = new DiskNode(copyTo.x, copyTo.y, copyTo.size, copyFrom.used + copyTo.used);
	disks.add(emptyDisk);
	disks.add(filledDisk);
	DiskNode dataNode = copyFrom.equals(this.dataNode) ? filledDisk
		: disks.stream().filter(n -> n.x == this.dataNode.x && n.y == this.dataNode.y).findFirst().get();
	return new AStarNode(disks, emptyDisk, dataNode);
    }

    @Override
    public int hashCode() {
	return ("" + emptyNode.x + "," + emptyNode.y + "|" + dataNode.x + "," + dataNode.y).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	AStarNode other = (AStarNode) obj;
	return other.hashCode() == this.hashCode();
    }

    public void showGrid() {
	for (int i = 0; i < Main.GRID_SIZE_Y; i++) {
	    final int row = i;
	    System.out.println(diskNodes.stream().filter(n -> n.y == row)
		    .sorted((n1, n2) -> Integer.compare(n1.x, n2.x)).map(n -> {
			if (n.used > 100) {
			    return "#";
			} else if (n.equals(dataNode)) {
			    return "_";
			} else if (n.equals(emptyNode)) {
			    return "0";
			} else {
			    return ".";
			}
		    }).collect(Collectors.joining("")));
	}
	System.out.println("\n");
    }

}
