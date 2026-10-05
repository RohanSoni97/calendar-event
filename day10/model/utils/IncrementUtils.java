package mocks.day10.model.utils;

public class IncrementUtils {

    private static Integer counter = 0;

    public synchronized static   Integer increment() {
        return ++counter;
    }


}
