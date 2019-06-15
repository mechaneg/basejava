import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamsAlgorithms {
    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (res, val) -> res * 10 + val);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        long oddNumber = integers.stream().filter(StreamsAlgorithms::isOdd).count();
        return integers.stream().filter(isOdd(oddNumber) ? StreamsAlgorithms::isEven : StreamsAlgorithms::isOdd).collect(Collectors.toList());
    }

    private static boolean isOdd(Integer val) {
        return val % 2 == 1;
    }

    private static boolean isEven(Integer val) {
        return !isOdd(val);
    }

    private static boolean isOdd(Long val) {
        return val % 2 == 1;
    }
}
