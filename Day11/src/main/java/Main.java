import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// 60 < x < 70

public class Main {

    public static void main(String[] args) {
	Area startNode = new Area();
	startNode.initStart();
	startNode.display();

	Area goalNode = new Area();
	goalNode.initGoal();

	AStarSearch search = new AStarSearch();

	/*
	 * Thread forwardSearch = new Thread(() ->
	 * {System.out.println(1);search.findPath(startNode, goalNode);});
	 * forwardSearch.start();
	 * 
	 * Thread backwardSearch = new Thread(() -> {System.out.println(2);
	 * search.findPath(goalNode, startNode);}); backwardSearch.start();
	 */

	List<Area> path = search.findPath(startNode, goalNode).stream().map(o -> (Area) o).collect(Collectors.toList());
	System.out.println(path);
	path.stream().forEach(a -> a.display());
    }

    private static class Area extends AStarNode {

	private int elFloor = 1;
	private List<Element> elements = new ArrayList<>();
	private int steps = 0;
	private static double minCost = Double.MAX_VALUE;
	private static double minHeuristic = Double.MAX_VALUE;

	private static final int NUM_FLOORS = 4;
	private static final int MAX_HEURISTIC = 10000;

	public List<Element> getElements() {
	    return elements;
	}

	public void setElements(List<Element> elements) {
	    this.elements = elements;
	}

	public int getSteps() {
	    return steps;
	}

	public void setSteps(int steps) {
	    this.steps = steps;
	}

	public int getElFloor() {
	    return elFloor;
	}

	public void setElFloor(int elFloor) {
	    this.elFloor = elFloor;
	}

	public Area copy() {
	    Area area = new Area();
	    area.setElements(this.getElements().stream().map(e -> new Element(e.getFloor(), e.getName()))
		    .collect(Collectors.toList()));
	    area.setElFloor(this.getElFloor());
	    area.setSteps(this.getSteps() + 1);
	    return area;
	}

	public void initStart() {

	    // demo
	    // elements.add(new Element(1, "HM"));
	    // elements.add(new Element(1, "LM"));
	    // elements.add(new Element(2, "HG"));
	    // elements.add(new Element(3, "LG"));

	    // part1
	    elements.add(new Element(1, "SG"));
	    elements.add(new Element(1, "SM"));
	    elements.add(new Element(1, "PG"));
	    elements.add(new Element(1, "PM"));
	    elements.add(new Element(2, "TG"));
	    elements.add(new Element(2, "RG"));
	    elements.add(new Element(2, "RM"));
	    elements.add(new Element(2, "CG"));
	    elements.add(new Element(2, "CM"));
	    elements.add(new Element(3, "TM"));

	    // +part2
	    elements.add(new Element(1, "EG"));
	    elements.add(new Element(1, "EM"));
	    elements.add(new Element(1, "DG"));
	    elements.add(new Element(1, "DM"));

	    setElFloor(1);
	}

	public void initGoal() {
	    this.initStart();
	    elements.stream().forEach(e -> e.setFloor(NUM_FLOORS));
	    setElFloor(4);
	}

	public void display() {
	    for (int i = NUM_FLOORS; i >= 1; i--) {
		System.out.println(displayFloor(i));
	    }
	    int g = steps;
	    int h = (int) getHeuristic();
	    System.out.println("f=g+h=" + (g + h) + ", g=" + g + ", h=" + h);
	    System.out.println("__________");
	}

	public String asString() {
	    String s = "";
	    for (int i = NUM_FLOORS; i >= 1; i--) {
		s += displayFloor(i);
	    }
	    return s;
	}

	private String displayFloor(int floor) {
	    String s = "F" + floor + " ";
	    s += elFloor == floor ? "E  " : ".  ";
	    s += elements.stream().map(e -> (e.getFloor() == floor) ? e.getName() : ". ")
		    .collect(Collectors.joining(" "));
	    return s;
	}

	public double getHeuristic() {
	    int h = 0;
	    for (int i = 1; i <= NUM_FLOORS; i++) {
		h += isFriedChip(i) ? MAX_HEURISTIC + 1 : 0;
	    }
	    if (h > 0) {
		return h;
	    }
	    h += elements.stream().mapToDouble(e -> getHeuristic(e)).sum();
	    h -= NUM_FLOORS - elements.stream().mapToInt(e -> e.getFloor()).min().getAsInt();
	    return h;
	}

	private double getHeuristic(Element e) {
	    double result = 2 * Math.sqrt(NUM_FLOORS - e.getFloor());
	    // int result = (int) Math.pow((NUM_FLOORS - e.getFloor()), 2);
	    if (e.getFloor() < elFloor) {
		result += (elFloor - e.getFloor());
	    }
	    return result;
	}

	private boolean isFriedChip(int floor) {
	    Set<String> names = elements.stream().filter(e -> e.getFloor() == floor).map(e -> e.getName())
		    .collect(Collectors.toSet());
	    Set<String> chips = names.stream().filter(e -> e.charAt(1) == 'M').collect(Collectors.toSet());
	    Set<String> generators = names.stream().filter(e -> e.charAt(1) == 'G').collect(Collectors.toSet());
	    if (chips.isEmpty() || generators.isEmpty()) {
		return false;
	    } else {
		for (String ch : chips) {
		    if (!names.contains(ch.charAt(0) + "G")) {
			return true;
		    }
		}
	    }
	    return false;
	}

	@Override
	public float getCost(AStarNode node) {
	    return 1;
	}

	@Override
	public float getEstimatedCost(AStarNode node) {
	    return (float) Math.abs(this.getHeuristic() - ((Area) node).getHeuristic());
	}

	@Override
	public List<AStarNode> getNeighbors() {
	    List<AStarNode> list = new ArrayList<>();
	    List<Element> floorElements = elements.stream().filter(e -> e.getFloor() == elFloor)
		    .collect(Collectors.toList());
	    if (floorElements.isEmpty()) {
		return list;
	    }
	    if (elFloor < 4) {
		// move two elements up
		for (int i = 0; i < floorElements.size(); i++) {
		    for (int k = i + 1; k < floorElements.size(); k++) {
			Area area = this.copy();
			area.moveElement(floorElements.get(i).getName(), 1);
			area.moveElement(floorElements.get(k).getName(), 1);
			area.moveElevator(1);
			if (area.getHeuristic() < MAX_HEURISTIC) {
			    list.add(area);
			}
		    }
		}
	    }

	    if (elFloor > 1 && elements.stream().filter(e -> e.getFloor() < elFloor).count() > 0) {
		// move single element down
		list.addAll(floorElements.stream().map(e -> {
		    Area area = this.copy();
		    area.moveElement(e.getName(), -1);
		    area.moveElevator(-1);
		    return area;
		}).filter(a -> a.getHeuristic() < MAX_HEURISTIC).collect(Collectors.toList()));
	    }

	    list.stream().map(a -> (Area) a).forEach(a -> {
		if (a.getHeuristic() < minHeuristic) {
		    minHeuristic = a.getHeuristic();
		    a.display();
		}
		if (a.getHeuristic() == 0 && a.getHeuristic() < minCost) {
		    minCost = a.getHeuristic();
		    System.out.println("SOLUTION");
		    a.display();
		}
	    });
	    return list;
	}

	public void moveElement(String eName, int by) {
	    elements.stream().filter(e -> e.getName().equals(eName)).forEach(e -> e.setFloor(e.getFloor() + by));
	}

	public void moveElevator(int by) {
	    setElFloor(elFloor + by);
	}

	@Override
	public boolean equals(Object obj) {
	    Area other = (Area) obj;
	    return this.asString().equals(other.asString());
	}

	@Override
	public int hashCode() {
	    return this.asString().hashCode();
	}

    }

    private static class Element {

	int floor;
	String name;

	public Element(int floor, String name) {
	    this.floor = floor;
	    this.name = name;
	}

	public int getFloor() {
	    return floor;
	}

	public void setFloor(int floor) {
	    this.floor = floor;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

    }

}
