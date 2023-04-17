import java.util.*;

public class OptimalPathCalculator {
    private final int[] differentWagonValues;
    private final int[] wagons;
    private final String[] optimalPath;

    public OptimalPathCalculator(int[] wagonNumbers) {
        wagons = wagonNumbers;
        // initialise different wagons-values unique ascending
        differentWagonValues = getUniqueWagonValuesAscending(wagonNumbers);

        // construct cost-arrays
        int[][] C_L_arrays = getC_Li_arrays(wagonNumbers);
        int[][] C_R_arrays = getC_Ri_arrays(wagonNumbers);

        // initialise lp- and rp-nodes for every unique wagon-nr
        Node[] LPNodes = initialiseNodes("LP");
        Node[] RPNodes = initialiseNodes("RP");

        // add edges between nodes
        addEdges(LPNodes, RPNodes, C_L_arrays, C_R_arrays);

        // get starting node -> will always have to start at LP1
        Node LP1 = LPNodes[0];
        // calculate minimum distance from LP1 to all nodes using Dijkstra's algorithm
        (new Dijkstra()).calculateShortestPathFromSource(LP1);

        // get the distances of the last RP and LP node to LP1
        Node lastLPNode = LPNodes[differentWagonValues.length - 1];
        Node lastRPNode = RPNodes[differentWagonValues.length - 1];

        if (lastLPNode.getDistance() <= lastRPNode.getDistance()) {    // for now takes lastLPNode if they are of equal length
            optimalPath = lastLPNode.getShortestPath().stream().map(Node::getName).toArray(String[]::new);
        } else {
            optimalPath = lastRPNode.getShortestPath().stream().map(Node::getName).toArray(String[]::new);
        }
    }

    private void addEdges(Node[] LPNodes, Node[] RPNodes, int[][] C_L_arrays, int[][] C_R_arrays) {
        for (int i = 1; i < differentWagonValues.length; i++) { // take care when indexing this

            int firstPosOfCurrentValue = getFirstPos(i - 1);
            int lastPosOfCurrentValue = getLastPos(i - 1);
            LPNodes[i - 1].addDestination(LPNodes[i], C_L_arrays[i][lastPosOfCurrentValue]);  // when starting from LP on this layer, the pointer will end up at the rightmost position
            LPNodes[i - 1].addDestination(RPNodes[i], C_R_arrays[i][lastPosOfCurrentValue]);

            RPNodes[i - 1].addDestination(LPNodes[i], C_L_arrays[i][firstPosOfCurrentValue]); // when starting from RP on this layer, the pointer will end up at the leftmost position of that value
            RPNodes[i - 1].addDestination(RPNodes[i], C_R_arrays[i][firstPosOfCurrentValue]);

            // because of the way in which the cost-arrays are constructed, the "lack of care" for adjusting the indices here should be alright
            // i.e. not adjusting for the fact that actually, the original last index of the current value does not have a cost on the next layer anymore, because the value of that index was removed in the previous iteration
        }
    }

    private Node[] initialiseNodes(String type) {
        Node[] RPNodes = new Node[differentWagonValues.length];
        for (int i = 0; i < differentWagonValues.length; i++) {
            Node RP = new Node(type + differentWagonValues[i]);
            RPNodes[i] = RP;
        }
        return RPNodes;
    }

    private int[] getUniqueWagonValuesAscending(int[] wagonNumbers) {
        Set<Integer> helper = new HashSet<>();
        for (int wagon : wagonNumbers) {
            helper.add(wagon);
        }
        int[] temp = helper.stream().mapToInt(Number::intValue).toArray();
        Arrays.sort(temp);
        return temp;
    }

    public String[] getOptimalPath() {
        return optimalPath;
    }

    public static int getFirstOccurrence(int[] arr, int z) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == z) {
                return i;
            }
        }
        throw new RuntimeException("The array does not contain the number.");
    }

    public static int getLastOccurrence(int[] arr, int z) {
        for (int i = arr.length - 1; i > -1; i--) {
            if (arr[i] == z) {
                return i;
            }
        }
        throw new RuntimeException("The array does not contain the number.");
    }

    private int[][] getC_Li_arrays(int[] wagonNumbers) {
        int[][] C_L_arrays = new int[differentWagonValues.length][];
        for (int i = 0; i < C_L_arrays.length; i++) {
            C_L_arrays[i] = getC_Li(wagonNumbers, differentWagonValues[i]);
        }
        return C_L_arrays;
    }

    private int[][] getC_Ri_arrays(int[] wagonNumbers) {
        int[][] C_R_arrays = new int[differentWagonValues.length][];
        for (int i = 0; i < C_R_arrays.length; i++) {
            C_R_arrays[i] = getC_Ri(wagonNumbers, differentWagonValues[i]);
        }
        return C_R_arrays;
    }

    private int[] getC_Li(int[] arr, int z) { // gets the cost array for layer i, corresponding to wagon-nr z
        int[] C_Lz = new int[arr.length + 1];
        int firstInd = getFirstOccurrence(arr, z);

        C_Lz[firstInd + 1] = 0;
        for (int i = firstInd - 1; i > -1; i--) {
            C_Lz[i] = C_Lz[i + 1] + 1;    // it is guaranteed that there will be no other occurrence of num before the first one
            if (arr[i] <= z) {
                C_Lz[i]--;  // if the number is smaller than z, it will already have been removed at this point and therefore not have to be moved
            }
        }

        for (int i = firstInd + 1; i < arr.length + 1; i++) {
            C_Lz[i] = C_Lz[i - 1] + 1;
            if (arr[i - 1] <= z) {   // if number at position is equal to or smaller than num, cost should not increase
                C_Lz[i]--;
            }
        }
        return C_Lz;
    }

    private int[] getC_Ri(int[] arr, int z) { // gets the cost array for layer i, corresponding to wagon-nr z
        int[] C_Rz = new int[arr.length + 1];
        int lastInd = getLastOccurrence(arr, z);

        C_Rz[lastInd + 1] = 0;  // TODO: check whether this is necessary
        for (int i = lastInd + 1; i < arr.length + 1; i++) {
            C_Rz[i] = C_Rz[i - 1] + 1;    // it is guaranteed that there will be no other occurrence of num before the first one
            if (arr[i - 1] <= z) {
                C_Rz[i]--;  // if the number is smaller than z, it will already have been removed at this point and therefore not have to be moved
            }
        }

        for (int i = lastInd - 1; i > -1; i--) {
            C_Rz[i] = C_Rz[i + 1] + 1;
            if (arr[i] <= z) {   // if number at position is equal to or smaller than num, cost should not increase
                C_Rz[i]--;
            }
        }
        return C_Rz;
    }

    private int getLastPos(int i) {
        return getLastOccurrence(wagons, differentWagonValues[i]);
    }

    private int getFirstPos(int i) {
        return getFirstOccurrence(wagons, differentWagonValues[i]);
    }

}
