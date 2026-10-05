package mocks.day10.model.utils;

public class EventIdsIncrementUtils {
    private static Integer counter = 0;

    public synchronized static   Integer increment() {
        return ++counter;
    }
}
