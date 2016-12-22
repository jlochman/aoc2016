
public class DiskNode {

    int x;
    int y;
    int size;
    int used;
    int avail;
    
    public DiskNode(int x, int y, int size, int used) {
	super();
	this.x = x;
	this.y = y;
	this.size = size;
	this.used = used;
	this.avail = size - used;
    }

    @Override
    public int hashCode() {
	return 100 * x + y;
    }

    @Override
    public boolean equals(Object obj) {
	DiskNode other = (DiskNode) obj;
	return other.hashCode() == this.hashCode();
    }
    
    public String asString() {
	return String.format("[%d,%d]: size: %d, used: %d", x, y, size, used);
    }

}
