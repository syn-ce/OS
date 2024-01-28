package main.java.trainstation;

import main.java.graph.algorithm.OptimalPathCalculator;

import java.util.*;

/**
 * Contains all the Rails of the station and instructs the Switcher to move all wagons from the parkingRail to the
 * mainRail.
 */

public class TrainStation {

    private final Rail parkingRail;
    private final Rail sidingRail;
    private final Rail mainRail;
    private final int[] wagonValues;
    private final int[] uniqueWagonValuesAscending;
    private OptimalPathCalculator optimalPathCalculator;
    private Switcher switcher;

    /**
     * Constructs a new TrainStation with the specified wagon values. The wagon values represent the values of the
     * wagons on the parkingRail from left to right, the leftmost wagon being the wagon which can be accessed.
     *
     * @param wagonValues The values of the wagons in correct order, with the left wagon being the accessible wagon.
     */
    public TrainStation(Integer... wagonValues) {
        parkingRail = new Rail("Parking", wagonValues);
        sidingRail = new Rail("Siding");
        mainRail = new Rail("Main");
        this.wagonValues = integerToIntArray(wagonValues);
        uniqueWagonValuesAscending = getUniqueWagonValuesAscending(this.wagonValues);
        optimalPathCalculator = new OptimalPathCalculator(this.wagonValues, this.uniqueWagonValuesAscending);
    }

    /**
     * Instruct the Switcher to move the wagons from the parkingRail to the mainRail with as little moves as possible.
     * @param print Indicate if the Switcher's log shall be printed
     * @return The log size of the Switcher (i.e. the number of times the Switcher moved a wagon).
     */
    public int moveNew(boolean print) {
        String[] optimalPath = getOptimalPath();
        switcher = new Switcher(parkingRail, sidingRail, mainRail, uniqueWagonValuesAscending);
        switcher.shuntNew(optimalPath, print);
        return switcher.getLogSize();
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
