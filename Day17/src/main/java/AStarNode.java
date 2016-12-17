import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AStarNode implements Comparable<Object> {

    AStarNode pathParent;
    float costFromStart;
    float estimatedCostToGoal;

    final static int X_MIN = 1;
    final static int X_MAX = 4;
    final static int Y_MIN = 1;
    final static int Y_MAX = 4;

    private int x;
    private int y;
    private String path;

    public AStarNode(int x, int y, String path) {
	this.x = x;
	this.y = y;
	this.path = path;
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

    public float getCost(AStarNode node) {
	return 1;
    }

    public float getEstimatedCost(AStarNode node) {
	return Math.abs(node.x - this.x) + Math.abs(node.y - this.y);
    };

    public List<AStarNode> getNeighbors() {
	List<AStarNode> list = new ArrayList<>();
	try {
	    String hash = getHash(Main.INPUT + this.path);
	    if (this.x > X_MIN && shouldOpen(hash.charAt(0))) {
		list.add(new AStarNode(this.x - 1, this.y, this.path + "U"));
	    }
	    if (this.x < X_MAX && shouldOpen(hash.charAt(1))) {
		list.add(new AStarNode(this.x + 1, this.y, this.path + "D"));
	    }
	    if (this.y > Y_MIN && shouldOpen(hash.charAt(2))) {
		list.add(new AStarNode(this.x, this.y - 1, this.path + "L"));
	    }
	    if (this.y < Y_MAX && shouldOpen(hash.charAt(3))) {
		list.add(new AStarNode(this.x, this.y + 1, this.path + "R"));
	    }
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
	return list;
    };

    private boolean shouldOpen(char ch) {
	return ch == 'b' || ch == 'c' || ch == 'd' || ch == 'e' || ch == 'f';
    }

    private String getHash(String s) throws NoSuchAlgorithmException {
	MessageDigest md5 = MessageDigest.getInstance("MD5");
	md5.update(StandardCharsets.UTF_8.encode(s));
	return String.format("%032x", new BigInteger(1, md5.digest()));
    }

    @Override
    public int hashCode() {
	return this.path.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	return ((AStarNode) obj).hashCode() == this.hashCode();
    }

    public String getPath() {
	return path;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

}
