package main.java.graph.datastructure;

import java.util.*;

/**
 * A Node is connected to its successors via a weighted edge.
 */
public class Node {

    private String name;

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    private Map<Node, Integer> successors = new HashMap<>();

    /**
     * Constructs a new Node with the specified name.
     * @param name Name of the Node. Does not have to be unique.
     */
    public Node(String name) {
        this.name = name;
    }

    /**
     * Adds a successor-Node to this node. The edge from this node to the successor-Node will have the weight distance.
     * @param successor New successor Node to this Node.
     * @param distance Weight of the edge from this Node to the successor-Node.
     */
    public void addSuccessor(Node successor, int distance) {
        successors.put(successor, distance);
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
    public String[] getShortestPathNames() {
        return shortestPath.stream().map(Node::getName).toArray(String[]::new);
    }

    public Map<Node, Integer> getSuccessors() {
        return successors;
    }

    public String getName() {
        return name;
    }
}