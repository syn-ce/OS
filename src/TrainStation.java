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
    private int[] integerToIntArray(Integer[] ints) {
        int[] arr = new int[ints.length];
        for (int i = 0; i < ints.length; i++){
            arr[i] = ints[i];
        }
        return arr;
    }

    private String[] getOptimalPath() {
        OptimalPathCalculator g = new OptimalPathCalculator(wagonValues.clone());
        return g.getOptimalPath();
    }

    private Stack<Integer> listToStack(Integer[] arr) {
        List<Integer> wagons = Arrays.asList(arr.clone());
        Collections.reverse(wagons);
        Stack<Integer> aS = new Stack<>();
        aS.addAll(wagons);
        return aS;
    }
}
