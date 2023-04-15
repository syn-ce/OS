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

    private final Shunter shunter;
    public TrainStation(Integer... ints) {
        // 12, 4, 19, 20, 3, 16, 4, 6, 9, 8
        parkingRail = new Rail("parkingRail", listToStack(ints));
        switchingRail = new Rail("switchingRail", new Stack<>());
        trainRail = new Rail("trainRail", new Stack<>());

        String[] optimalPath = getOptimalPath(integerToIntArray(ints));

        shunter = new Shunter(parkingRail, switchingRail, trainRail);
        shunter.shuntNew(optimalPath);
    }

    private int[] integerToIntArray(Integer[] ints) {
        int[] arr = new int[ints.length];
        for (int i = 0; i < ints.length; i++){
            arr[i] = ints[i];
        }
        return arr;
    }

    private String[] getOptimalPath(int[] wagonValues) {
        DirectedAcyclicGraph g = new DirectedAcyclicGraph(wagonValues);
        return g.getOptimalPath();
    }

    private Stack<Integer> listToStack(Integer[] arr) {
        List<Integer> wagons = Arrays.asList(arr);
        Collections.reverse(wagons);
        Stack<Integer> aS = new Stack<>();
        aS.addAll(wagons);
        return aS;
    }
}
