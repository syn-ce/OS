import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * This class will contain all the Rails of the station and give the optimal instructions to the Shunter.
 */

public class TrainStation {

    private final Rail parkingRail;
    private final Rail switchingRail;
    private final Rail trainRail;
    private final int[] wagonValues;
    private Shunter shunter;

    /**
     * Constructs a new TrainStation with the specified wagon values. All wagon values will be
     * @param ints The values of the wagons in correct order, with the left wagon being the first wagon.
     */
    public TrainStation(Integer... ints) {
        parkingRail = new Rail("parkingRail", listToStack(ints));
        switchingRail = new Rail("switchingRail", new Stack<>());
        trainRail = new Rail("trainRail", new Stack<>());
        wagonValues = integerToIntArray(ints);
    }

    public int moveNew() {
        String[] optimalPath = getOptimalPath();
        shunter = new Shunter(parkingRail, switchingRail, trainRail);
        shunter.shuntNew(optimalPath);
        return shunter.getLogSize();
    }
    public int moveOld() {
        shunter = new Shunter(parkingRail, switchingRail, trainRail);
        shunter.shunt2();
        return shunter.getLogSize();
    }

    public int moveOld2() {
        shunter = new Shunter(parkingRail, switchingRail, trainRail);
        shunter.shunt2equal();
        return shunter.getLogSize();
    }

    public int moveOld3() {
        shunter = new Shunter(parkingRail, switchingRail, trainRail);
        shunter.shunt3();
        return shunter.getLogSize();
    }

    /**
     * Converts an Array of Integers to an Array of arr.
     * @param arr Integer-Array to be converted.
     * @return int-Array with values of arr.
     */
    private int[] integerToIntArray(Integer[] arr) {
        int[] ints = new int[arr.length];
        for (int i = 0; i < arr.length; i++){
            ints[i] = arr[i];
        }
        return ints;
    }

    /**
     * Calculates the optimal path to be taken when moving the wagons.
     * @return An Array of Strings containing the names of the nodes which are traversed in the optimal path
     */

    private String[] getOptimalPath() {
        OptimalPathCalculator g = new OptimalPathCalculator(wagonValues.clone());
        return g.getOptimalPath();
    }

    /**
     * Converts an array of Integers to a Stack.
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
