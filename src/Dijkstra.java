import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * This class implements Dijkstra's algorithm naively.
 */
public class Dijkstra {

    public Dijkstra() {

    }

    /**
     * Calculates the shortest path from a source Node to all other Nodes connected to that Node and sets their
     * distances accordingly.
     * @param source Node from which to calculate the distance to all other Nodes.
     */
    public void calculateShortestPathsFromNode(RailGraph g, Node source) {
        resetShortestPaths(g);
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getSuccessors().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    /**
     * Looks at the Node to evaluate and another Node connected to the first Node via an edge. If the first Nodes
     * distance can be shortened by going through the second Node and the specified edge, the first Node's shortest
     * distance and shortest path are updated accordingly.
     * @param evaluationNode Node for which a shorter path may be found by going through sourceNode and the edge between them.
     * @param edgeWeight Weight of the edge between evaluationNode and sourceNode.
     * @param sourceNode Node before the evaluationNode, connected to it via an edge with weight edgeWeight.
     */
    private void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeight < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeight);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    /**
     * Of all the nodes for which the shortest path from the source Node has not yet been determined definitely, finds
     * the one with the smallest distance to the source Node.
     * @param unsettledNodes Nodes to which the distance has not yet been finally determined.
     * @return The unsettled Node with the smallest distance to the source Node.
     */
    private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    /**
     * Resets the shortest path as well as the distance of all nodes of the graph to the default values.
     * This will prevent undesired behaviour and ambiguous distances of nodes when Dijkstra is called again on a
     * different node of the same graph.
     * @param g Graph containing all the nodes of which the distances shall be reset.
     */
    private void resetShortestPaths(RailGraph g) {
        for (Node n : g.getNodes()) {
            n.setDistance(Integer.MAX_VALUE);
            n.setShortestPath(new LinkedList<>());
        }
    }
}
