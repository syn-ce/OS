/**
 * The OptimalPathCalculator calculates the optimal path to be taken when moving the wagon values from the parking Rail
 * to the train Rail. The OptimalPathCalculator will assume that initially all wagons are parked on the parking Rail.
 * The optimal path is calculated by constructing a graph related to the problem and finding the shortest path between
 * specific nodes.
 */
public class OptimalPathCalculator {
    private final int[] uniqueWagonValuesAscending;
    private final int[] wagons;
    private final String[] optimalPath;
    private RailGraph railGraph;
    /**
     * Constructs a new OptimalPathCalculator and calculates the optimal path using a RailGraph and Dijkstra's algorithm.
     *
     * @param wagonValues Wagon values in their correct order, meaning wagonValues[0] is the top wagon of the Stack
     *                     (the "last" wagon).
     */
    public OptimalPathCalculator(int[] wagonValues, int[] uniqueWagonValuesAscending) {
        wagons = wagonValues.clone();  // TODO: clone probably not necessary here, but check where it is
        // initialise different wagons-values unique ascending
        this.uniqueWagonValuesAscending = uniqueWagonValuesAscending;

        // construct cost-arrays
        int[][] C_L_arrays = getC_L_arrays();
        int[][] C_R_arrays = getC_R_arrays();

        // construct graph for problem and solve using Dijkstra
        railGraph = new RailGraph(wagons, this.uniqueWagonValuesAscending, C_L_arrays, C_R_arrays);

        // get the distances of the last RP and LP node to LP1 (the starting node)
        Node lastLPNode = railGraph.getNodeByLayer("LP", railGraph.getNrOfLayers() - 1);
        Node lastRPNode = railGraph.getNodeByLayer("RP", railGraph.getNrOfLayers() - 1);

        if (lastLPNode.getDistance() <= lastRPNode.getDistance()) {    // for now takes lastLPNode if they are of equal length
            optimalPath = lastLPNode.getShortestPathNames();
        } else {
            optimalPath = lastRPNode.getShortestPathNames();
        }
    }

    public String[] getOptimalPath() {
        return optimalPath;
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

    /**
     * Calculates all left-cost arrays, meaning a left cost array for every unique value in the array.
     *
     * @return Array of all left-cost arrays.
     */
    private int[][] getC_L_arrays() {
        int[][] C_L_arrays = new int[uniqueWagonValuesAscending.length][];
        for (int i = 0; i < C_L_arrays.length; i++) {
            C_L_arrays[i] = getC_Li(wagons, uniqueWagonValuesAscending[i]);
        }
        return C_L_arrays;
    }

    /**
     * Calculates all right-cost arrays, meaning a right cost array for every unique value in the array.
     *
     * @return Array of all right-cost arrays.
     */
    private int[][] getC_R_arrays() {
        int[][] C_R_arrays = new int[uniqueWagonValuesAscending.length][];
        for (int i = 0; i < C_R_arrays.length; i++) {
            C_R_arrays[i] = getC_Ri(wagons, uniqueWagonValuesAscending[i]);
        }
        return C_R_arrays;
    }

    /**
     * Calculates the left-cost array for the given array and one of the values present in the array, z. The left-cost
     * array is an array of values 0 to (arr.length-1). The cost of each position k is determined by counting the numbers
     * greater than z between C_Li[k] and the leftmost position of z in the array (m). C_Li[k] has to be understood as
     * the cost of starting from exactly "before" position k in the array, meaning that for instance C_Li[0] will be the
     * number of values > z up to position m in the array, including the value at arr[0]. However, this consideration is
     * symmetric to the leftmost occurrence of z in arr (m), meaning that for any position k > m, arr[k] will not be
     * considered. This is especially true for k = (arr.length) (which would of course result in an OutOfBoundsException).
     *
     * @param arr Array to determine the left-cost array of.
     * @param z   Value present in the array after which the cost shall be calculated for all positions.
     * @return Left cost array for value z.
     */
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


    /**
     * Calculates the right-cost array for the given array and one of the values present in the array, z. The right-cost
     * array is an array of values 0 to (arr.length-1). The cost of each position k is determined by counting the numbers
     * greater than z between C_Li[k] and the rightmost position of z in the array (m). C_Li[k] has to be understood as
     * the cost of starting from exactly "before" position k in the array, meaning that for instance C_Li[0] will be the
     * number of values > z up to position m in the array, including the value at arr[0]. However, this consideration is
     * symmetric to the rightmost occurrence of z in arr (m), meaning that for any position k > m, arr[k] will not be
     * considered. This is especially true for k = (arr.length) (which would of course result in an OutOfBoundsException).
     *
     * @param arr Array to determine the right-cost array of.
     * @param z   Value present in the array after which the cost shall be calculated for all positions.
     * @return Right cost array for value z.
     */
    private int[] getC_Ri(int[] arr, int z) { // gets the cost array for layer i, corresponding to wagon-nr z
        int[] C_Rz = new int[arr.length + 1];
        int lastInd = getLastOccurrence(arr, z);

        C_Rz[lastInd + 1] = 0;
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
