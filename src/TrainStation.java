import java.util.*;

/**
 * Contains all the Rails of the station and instructs the Shunter to move all wagons from the parkingRail to the
 * trainRail.
 */

public class TrainStation {

    private final Rail parkingRail;
    private final Rail switchingRail;
    private final Rail trainRail;
    private final int[] wagonValues;
    private final int[] uniqueWagonValuesAscending;
    private OptimalPathCalculator optimalPathCalculator;
    private Shunter shunter;

    /**
     * Constructs a new TrainStation with the specified wagon values. The wagon values represent the values of the
     * wagons on the parkingRail from left to right, the leftmost wagon being the wagon which can be accessed.
     *
     * @param wagonValues The values of the wagons in correct order, with the left wagon being the accessible wagon.
     */
    public TrainStation(Integer... wagonValues) {
        parkingRail = new Rail("parkingRail", wagonValues);
        switchingRail = new Rail("switchingRail");
        trainRail = new Rail("trainRail");
        this.wagonValues = integerToIntArray(wagonValues);
        uniqueWagonValuesAscending = getUniqueWagonValuesAscending(this.wagonValues);
        optimalPathCalculator = new OptimalPathCalculator(this.wagonValues, this.uniqueWagonValuesAscending);
    }

    /**
     * Instruct the Shunter to move the wagons from the parkingRail to the trainRail with as little moves as possible.
     * @return The log size of the Shunter (i.e. the number of times the Shunter moved a wagon).
     */
    public int moveWagons() {
        String[] optimalPath = getOptimalPath();
        shunter = new Shunter(parkingRail, switchingRail, trainRail, uniqueWagonValuesAscending);
        shunter.shuntNew(optimalPath);
        return shunter.getLogSize();
    }

    /**
     * Converts an Array of Integers to an Array of arr.
     *
     * @param arr Integer-Array to be converted.
     * @return int-Array with values of arr.
     */
    private int[] integerToIntArray(Integer[] arr) {
        int[] ints = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ints[i] = arr[i];
        }
        return ints;
    }

    /**
     * Returns an Array with all values present in wagonValues except for the duplicates, in ascending order.
     *
     * @param wagonValues Array out of which the unique values are to be taken.
     * @return Array of all unique values present in wagonValues, in ascending order.
     */
    private int[] getUniqueWagonValuesAscending(int[] wagonValues) {
        Set<Integer> helper = new HashSet<>();
        for (int wagon : wagonValues) {
            helper.add(wagon);
        }
        int[] temp = helper.stream().mapToInt(Number::intValue).toArray();
        Arrays.sort(temp);
        return temp;
    }

    /**
     * Calculates the optimal path to be taken when moving the wagons.
     *
     * @return An array of Strings containing the names of the nodes which are traversed in the optimal path
     */

    private String[] getOptimalPath() {
        return optimalPathCalculator.getOptimalPath();
    }

}
