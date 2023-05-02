import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        TrainStation ts = new TrainStation(12, 4, 19, 20, 3, 16, 4, 6, 9, 8);
        ts.moveWagons();

        TrainStation ts2 = new TrainStation(8, 5, 2, 9, 1, 5, 5, 7, 3, 7, 3, 5, 8, 3, 10, 7, 7, 6, 1, 5, 2, 3, 10, 10, 10, 10, 2, 5, 5, 1, 7, 9, 4, 6, 6, 7, 2, 1, 9, 3, 10, 2, 1, 8, 7, 5, 4, 1, 1, 1, 3, 7, 4, 6, 6, 4, 7, 7, 3, 5, 1, 3, 7, 3, 2, 2, 3, 2, 8, 4, 7, 6, 5, 5, 3, 9, 1, 5, 5, 4, 3, 1, 1, 4, 10, 4, 3, 5, 10, 6, 8, 2, 10, 8, 2, 9, 1, 5, 10, 8);
        ts2.moveWagons();

        TrainStation ts3 = new TrainStation(70, 29, 29, 78, 53, 87, 47, 94, 29, 53, 99, 51, 13, 99, 86, 95, 56, 87, 1, 75, 32, 70, 14, 22, 22, 10, 50, 85, 72, 51, 1, 39, 91, 90, 51, 16, 23, 9, 98, 22, 34, 67, 12, 91, 86, 36, 23, 33, 87, 22, 35, 36, 3, 45, 58, 67, 85, 82, 19, 36, 46, 59, 95, 59, 46, 10, 86, 69, 86, 89, 15, 41, 74, 43, 25, 91, 43, 28, 7, 54, 10, 24, 71, 48, 28, 72, 2, 95, 64, 68, 63, 79, 68, 72, 43, 58, 33, 84, 50, 71);
        ts3.moveWagons();

        TrainStation ts4 = new TrainStation(58, 80, 28, 76, 80, 85, 20, 42, 42, 44, 21, 66, 55, 80, 46, 45, 74, 39, 45, 63, 5, 53, 59, 2, 87, 56, 62, 62, 19, 41, 70, 48, 67, 19, 54, 46, 26, 66, 99, 12, 41, 75, 50, 65, 24, 75, 68, 80, 21, 97, 38, 72, 23, 99, 39, 10, 67, 90, 42, 37, 99, 38, 64, 68, 35, 60, 74, 67, 19, 29, 50, 89, 10, 29, 47, 4, 2, 51, 79, 26, 72, 12, 24, 2, 23, 93, 43, 30, 44, 96, 5, 93, 27, 83, 96, 35, 50, 45, 14, 48);
        ts4.moveWagons();
    }

    private static Integer[] rdmIntegerArray(int nrOfValues, int maxValue) {
        Integer[] wagons = new Integer[nrOfValues];
        for (int j = 0; j < nrOfValues; j++) {
            int wagon = ThreadLocalRandom.current().nextInt(1,  maxValue + 1);
            wagons[j] = wagon;
        }
        return wagons;
    }
}