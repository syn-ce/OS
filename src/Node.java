import java.util.*;

/**
 * A Node is connected to its successors via a weighted edge.
 */
public class Node {

    private String name;

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    private Map<Node, Integer> successors = new HashMap<>();

    public void addSuccessor(Node destination, int distance) {
        successors.put(destination, distance);
    }

    public Node(String name) {
        this.name = name;
    }

    public void setDistance(Integer d) {
        distance = d;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setShortestPath(List<Node> path) {
        shortestPath = path;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }
    public String[] getShortestPathStrings() {
        return shortestPath.stream().map(Node::getName).toArray(String[]::new);
    }

    public Map<Node, Integer> getSuccessors() {
        return successors;
    }

    public String getName() {
        return name;
    }
}