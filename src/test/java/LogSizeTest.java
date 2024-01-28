package test.java;

import main.java.trainstation.TrainStation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogSizeTest {

    @Test
    void testLogSizeTrivial() {
        TrainStation ts = new TrainStation(1, 2, 3, 4, 5);
        assertEquals(ts.moveNew(false), 5);
    }

    @Test
    void testLogSizeSimpleDecision() {
        TrainStation ts = new TrainStation(2, 3, 1, 4, 2);
        assertEquals(10, ts.moveNew(false));
    }

    @Test
    void testLogSizeMediumDecision() {
        TrainStation ts = new TrainStation(1, 3, 3, 0, 3, 1, 2);
        assertEquals(15, ts.moveNew(false));
    }

    @Test
    void testLogSizeComplexDecision() {
        TrainStation ts = new TrainStation(8, 5, 2, 9, 1, 5, 5, 7, 3, 7, 3, 5, 8, 3, 10, 7, 7, 6, 1, 5, 2, 3, 10, 10, 10, 10, 2, 5, 5, 1, 7, 9, 4, 6, 6, 7, 2, 1, 9, 3, 10, 2, 1, 8, 7, 5, 4, 1, 1, 1, 3, 7, 4, 6, 6, 4, 7, 7, 3, 5, 1, 3, 7, 3, 2, 2, 3, 2, 8, 4, 7, 6, 5, 5, 3, 9, 1, 5, 5, 4, 3, 1, 1, 4, 10, 4, 3, 5, 10, 6, 8, 2, 10, 8, 2, 9, 1, 5, 10, 8);
        assertEquals(451, ts.moveNew(false));
    }

}
