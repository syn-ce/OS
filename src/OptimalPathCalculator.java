import java.util.*;

/**
 * The OptimalPathCalculator calculates the optimal path to be taken when moving the wagon values from the parking Rail
 * to the train Rail. The OptimalPathCalculator will assume that initially all wagons are parked on the parking Rail.
 * The optimal path is calculated by constructing a graph related to the problem and finding the shortest path between
 * specific nodes.
 */
public class OptimalPathCalculator {
    private final int[] differentWagonValues;
    private final int[] wagons;
    private final String[] optimalPath;

    /**
     * Constructs a new OptimalPathCalculator and calculates the optimal path using a graph and Dijkstra's algorithm.
     * @param wagonNumbers
     */
    public OptimalPathCalculator(int[] wagonNumbers, int[] uniqueWagonValuesAscending) {
        wagons = wagonNumbers.clone();  // TODO: clone probably not necessary here, but check where it is
        // initialise different wagons-values unique ascending
        differentWagonValues = uniqueWagonValuesAscending;

        // construct cost-arrays
        int[][] C_L_arrays = getC_Li_arrays(wagonNumbers);
        int[][] C_R_arrays = getC_Ri_arrays(wagonNumbers);

        // construct graph for problem and solve using Dijkstra
        CustomGraph g = new CustomGraph(wagons, differentWagonValues, C_L_arrays, C_R_arrays);

        // get the distances of the last RP and LP node to LP1 (the starting node)
        Node lastLPNode = g.getNodeByLayer("LP", g.getNrOfLayers()-1);
        Node lastRPNode = g.getNodeByLayer("RP", g.getNrOfLayers()-1);

        if (lastLPNode.getDistance() <= lastRPNode.getDistance()) {    // for now takes lastLPNode if they are of equal length
            optimalPath = lastLPNode.getShortestPathStrings();
        } else {
            optimalPath = lastRPNode.getShortestPathStrings();
        }
    }
    
    public String[] getOptimalPath() {
        return optimalPath;
    }

    /**
     * Gets the index of the first occurrence of an int in an Array of ints.
     * @param arr Array in which to look for the value.
     * @param z Value to find the first occurrence of.
     * @return Index of the first occurrence of the value in the array.
     * @throws RuntimeException If the value is not present in the array.
     */
    public static int getFirstOccurrence(int[] arr, int z) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == z) {
                return i;
            }
        }
        throw new RuntimeException("The array does not contain the number.");
    }

    /**
     * Gets the index of the last occurrence of an int in an Array of ints.
     * @param arr Array in which to look for the value.
     * @param z Value to find the last occurrence of.
     * @return Index of the last occurrence of the value in the array.
     * @throws RuntimeException If the value is not present in the array.
     */
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
}
