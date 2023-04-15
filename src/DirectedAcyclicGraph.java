import java.util.*;

public class DirectedAcyclicGraph {
    private int[] differentWagonValues;
    private int[] wagons;
    private String[] optimalPath;

    //    Node1[][] layers;    // for each layer stores a pair of nodes representing LP and RP

    public DirectedAcyclicGraph(int[] wagons) {
        this.wagons = wagons;
        // get different wagons-values unique ascending
        Set<Integer> helper = new HashSet<>();
        for (int wagon : wagons) {
            helper.add(wagon);
        }
        differentWagonValues = helper.stream().mapToInt(Number::intValue).toArray();
        Arrays.sort(differentWagonValues);

        // construct cost-arrays
        int[][] C_L_arrays = new int[differentWagonValues.length][];
        for (int i = 0; i < C_L_arrays.length; i++) {
            C_L_arrays[i] = getC_Li(wagons, differentWagonValues[i]);
        }
        int[][] C_R_arrays = new int[differentWagonValues.length][];
        for (int i = 0; i < C_R_arrays.length; i++) {
            C_R_arrays[i] = getC_Ri(wagons, differentWagonValues[i]);
        }

        //-------------------------------------------------
        Node[] LPNodes = new Node[differentWagonValues.length];
        Node[] RPNodes = new Node[differentWagonValues.length];

        // initialise all nodes -> maybe "collapse" nodes for which position of LP = position of RP into one node?
        // (this would probably make things a lot more complicated for relatively little reward, though)
        for (int i = 0; i < differentWagonValues.length; i++) {
            Node LP = new Node("LP" + differentWagonValues[i]);
            Node RP = new Node("RP" + differentWagonValues[i]);

            LPNodes[i] = LP;
            RPNodes[i] = RP;
        }


        // add edges between nodes

        // cost to remove all occurrences of all ones; there is no strategy here (yet) (actually, does it even have to be considered for determining the shortest path? -> Don't think it does..)
        int costForFirstRemoval = C_L_arrays[0][0];   // the first iteration will be started from the left
        // this cost should actually be equal to the first position (index) of the first wagon in the number-list (thank god for 0-indexing)

        for (int i = 1; i < differentWagonValues.length; i++) { // take care when indexing this

            int firstPosOfCurrentValue = getFirstPos(i - 1);
            int lastPosOfCurrentValue = getLastPos(i - 1);
            LPNodes[i - 1].addDestination(LPNodes[i], C_L_arrays[i][lastPosOfCurrentValue]);  // when starting from LP on this layer, the pointer will end up at the rightmost position
            LPNodes[i - 1].addDestination(RPNodes[i], C_R_arrays[i][lastPosOfCurrentValue]);

            RPNodes[i - 1].addDestination(LPNodes[i], C_L_arrays[i][firstPosOfCurrentValue]); // when starting from RP on this layer, the pointer will end up at the leftmost position of that value
            RPNodes[i - 1].addDestination(RPNodes[i], C_R_arrays[i][firstPosOfCurrentValue]);

            // because of the way in which the cost-arrays are constructed, the "lack of care" for adjusting the indices here should be alright
            // i.e. not adjusting for the fact that actually, the original last index of the current value does not have a cost on the next layer anymore, because the value of that index was removed in the previous iteration
        }

        for (Node n : LPNodes) {
            System.out.println(n.getName() + ": " + n.getAdjacentNodesString());
        }
        System.out.println();
        for (Node n : RPNodes) {
            System.out.println(n.getName() + ": " + n.getAdjacentNodesString());
        }

        // create graph and add all nodes to graph

        // create starting node with two nodes of first layer as successors (maybe with cost of first iteration as cost -> would make sense, (but to determine the best path itself should not matter))
        Node startingNode = new Node("start");

        startingNode.addDestination(LPNodes[0], 0);
        startingNode.addDestination(RPNodes[0], 0);
        Graph g = new Graph();
        g.addNode(startingNode);
        for (int i = 0; i < differentWagonValues.length; i++) {
            g.addNode(LPNodes[i]);
            g.addNode(RPNodes[i]);
        }

        Dijkstra.calculateShortestPathFromSource(g, startingNode);

        for (Node n : g.getNodes()) {
            System.out.println(n.getName() + ": " + n.getDistance());
        }
        System.out.println();
        // we only care about RP5 and LP5
        // (actually, perhaps only RP4 and RP4, since the cost for ascending the last layer may always be zero so the costs for LP5 and RP5 will be the same, but whatever)
        // yep, we will look at the second-to-last layer's nodes, since these will probably provide more information on the actual path up to that point (again, the cost of ascending from second-to-last to last is 0, so we might as well leave it out? -> worry about that later)
        String LPName = LPNodes[differentWagonValues.length - 2].getName();
        String RPName = RPNodes[differentWagonValues.length - 2].getName();

        Node LP4 = g.getNodeByName(LPName);
        Node RP4 = g.getNodeByName(RPName);

        if (LP4.getDistance() <= RP4.getDistance()) {    // for now takes LP4 if they are of equal length
            optimalPath = LP4.getShortestPath().stream().map(Node::getName).toArray(String[]::new);
        }
        else {
            optimalPath = RP4.getShortestPath().stream().map(Node::getName).toArray(String[]::new);
        }

        // compare and see which node can be reached faster - if it's the same, possibly look at both bc it's interesting, but then it really does not matter which one we pick
        System.out.println("Distance from start to (second-to-)last LP-Node(" + LPName + "): " + LP4.getDistance() + " (which makes for " + (LP4.getDistance() + costForFirstRemoval) + " in total extra cost)");
        System.out.println("Distance from start to (second-to-)last RP-Node(" + RPName + "): " + RP4.getDistance() + " (which makes for " + (RP4.getDistance() + costForFirstRemoval) + " in total extra cost)");
        System.out.println("The shortest path to the LP-Node: " + Arrays.toString(LP4.getShortestPath().stream().map(Node::getName).toArray()));
        System.out.println("The shortest path to the RP-Node: " + Arrays.toString(RP4.getShortestPath().stream().map(Node::getName).toArray()));
        System.out.println();
        System.out.println("""
                Please note that this extra cost is not equal to the number of movements which have to be made to remove all values / wagons.
                If one was allowed to reposition the pointer to their heart's content after the removal of every number (i.e. to either the 
                leftmost or rightmost position of an occurrence of the current number / value) this total extra cost would be zero, because 
                all movements would directly be for removing values. However, because here we are not allowed to "jump" in such a way, we will
                often end up at positions which are NOT an optimal starting position for the removal of the next number. This algorithm calculates
                this EXTRA COST, meaning the movements which we have to do in order to get into an optimal position (there are two, leftmost and 
                rightmost). This algorithm will find the optimal path to take (i.e. when to move to the leftmost next and when to move to the 
                rightmost next). Note that when using this for implementing the Rail-task (OOP task 2.4: Rangieren) the Shunter HAS to remove all
                occurrences of the current wagon value it encounters while moving to the leftmost or rightmost (i.e. optimal) occurrence / position 
                of that value.
                """);
        //        if (LP5.getDistance() <= RP5.getDistance()) {
//        }

    }

    public String[] getOptimalPath() {
        return optimalPath;
    }

    public static int getFirstOccurrence(int[] arr, int z) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == z) {
                return i;
            }
        }
        throw new RuntimeException("The array does not contain the number.");
    }

    public static int getLastOccurrence(int[] arr, int z) {
        for (int i = arr.length - 1; i > -1; i--) {
            if (arr[i] == z) {
                return i;
            }
        }
        throw new RuntimeException("The array does not contain the number.");
    }

    public static int[] getC_Li(int[] arr, int z) { // gets the cost array for layer i, corresponding to wagon-nr z
        int[] C_Lz = new int[arr.length + 1];
        int firstInd = getFirstOccurrence(arr, z);

        C_Lz[firstInd + 1] = 0;
        for (int i = firstInd - 1; i > -1; i--) {
            C_Lz[i] = C_Lz[i + 1] + 1;    // it is guaranteed that there will be no other occurrence of num before the first one
            if (arr[i] <= z) {
                C_Lz[i]--;  // if the number is smaller than z, it will already have been removed at this point and therefore not have to be moved
            }
        }

        for (int i = firstInd + 1; i < arr.length + 1; i++) {
            C_Lz[i] = C_Lz[i - 1] + 1;
            if (arr[i - 1] <= z) {   // if number at position is equal to or smaller than num, cost should not increase
                C_Lz[i]--;
            }
        }
        return C_Lz;
    }

    public static int[] getC_Ri(int[] arr, int z) { // gets the cost array for layer i, corresponding to wagon-nr z
        int[] C_Rz = new int[arr.length + 1];
        int lastInd = getLastOccurrence(arr, z);

        C_Rz[lastInd + 1] = 0;  // TODO: check whether this is necessary
        for (int i = lastInd + 1; i < arr.length + 1; i++) {
            C_Rz[i] = C_Rz[i - 1] + 1;    // it is guaranteed that there will be no other occurrence of num before the first one
            if (arr[i - 1] <= z) {
                C_Rz[i]--;  // if the number is smaller than z, it will already have been removed at this point and therefore not have to be moved
            }
        }

        for (int i = lastInd - 1; i > -1; i--) {
            C_Rz[i] = C_Rz[i + 1] + 1;
            if (arr[i] <= z) {   // if number at position is equal to or smaller than num, cost should not increase
                C_Rz[i]--;
            }
        }
        return C_Rz;
    }


    private int getLastPos(int i) {
        // equal to last index of first value
        int lC = getLastOccurrence(wagons, differentWagonValues[i]);
        System.out.println("After the removal of all wagons (FROM LEFT TO RIGHT) with the smallest wagon value (" + differentWagonValues[i]
                + "), pointer stands at " + lC);
        return lC;
    }

    private int getFirstPos(int i) {
        // equal to last index of first value
        int lC = getFirstOccurrence(wagons, differentWagonValues[i]);
        System.out.println("After the removal of all wagons (FROM RIGHT TO LEFT) with the smallest wagon value (" + differentWagonValues[i]
                + "), pointer stands at " + lC);
        return lC;
    }

}
