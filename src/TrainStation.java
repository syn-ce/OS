import java.util.*;

/**
 * Contains all the Rails of the station and instructs the Shunter to move all wagons from the parkingRail to the
 * mainRail.
 */

public class TrainStation {

    private final Rail parkingRail;
    private final Rail switchingRail;
    private final Rail mainRail;
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
        mainRail = new Rail("mainRail");
        this.wagonValues = integerToIntArray(wagonValues);
        uniqueWagonValuesAscending = getUniqueWagonValuesAscending(this.wagonValues);
        optimalPathCalculator = new OptimalPathCalculator(this.wagonValues, this.uniqueWagonValuesAscending);
        optimalPathCalculator.calculateOptimalPath();
    }

    /**
     * Instruct the Shunter to move the wagons from the parkingRail to the mainRail with as little moves as possible.
     * @return The log size of the Shunter (i.e. the number of times the Shunter moved a wagon).
     */
    public void moveWagonsFromParkingToMain() {
        String[] optimalPath = optimalPathCalculator.getOptimalPath();
        shunter = new Shunter(parkingRail, switchingRail, mainRail, uniqueWagonValuesAscending);
        shunter.moveWagonsFromParkingToMain(optimalPath);
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
}
