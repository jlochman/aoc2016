import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Maze {

    Map<Position, Element> maze;
    int numRows;
    int numCols;

    public Maze() {
	maze = new HashMap<>();
	numRows = 0;
	numCols = 0;
    }

    public void add(char ch, int row, int col) {
	numRows = Math.max(numRows, row + 1);
	numCols = Math.max(numCols, col + 1);
	maze.put(new Position(row, col), new Element(ch));
    }

    public void show() {
	for (int row = 0; row < numRows; row++) {
	    final int r = row;
	    System.out.println(maze.entrySet().stream().filter(e -> e.getKey().row == r)
		    .sorted((e1, e2) -> Integer.compare(e1.getKey().col, e2.getKey().col))
		    .map(e -> Character.toString(e.getValue().ch)).collect(Collectors.joining("")));
	}
    }

    public int findPath(int from, int to) {
	Position startNode = getPosition(from);
	Position endNode = getPosition(to);
	AStarSearch search = new AStarSearch();
	return (int) search.findPath(startNode, endNode);
    }

    private Position getPosition(int i) {
	Optional<Position> pos = maze.entrySet().stream().filter(e -> e.getValue().ch == Integer.toString(i).charAt(0))
		.map(e -> e.getKey()).findFirst();
	return pos.isPresent() ? pos.get() : null;
    }

    private List<Position> getAccessibleNeighbors(Position position) {
	List<Position> positions = new ArrayList<>();
	Position pos;
	Element e;

	pos = new Position(position.row + 1, position.col);
	e = maze.get(pos);
	if (e != null && e.isAccessible()) {
	    positions.add(pos);
	}

	pos = new Position(position.row - 1, position.col);
	e = maze.get(pos);
	if (e != null && e.isAccessible()) {
	    positions.add(pos);
	}

	pos = new Position(position.row, position.col + 1);
	e = maze.get(pos);
	if (e != null && e.isAccessible()) {
	    positions.add(pos);
	}

	pos = new Position(position.row, position.col - 1);
	e = maze.get(pos);
	if (e != null && e.isAccessible()) {
	    positions.add(pos);
	}

	return positions;
    }

    class Position extends AStarNode {
	private int row;
	private int col;

	public Position(int row, int col) {
	    this.row = row;
	    this.col = col;
	}

	@Override
	public int hashCode() {
	    return 1000 * row + col;
	}

	@Override
	public boolean equals(Object obj) {
	    Position other = (Position) obj;
	    return this.hashCode() == other.hashCode();
	}

	@Override
	public float getCost(AStarNode node) {
	    return 1;
	}

	@Override
	public float getEstimatedCost(AStarNode node) {
	    Position other = (Position) node;
	    return Math.abs(other.col - this.col) + Math.abs(other.row - this.row);
	}

	@Override
	public List<AStarNode> getNeighbors() {
	    return getAccessibleNeighbors(this).stream().map(o -> {
		o.pathParent = this;
		return (AStarNode) o;
	    }).collect(Collectors.toList());
	}

    }

    class Element {
	char ch;

	public Element(char ch) {
	    this.ch = ch;
	}

	public boolean isAccessible() {
	    return ch != '#';
	}

	@Override
	public int hashCode() {
	    return Character.getNumericValue(ch);
	}

	@Override
	public boolean equals(Object obj) {
	    Element other = (Element) obj;
	    return other.hashCode() == this.hashCode();
	}

    }

}
