package main.java.graph.datastructure;

import main.java.graph.algorithm.Dijkstra;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The RailGraph is used to construct a Graph representing the problem the Shunter has to solve (i.e. moving all
 * wagons from the parking-Rail to the train-Rail with as little movements as possible). The RailGraph is a Graph in
 * which for every unique wagon value in wagonValues, two nodes are being created, one LP- and one RP-Node. The nodes
 * represent decisions. The node with the name "LPi" indicates the decision that for the wagon value i, the Shunter
 * should start removing the values of that wagon from left to right. However, sometimes the Shunter will start "in the
 * middle" of all wagons with a certain number, meaning it won't be able to immediately start removing values from the
 * left, for instance. In this case, the Shunter will still move to the leftmost position of the current wagon value
 * before it moves to the rightmost, but will immediately move every wagon with the current value it encounters along
 * the way onto the train-Rail. This has to be done in order to keep the strategy optimal.
 * Therefore, a proper rephrasing of the nodes' meanings may be stated as follows: For each unique wagon value i, the
 * node named "LPi" corresponds to the decision of the Shunter to remove the RIGHTMOST wagon with value i last.
 * Accordingly, a node named "RPi" stand for the decision of the Shunter to remove the LEFTMOST wagon with value i last.
 * The graph can be imagined as being separated into layers, each layer consisting of one "LP" and one "RP" node.
 * Each node connects to both nodes of the next layer via a weighted edge. The weight of the edge from node LPi to RPj
 * indicates the cost of removing the leftmost wagon with value j last, after having removed the rightmost wagon with
 * value i last.
 * The optimal path, i.e. the optimal way in which to remove all wagon values, is then determined by calculating the
 * shortest path from the "LP"-node of the first layer (since that is where the Shunter logically has to start when all
 * wagons are initially parked on the parking Rail) to one of the nodes of the last layer.
 */
public class RailGraph {
    private Node[] LPNodes;
    private Node[] RPNodes;
    private int[] uniqueWagonValuesAscending;
    private int[] wagonValues;
    private Dijkstra dijkstra = new Dijkstra();

    /**
     * Constructs a new RailGraph.
     *
     * @param wagons                     Values of wagons in correct order.
     * @param uniqueWagonValuesAscending Wagons, except all duplicates have been removed and the values have been ordered ascending.
     * @param C_L_arrays                 An array containing the left cost-array for each unique wagon value, in order.
     * @param C_R_arrays                 An array containing the right cost-array for each unique wagon value, in order.
     */
    public RailGraph(int[] wagons, int[] uniqueWagonValuesAscending, int[][] C_L_arrays, int[][] C_R_arrays) {
        this.uniqueWagonValuesAscending = uniqueWagonValuesAscending.clone();       // TODO: check whether clone is necessary here
        wagonValues = wagons.clone();   // TODO: check whether this is necessary

        // initialise lp- and rp-nodes for every unique wagon-nr
        LPNodes = initialiseNodes("LP");
        RPNodes = initialiseNodes("RP");

        // add edges between nodes
        addEdges(C_L_arrays, C_R_arrays);
        // calculate minimum distance from LP1 to all nodes using Dijkstra's algorithm
        dijkstra.calculateShortestPathsFromNode(this, LPNodes[0]);   // search will always start at LP1 (/first LP-Node)
    }

    private Node[] initialiseNodes(String type) {
        Node[] nodes = new Node[uniqueWagonValuesAscending.length];
        for (int i = 0; i < uniqueWagonValuesAscending.length; i++) {
            Node node = new Node(type + uniqueWagonValuesAscending[i]);
            nodes[i] = node;
        }
        return nodes;
    }

    /**
     * Adds the weighted edges to the Nodes of the graph. Each Node will be connected to both Nodes of the next layer of the graph,
     * i.e. the two nodes associated with the next-greatest wagon value.
     *
     * @param C_L_arrays An array containing the left cost-array for each unique wagon value, in order.
     * @param C_R_arrays An array containing the right cost-array for each unique wagon value, in order.
     */
    private void addEdges(int[][] C_L_arrays, int[][] C_R_arrays) {
        for (int i = 1; i < uniqueWagonValuesAscending.length; i++) { // take care when indexing this
            int firstPosOfCurrentValue = getFirstOccurrence(wagonValues, uniqueWagonValuesAscending[i - 1]);
            int lastPosOfCurrentValue = getLastOccurrence(wagonValues, uniqueWagonValuesAscending[i - 1]);
            LPNodes[i - 1].addSuccessor(LPNodes[i], C_L_arrays[i][lastPosOfCurrentValue]);  // when starting from LP on this layer, the pointer will end up at the rightmost position
            LPNodes[i - 1].addSuccessor(RPNodes[i], C_R_arrays[i][lastPosOfCurrentValue]);

            RPNodes[i - 1].addSuccessor(LPNodes[i], C_L_arrays[i][firstPosOfCurrentValue]); // when starting from RP on this layer, the pointer will end up at the leftmost position of that value
            RPNodes[i - 1].addSuccessor(RPNodes[i], C_R_arrays[i][firstPosOfCurrentValue]);

            // because of the way in which the cost-arrays are constructed, the "lack of care" for adjusting the indices here should be alright
            // i.e. not adjusting for the fact that actually, the original last index of the current value does not have a cost on the next layer anymore, because the value of that index was removed in the previous iteration
        }
    }

    /**
     * Gets any Node of the graph by its 'layer', i.e. the index of its associated unique wagon value in differentWagonValues, and type.
     *
     * @param type  Either "LP" or "RP", depending on which of the two Nodes one wants to access.
     * @param layer Layer of the node in the graph = index of the associated wagon value in differentWagonValues.
     * @return The requested Node.
     */
    public Node getNodeByLayer(String type, int layer) {    // TODO: figure out whether these errors have to be checked? (out of bounds)
        if (layer >= uniqueWagonValuesAscending.length) {
            throw new RuntimeException("Layer out of range!");
        }
        if (type.equals("LP")) {
            return LPNodes[layer];
        }
        if (type.equals("RP")) {
            return RPNodes[layer];
        }
        throw new RuntimeException("Type of node has to equal 'LP' or 'RP'!");
    }

    public int getNrOfLayers() {    // could also return the length of RPNodes since they will always be equal
        return LPNodes.length;
    }

    /**
     * Getter for the nodes of the RailGraph.
     *
     * @return All nodes of the RailGraph as a Set.
     */
    public Set<Node> getNodes() {
        Set<Node> nodes = new HashSet<>();
        nodes.addAll(Arrays.asList(LPNodes));
        nodes.addAll(Arrays.asList(RPNodes));
        return nodes;
    }


    /**
     * Gets the index of the first occurrence of an int in an Array of ints.
     *
     * @param arr Array in which to look for the value.
     * @param z   Value to find the first occurrence of.
     * @return Index of the first occurrence of the value in the array.
     * @throws RuntimeException If the value is not present in the array.
     */
    private int getFirstOccurrence(int[] arr, int z) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == z) {
                return i;
            }
        }
        throw new RuntimeException("The array does not contain the number.");
    }

    /**
     * Gets the index of the last occurrence of an int in an Array of ints.
     *
     * @param arr Array in which to look for the value.
     * @param z   Value to find the last occurrence of.
     * @return Index of the last occurrence of the value in the array.
     * @throws RuntimeException If the value is not present in the array.
     */
    private int getLastOccurrence(int[] arr, int z) {
        for (int i = arr.length - 1; i > -1; i--) {
            if (arr[i] == z) {
                return i;
            }
        }
        throw new RuntimeException("The array does not contain the number.");
    }

}
