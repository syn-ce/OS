import java.util.*;

/**
 * A Rail consists of a Stack of wagons, the value of each represented by an Integer. There may be gaps in the numbering
 * and an arbitrary number of wagons may have the same value. Out of all the wagons currently on the Rail, only one
 * can ever be moved, that being the "last" one. Similarly, a wagon can only be added at the "end" of the Rail.
 */
public class Rail {
    private final String name;

    private final Stack<Integer> wagons;

    /**
     * Constructs a new Rail with the specified name and a Stack representing the wagon values in their correct
     * ordering. The top of the Stack will be interpreted as the last wagon, meaning the wagon which can be accessed.
     * @param name Name of the Rail. Used in the log.
     * @param wagons Stack of wagons in correct order. Top of Stack is the side of the Rail from which the last wagon
     *               can be accessed.
     */
    public Rail(String name, Integer[] wagons) {
        this.name = name;
        this.wagons = arrToReverseStack(wagons);
    }

    public Rail(String name) {
        this.name = name;
        this.wagons = new Stack<>();
    }

    /**
     * Removes the last wagon of the Rail.
     * @return Wagon which has been removed.
     */
    public Integer removeWagon() {
        if (wagons.isEmpty()) {
            return null;
        }
        return wagons.pop();
    }

    /**
     * Adds a wagon to the end of the Rail.
     * @param wagonValue Value of wagon to be added.
     */
    public void addWagon(int wagonValue) {
        wagons.add(wagonValue);
    }

    /**
     * @return int-array as copy of the wagon-values on the rail.
     */
    public int[] getWagonValues() {
        int[] copy = new int[wagons.size()];
        for (int i = 0; i < wagons.size(); i++) {
            copy[i] = wagons.get(i);
        }
        return copy;
    }

    /**
     *
     * @return String-representation of the wagons on the rail. The top of the wagons-stack represents the last wagon on
     * the Rail.
     */
    public String getWagonsString() {
        String s = wagons.toString();
        return s.substring(1, s.length() - 1).replace(",", "");
    }

    /**
     *
     * @return String-representation of the wagons on the rail in reverse order.
     */
    public String getReverseWagonString() {
        Stack<Integer> temp = (Stack<Integer>) wagons.clone();
        Collections.reverse(temp);
        String s = temp.toString();
        return s.substring(1, s.length()-1).replace(",", "");
    }

    public String getName() {
        return name;
    }

    /**
     * @return The value of the last wagon.
     */
    public Integer getNextWagonValue() {
        if (wagons.isEmpty()) {
            return null;
        }
        return wagons.peek();
    }

    /**
     * @param value Value which is searched.
     * @return Smallest position of a wagon with value value. Starts searching from the end of the train (the last wagon).
     * Is equal to the number of wagons which would have to be moved to gain access to the nearest wagon with value.
     * If not present, return -1.
     */
    public int getSmallestPosOfValue(int value) {
        ArrayList<Integer> tempFuck = new ArrayList<>();
        int firstPos = 0;
        while (!wagons.isEmpty()) {
            if (wagons.peek() == value) {
                Collections.reverse(tempFuck);
                wagons.addAll(tempFuck);
                return firstPos;
            }
            tempFuck.add(wagons.pop());
            firstPos++;
        }
        Collections.reverse(tempFuck);
        wagons.addAll(tempFuck);
        return -1;
    }

    /**
     * Converts an array of Integers to a Stack. The first Integer in the array will be the top of the Stack.
     *
     * @param arr Array to be converted.
     * @return Stack containing the values of the array. First value in the array will be at the top of the Stack.
     */
    private Stack<Integer> arrToReverseStack(Integer[] arr) {
        List<Integer> wagons = Arrays.asList(arr.clone());
        Collections.reverse(wagons);
        Stack<Integer> aS = new Stack<>();
        aS.addAll(wagons);
        return aS;
    }

    /**
     * Getter for the value of the wagon positioned at pos. Indexing starts at 0 from the last wagon.
     * @param pos Position of the wagon in the Stack of the Rail.
     * @return Value of wagon at pos.
     */
    public int getWagonValue(int pos) {
        if (pos >= wagons.size()) {
            return -1;
        }
        return wagons.get(pos);
    }

    public int getNrOfWagons() {
        return wagons.size();
    }
}
