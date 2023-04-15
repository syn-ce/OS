import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
// ALL WAGONS MUST HAVE POSITIVE NUMBERS
public class Rail {
    private final String name;

    private final Stack<Integer> wagons;

    public Rail(String name, Stack<Integer> wagons) {
        this.name = name;
        this.wagons = wagons;
    }

    public Rail(String name) {
        this.name = name;
        this.wagons = new Stack<>();
    }

    public Integer removeWagon() {
        if (wagons.isEmpty()) {
            return null;
        }
        return wagons.pop();
    }

    public void addWagon(int waggonNr) {
        wagons.add(waggonNr);
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
     * @return String-representation of the wagons on the rail. Note that the top of the wagons-stack represents the back of the train.
     */
    public String getWagonsString() {
        String s = wagons.toString();
        return s.substring(1, s.length() - 1).replace(",", "");
    }

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
