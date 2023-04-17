public class CustomGraph {
    private Node[] LPNodes;
    private Node[] RPNodes;
    private int[] differentWagonValues;
    private int[] wagonValues;
    private Dijkstra dijkstra = new Dijkstra();

    public CustomGraph(int[] wagons, int[] differentWagonValues, int[][] C_L_arrays, int[][] C_R_arrays) {
        this.differentWagonValues = differentWagonValues.clone();       // TODO: check whether clone is necessary here
        wagonValues = wagons.clone();   // TODO: check whether this is necessary
        LPNodes = initialiseNodes("LP");
        RPNodes = initialiseNodes("RP");

        addEdges(C_L_arrays, C_R_arrays);
        dijkstra.calculateShortestPathFromSource(LPNodes[0]);   // search will always start at LP1 (/first LP-Node)
    }

    private Node[] initialiseNodes(String type) {
        Node[] RPNodes = new Node[differentWagonValues.length];
        for (int i = 0; i < differentWagonValues.length; i++) {
            Node RP = new Node(type + differentWagonValues[i]);
            RPNodes[i] = RP;
        }
        return RPNodes;
    }

    private void addEdges(int[][] C_L_arrays, int[][] C_R_arrays) {
        for (int i = 1; i < differentWagonValues.length; i++) { // take care when indexing this
            int firstPosOfCurrentValue = getFirstOccurrence(wagonValues, differentWagonValues[i - 1]);
            int lastPosOfCurrentValue = getLastOccurrence(wagonValues, differentWagonValues[i - 1]);
            LPNodes[i - 1].addDestination(LPNodes[i], C_L_arrays[i][lastPosOfCurrentValue]);  // when starting from LP on this layer, the pointer will end up at the rightmost position
            LPNodes[i - 1].addDestination(RPNodes[i], C_R_arrays[i][lastPosOfCurrentValue]);

            RPNodes[i - 1].addDestination(LPNodes[i], C_L_arrays[i][firstPosOfCurrentValue]); // when starting from RP on this layer, the pointer will end up at the leftmost position of that value
            RPNodes[i - 1].addDestination(RPNodes[i], C_R_arrays[i][firstPosOfCurrentValue]);

            // because of the way in which the cost-arrays are constructed, the "lack of care" for adjusting the indices here should be alright
            // i.e. not adjusting for the fact that actually, the original last index of the current value does not have a cost on the next layer anymore, because the value of that index was removed in the previous iteration
        }
    }
    private int getFirstOccurrence(int[] arr, int z) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == z) {
                return i;
            }
        }
        throw new RuntimeException("The array does not contain the number.");
    }

    private int getLastOccurrence(int[] arr, int z) {
        for (int i = arr.length - 1; i > -1; i--) {
            if (arr[i] == z) {
                return i;
            }
        }
        throw new RuntimeException("The array does not contain the number.");
    }

    public Node getNodeByLayer(String type, int layer) {    // TODO: figure out whether these errors have to be checked? (out of bounds)
        if (layer >= differentWagonValues.length) {
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
}
