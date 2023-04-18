import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Log {
    private List<String[]> actions = new ArrayList<>();
    ;
    private int actionId = 1;
    /**
     * Stores the length of the longest entry of each column. Stored as Integers as it will be converted to Object[].
     */
    private Integer[] columnLengths;
    /**
     * Stores for each column whether that column should be aligned to the right or not (default is left). Text will be aligned left, numeric columns right.
     */
    private boolean[] alignRight;
    /**
     * Specifies how much space there should be after each column.
     */
    private int spaceBetweenColumns = 3;

    /**
     * Constructs a new log with the specified column-headers and aligns all columns i for which alignRight[i] == true
     * to the right, all others to the left.
     * @param columns Column-headers of all columns.
     * @param alignRight Array of booleans where alignRight[i] specifies whether columns[i] should be aligned to the
     *                   right (true) or the left (false).
     */
    public Log(String[] columns, boolean[] alignRight) {
        actions.add(columns);
        columnLengths = new Integer[columns.length];
        Arrays.fill(columnLengths, 1);
        updateColumnLengths(columns);
        this.alignRight = alignRight;
    }

    // TODO: check this (different lengths)
    /**
     * Adds an action to the log using the specified entries. If there are more entries than columns, the last entries
     * are ignored. If there are too few, the last columns remain empty.
     * @param entries
     */
    public void addAction(String... entries) {
        String[] action = new String[entries.length + 1];
        action[0] = Integer.toString(actionId++);
        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i];
            action[i + 1] = entry;
        }
        updateColumnLengths(action);
        actions.add(action);
    }

    /**
     * Checks if the length of any entry of the action (including the additional whitespace) exceeds the length of its
     * column. If so, updates the length of the column accordingly.
     *
     * @param action An array of Strings containing an entry for every column of the protokoll.
     */
    private void updateColumnLengths(String[] action) {
        for (int i = 0; i < action.length; i++) {
            if (action[i].length() > columnLengths[i]) {
                columnLengths[i] = action[i].length();
            }
        }
    }

    /**
     * Multiplies the specified String the specified number of times and returns the new String.
     * @param s String to be multiplied.
     * @param repetitions Number of repetitions of the String.
     * @return Multiplied String.
     */
    private String multiplyString(String s, int repetitions) {
        return new String(new char[repetitions]).replace("\0", s);
    }

    /**
     * Constructs a new template protocol line.
     * @return String template protocol line.
     */
    private String constructProtocolLine() {
        StringBuilder s = new StringBuilder();

        String spaceBetween = multiplyString(" ", spaceBetweenColumns);
        for (int i = 0; i < columnLengths.length; i++) {
            s.append("%%").append(!alignRight[i] ? "-" : "").append("%ds").append(spaceBetween);
        }
        return String.format(s.append("\n").toString(), (Object[]) columnLengths);
    }

    // TODO: implement way to check whether the protokoll has changed since the last time the protokoll has been printed; if not, just return the String previously constructed (should save some time)

    /**
     * Prints the protocol into the console.
     */
    public void print() {
        StringBuilder printableProtokoll = new StringBuilder();
        for (String[] action : actions) {
            String s = constructProtocolLine();
            s = String.format(s, (Object[]) action);
            printableProtokoll.append(s);
        }
        System.out.println(printableProtokoll);
    }

    /**
     * Getter for the size of the protocol, i.e. the number of actions added to the protocol so far.
     * @return The number of actions recorded so far.
     */
    public int getSize() {
        return actionId-1;
    }
}
