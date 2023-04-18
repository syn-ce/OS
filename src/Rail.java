import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
// ALL WAGONS MUST HAVE POSITIVE NUMBERS

/**
 * A Rail is
 */
public class Rail {
    private final String name;

    private final Stack<Integer> wagons;

    /**
     * Constructs a new Rail with the specified name and a Stack representing the wagon values in their correct
     * ordering. The top of the Stack will be interpreted as the upfront-wagon, meaning the wagon which can be accessed.
     * @param name Name of the Rail. Used in the log.
     * @param wagons Stack of wagons in correct order. Top of Stack is the side of the Rail from which wagon can be accessed.
     */
    public Rail(String name, Stack<Integer> wagons) {
        this.name = name;
        this.wagons = wagons;
    }

    public Rail(String name) {
        this.name = name;
        this.wagons = new Stack<>();
    }

    /**
     * Removes the upfront wagon of the Rail.
     * @return Wagon which has been removed.
     */
    public Integer removeWagon() {
        if (wagons.isEmpty()) {
            return null;
        }
        return wagons.pop();
    }

    /**
     * Adds a wagon to the Rail.
     * @param wagonValue Value of wagon to be added.
     */
    public void addWagon(int wagonValue) {
        wagons.add(wagonValue);
    }

    /**
     * @return int-array as copy of the wagon-values on the rail.
     */
    public Integer[] getWagonValues() {
        Integer[] copy = new Integer[wagons.size()];
        for (int i = 0; i < wagons.size(); i++) {
            copy[i] = wagons.get(i);
        }
        return copy;
    }

    /**
     *
     * @return String-representation of the wagons on the rail. The top of the wagons-stack represents the last wagon on the Rail.
     */
    public String getWagonsString() {
        String s = wagons.toString();
        return s.substring(1, s.length() - 1).replace(",", "");
    }

    /**
     *
     * @return String-representation of the wagons on the rail in backwards order.
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
     * @return The number/value of the upfront wagon.
     */
    public Integer getNextWagon() {
        if (wagons.isEmpty()) {
            return null;
        }
        return wagons.peek();
    }

    /**
     * @param nr Value of wagon which is searched.
     * @return Smallest position of a wagon with value nr. Starts searching from the back of the train. Is equal to the
     * number of wagons which would have to be moved to gain access to the nearest wagon with nr. If not present, return -1.
     */
    public int getSmallestPosOfNr(int nr) {
        ArrayList<Integer> tempFuck = new ArrayList<>();
        int firstPos = 0;
        while (!wagons.isEmpty()) {
            if (wagons.peek() == nr) {
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

    // TODO: can cause exception, this might be a problem depending on how it's called

    /**
     * Getter for the value of the wagon positioned at pos.
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
