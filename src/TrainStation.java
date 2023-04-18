import java.util.*;

/**
 * This class will contain all the Rails of the station and give the optimal instructions to the Shunter.
 */

public class TrainStation {

    private final Rail parkingRail;
    private final Rail switchingRail;
    private final Rail trainRail;
    private final int[] wagonValues;
    private final int[] uniqueWagonValuesAscending;
    private Shunter shunter;

    /**
     * Constructs a new TrainStation with the specified wagon values. All wagon values will be
     *
     * @param ints The values of the wagons in correct order, with the left wagon being the first wagon.
     */
    public TrainStation(Integer... ints) {
        parkingRail = new Rail("parkingRail", listToStack(ints));
        switchingRail = new Rail("switchingRail", new Stack<>());
        trainRail = new Rail("trainRail", new Stack<>());
        wagonValues = integerToIntArray(ints);
        uniqueWagonValuesAscending = getUniqueWagonValuesAscending(wagonValues);
    }

    public int moveNew() {
        String[] optimalPath = getOptimalPath();
        shunter = new Shunter(parkingRail, switchingRail, trainRail, uniqueWagonValuesAscending);
        shunter.shuntNew(optimalPath);
        return shunter.getLogSize();
    }

    public int moveOld() {
        shunter = new Shunter(parkingRail, switchingRail, trainRail, uniqueWagonValuesAscending);
        shunter.shunt2();
        return shunter.getLogSize();
    }

    public int moveOld2() {
        shunter = new Shunter(parkingRail, switchingRail, trainRail, uniqueWagonValuesAscending);
        shunter.shunt2equal();
        return shunter.getLogSize();
    }

    public int moveOld3() {
        shunter = new Shunter(parkingRail, switchingRail, trainRail, uniqueWagonValuesAscending);
        shunter.shunt3();
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
     * Returns an Array with all values present in wagonNumbers except for the duplicates, in ascending order.
     *
     * @param wagonNumbers Array out of which the unique values are to be taken.
     * @return Array of all unique values present in wagonNumbers, in ascending order.
     */
    private int[] getUniqueWagonValuesAscending(int[] wagonNumbers) {
        Set<Integer> helper = new HashSet<>();
        for (int wagon : wagonNumbers) {
            helper.add(wagon);
        }
        int[] temp = helper.stream().mapToInt(Number::intValue).toArray();
        Arrays.sort(temp);
        return temp;
    }

    /**
     * Calculates the optimal path to be taken when moving the wagons.
     *
     * @return An Array of Strings containing the names of the nodes which are traversed in the optimal path
     */

    private String[] getOptimalPath() {
        OptimalPathCalculator g = new OptimalPathCalculator(wagonValues.clone(), uniqueWagonValuesAscending);
        return g.getOptimalPath();
    }

    /**
     * Converts an array of Integers to a Stack.
     *
     * @param arr
     * @return
     */
    private Stack<Integer> listToStack(Integer[] arr) {
        // TODO: why reverse??
        List<Integer> wagons = Arrays.asList(arr.clone());
        Collections.reverse(wagons);
        Stack<Integer> aS = new Stack<>();
        aS.addAll(wagons);
        return aS;
    }
}
