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
    private boolean print = false;
    public TrainStation(Integer... ints) {
        // 12, 4, 19, 20, 3, 16, 4, 6, 9, 8
        parkingRail = new Rail("parkingRail", listToStack(ints));
        switchingRail = new Rail("switchingRail", new Stack<>());
        trainRail = new Rail("trainRail", new Stack<>());

        // when the shunter is moved above the optimal path, the optimal path changes and is not optimal anymore??
        wagonValues = integerToIntArray(ints);
        if (print) System.out.println("Before: " + Arrays.toString(wagonValues));

    }

    public int moveNew() {
        String[] optimalPath = getOptimalPath();
        if (print) System.out.println("optimal path = " + Arrays.toString(optimalPath));
        shunter = new Shunter(parkingRail, switchingRail, trainRail);
        shunter.shuntNew(optimalPath);
        return shunter.getLogSize();
    }
    public int moveOld() {
        shunter = new Shunter(parkingRail, switchingRail, trainRail);
        shunter.shunt2();
        if (print) System.out.println("Log size = " + shunter.getLogSize());
        return shunter.getLogSize();
    }

    public int moveOld2() {
        shunter = new Shunter(parkingRail, switchingRail, trainRail);
        shunter.shunt2equal();
        if (print) System.out.println("Log size = " + shunter.getLogSize());
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
        if (print) System.out.println("wagonValues = " + Arrays.toString(wagonValues));
        // TODO: the wagon values get messed up - figure out why.
        DirectedAcyclicGraph g = new DirectedAcyclicGraph(wagonValues.clone());
//        DirectedAcyclicGraph g = new DirectedAcyclicGraph(wagonValues.clone());
//        DirectedAcyclicGraph g = new DirectedAcyclicGraph(wagonValues);
        return g.getOptimalPath();
    }

    private Stack<Integer> listToStack(Integer[] arr) { // TODO: figure out and fix this problem; this CHANGES ARR,
        // TODO: this should DEFINITELY NOT HAPPEN
        if (print) System.out.println(Arrays.toString(arr));
        List<Integer> wagons = Arrays.asList(arr.clone());
        Collections.reverse(wagons);
        Stack<Integer> aS = new Stack<>();
        aS.addAll(wagons);
        if (print) System.out.println(Arrays.toString(arr));
        return aS;
    }
}
