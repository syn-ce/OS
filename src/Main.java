import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
//        TrainStation ts = new TrainStation(12, 4, 19, 20, 3, 16, 4, 6, 9, 8);

//        TrainStation ts2 = new TrainStation(8, 5, 2, 9, 1, 5, 5, 7, 3, 7, 3, 5, 8, 3, 10, 7, 7, 6, 1, 5, 2, 3, 10, 10, 10, 10, 2, 5, 5, 1, 7, 9, 4, 6, 6, 7, 2, 1, 9, 3, 10, 2, 1, 8, 7, 5, 4, 1, 1, 1, 3, 7, 4, 6, 6, 4, 7, 7, 3, 5, 1, 3, 7, 3, 2, 2, 3, 2, 8, 4, 7, 6, 5, 5, 3, 9, 1, 5, 5, 4, 3, 1, 1, 4, 10, 4, 3, 5, 10, 6, 8, 2, 10, 8, 2, 9, 1, 5, 10, 8);
//        ts2.moveNew();
//        TrainStation ts21 = new TrainStation(8, 5, 2, 9, 1, 5, 5, 7, 3, 7, 3, 5, 8, 3, 10, 7, 7, 6, 1, 5, 2, 3, 10, 10, 10, 10, 2, 5, 5, 1, 7, 9, 4, 6, 6, 7, 2, 1, 9, 3, 10, 2, 1, 8, 7, 5, 4, 1, 1, 1, 3, 7, 4, 6, 6, 4, 7, 7, 3, 5, 1, 3, 7, 3, 2, 2, 3, 2, 8, 4, 7, 6, 5, 5, 3, 9, 1, 5, 5, 4, 3, 1, 1, 4, 10, 4, 3, 5, 10, 6, 8, 2, 10, 8, 2, 9, 1, 5, 10, 8);
//        ts21.moveOld();
        // 451 vs 451

//        TrainStation ts3 = new TrainStation(70, 29, 29, 78, 53, 87, 47, 94, 29, 53, 99, 51, 13, 99, 86, 95, 56, 87, 1, 75, 32, 70, 14, 22, 22, 10, 50, 85, 72, 51, 1, 39, 91, 90, 51, 16, 23, 9, 98, 22, 34, 67, 12, 91, 86, 36, 23, 33, 87, 22, 35, 36, 3, 45, 58, 67, 85, 82, 19, 36, 46, 59, 95, 59, 46, 10, 86, 69, 86, 89, 15, 41, 74, 43, 25, 91, 43, 28, 7, 54, 10, 24, 71, 48, 28, 72, 2, 95, 64, 68, 63, 79, 68, 72, 43, 58, 33, 84, 50, 71);
//        ts3.moveNew();
//        TrainStation ts31 = new TrainStation(70, 29, 29, 78, 53, 87, 47, 94, 29, 53, 99, 51, 13, 99, 86, 95, 56, 87, 1, 75, 32, 70, 14, 22, 22, 10, 50, 85, 72, 51, 1, 39, 91, 90, 51, 16, 23, 9, 98, 22, 34, 67, 12, 91, 86, 36, 23, 33, 87, 22, 35, 36, 3, 45, 58, 67, 85, 82, 19, 36, 46, 59, 95, 59, 46, 10, 86, 69, 86, 89, 15, 41, 74, 43, 25, 91, 43, 28, 7, 54, 10, 24, 71, 48, 28, 72, 2, 95, 64, 68, 63, 79, 68, 72, 43, 58, 33, 84, 50, 71);
//        ts31.moveOld();
        // 1304 vs 1300

//        TrainStation ts4 = new TrainStation(58, 80, 28, 76, 80, 85, 20, 42, 42, 44, 21, 66, 55, 80, 46, 45, 74, 39, 45, 63, 5, 53, 59, 2, 87, 56, 62, 62, 19, 41, 70, 48, 67, 19, 54, 46, 26, 66, 99, 12, 41, 75, 50, 65, 24, 75, 68, 80, 21, 97, 38, 72, 23, 99, 39, 10, 67, 90, 42, 37, 99, 38, 64, 68, 35, 60, 74, 67, 19, 29, 50, 89, 10, 29, 47, 4, 2, 51, 79, 26, 72, 12, 24, 2, 23, 93, 43, 30, 44, 96, 5, 93, 27, 83, 96, 35, 50, 45, 14, 48);
//        ts4.moveNew();
//        TrainStation ts41 = new TrainStation(58, 80, 28, 76, 80, 85, 20, 42, 42, 44, 21, 66, 55, 80, 46, 45, 74, 39, 45, 63, 5, 53, 59, 2, 87, 56, 62, 62, 19, 41, 70, 48, 67, 19, 54, 46, 26, 66, 99, 12, 41, 75, 50, 65, 24, 75, 68, 80, 21, 97, 38, 72, 23, 99, 39, 10, 67, 90, 42, 37, 99, 38, 64, 68, 35, 60, 74, 67, 19, 29, 50, 89, 10, 29, 47, 4, 2, 51, 79, 26, 72, 12, 24, 2, 23, 93, 43, 30, 44, 96, 5, 93, 27, 83, 96, 35, 50, 45, 14, 48);
//        ts41.moveOld();
        //  1404 vs 1370

//        List<int[]> won = new ArrayList<>();

        multipleRdmTesting(100, 100);

        TrainStation ts7 = new TrainStation(2, 3, 1, 4, 2);
        TrainStation ts71 = new TrainStation(2, 3, 1, 4, 2);
        ts71.moveOld();
        ts7.moveNew();

//        TrainStation ts6 = new TrainStation(10, 2, 3, 8, 8, 7, 6, 6, 2, 3); // -> this example was fixed by fixing the starting node to the left node of the first layer (LP1, so to speak); indeed, this had not been the case before, though it should have (since we always have to start from the left on the first layer)
//        TrainStation ts61 = new TrainStation(10, 2, 3, 8, 8, 7, 6, 6, 2, 3);
//        ts6.moveNew();
//        ts61.moveOld();
//        TrainStation ts5 = new TrainStation(2, 5, 4, 4, 9, 10, 8, 9, 3, 5);   -> this example was fixed by adjusting two indices: if the Shunter needed to consult the Graph while removing the second-to-last number, it would not have looked at the graph because of this indexing error, though it could (and should) have done so
//        TrainStation ts51 = new TrainStation(2, 5, 4, 4, 9, 10, 8, 9, 3, 5);
//        ts5.moveNew();
//        ts51.moveOld();
    }

    private static void multipleRdmTesting(int nrOfIterations, int nrOfRuns) {
        int totalWon = 0;
        int totalDraw = 0;
        long totalNewSum = 0;
        long totalOldSum = 0;
        int totalTests = nrOfIterations * nrOfRuns;
        for (int i = 0; i < nrOfIterations; i++) {
            int[] res = rdmTestingNewVsOldShunt(nrOfRuns, 30, 30);
            totalOldSum += res[0];
            totalNewSum += res[1];
            totalWon += res[2];
            totalDraw += res[3];
        }

        System.out.println();
        System.out.println("Total length of all old logs: " + totalOldSum);
        System.out.println("Total length of all new logs: " + totalNewSum);
        System.out.println("Ratio: " + ((double) totalNewSum / totalOldSum));
        System.out.println("Won: " + totalWon + "/" + totalTests);
        System.out.println("Draw: " + totalDraw + "/" + totalTests);
        System.out.println("Lost: " + (totalTests - totalWon - totalDraw) + "/" + totalTests);
    }

    private static int[] rdmTestingNewVsOldShunt(int nrOfRuns, int nrOfWagons, int maxWagonValue) {
        int totalOldSum = 0;
        int totalNewSum = 0;
        int newWasSmaller = 0;
        int equalLength = 0;
        for (int i = 0; i < nrOfRuns; i++) {

            Integer[] wagons = new Integer[nrOfWagons];
            for (int j = 0; j < nrOfWagons; j++) {
                int wagon = ThreadLocalRandom.current().nextInt(1, maxWagonValue + 1);
                wagons[j] = wagon;
            }
            TrainStation tsOld = new TrainStation(wagons);
            TrainStation tsOld2 = new TrainStation(wagons);
            int oldLogLength = Math.min(tsOld.moveOld(), tsOld2.moveOld2());
            TrainStation tsNew = new TrainStation(wagons);
            int newLogLength = tsNew.moveNew();

            if (newLogLength < oldLogLength) {
                newWasSmaller++;
//                System.out.println("New beat old here: " + Arrays.toString(wagons));
            } else if (newLogLength == oldLogLength) {
                equalLength++;
            } else {
                System.out.println(Arrays.deepToString(wagons));
            }

            totalOldSum += oldLogLength;
            totalNewSum += newLogLength;
        }
        System.out.println("Total length of all old logs: " + totalOldSum);
        System.out.println("Total length of all new logs: " + totalNewSum);
        System.out.println("Ratio: " + ((double) totalNewSum / totalOldSum));

        System.out.println("New beat old in " + newWasSmaller + "/" + nrOfRuns + " cases.");
        System.out.println("Draw in " + equalLength + "/" + nrOfRuns);
        System.out.println("Total: " + (newWasSmaller + equalLength) + "/" + nrOfRuns);
        System.out.println("Lost: " + (nrOfRuns - newWasSmaller - equalLength));
        return new int[]{totalOldSum, totalNewSum, newWasSmaller, equalLength};
    }
}