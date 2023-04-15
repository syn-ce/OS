import java.util.*;

public class Node {

    private String name;

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
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

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public String getAdjacentNodesString() {
        StringJoiner sj = new StringJoiner(", ");
        for (Map.Entry<Node, Integer> aN : adjacentNodes.entrySet()) {
            sj.add(aN.getKey().getName() + " (" + aN.getValue() + ")");
        }
        return sj.toString();
    }

    public String getName() {
        return name;
    }
    // getters and setters
}