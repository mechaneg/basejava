import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class StreamsAlgorithmsTest {

    @Test
    public void minValue() {
        assertEquals(123, StreamsAlgorithms.minValue(new int[]{1, 2, 3}));
        assertEquals(123, StreamsAlgorithms.minValue(new int[]{3, 2, 1}));
        assertEquals(123, StreamsAlgorithms.minValue(new int[]{3, 1, 3, 2, 1, 2}));

        assertEquals(1, StreamsAlgorithms.minValue(new int[]{1, 1, 1}));
        assertEquals(123456789, StreamsAlgorithms.minValue(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
    }

    @Test
    public void oddOrEven() {
        assertEquals(Arrays.asList(2), StreamsAlgorithms.oddOrEven(Arrays.asList(1, 2)));
        assertEquals(Arrays.asList(1, 1), StreamsAlgorithms.oddOrEven(Arrays.asList(1, 2, 1)));
        assertEquals(Arrays.asList(1, 3), StreamsAlgorithms.oddOrEven(Arrays.asList(1, 2, 3)));

        assertEquals(Arrays.asList(1, 1), StreamsAlgorithms.oddOrEven(Arrays.asList(1, 1)));
        assertEquals(Arrays.asList(), StreamsAlgorithms.oddOrEven(Arrays.asList(1, 1, 1)));

    }
}