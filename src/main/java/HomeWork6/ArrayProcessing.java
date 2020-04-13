package HomeWork6;

import java.util.*;

public class ArrayProcessing {
    private static final int NUMBER_ONE = 1;
    private static final int NUMBER_FOUR = 4;

    // Задание №1
    // Метод возвращает массив чисел после последней 4-ки переданного массива
    // RuntimeException, если нет 4
    public static int[] numbersAfterLastFour(int[] numbers) {
        int indexOfLastFour = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == NUMBER_FOUR) {
                indexOfLastFour = i;
            }
        }
        if (indexOfLastFour == -1) {
                throw new RuntimeException(String.format("Массив не содержит число %s!", NUMBER_FOUR));
        }
        return Arrays.copyOfRange(numbers, ++indexOfLastFour, numbers.length);
    }

    // Задание №2
    // Возвращает true, если переданный массив чисел состоит только из 1 и 4
    public static boolean isConsistOfNumbers(int[] numbers) {
        final Set<Integer> uniqValues = new HashSet<>();
        for (int number : numbers) {
            if (!(number == NUMBER_ONE || number == NUMBER_FOUR)) {
                return false;
            }
            uniqValues.add(number);
        }
        return uniqValues.size() == 2;
    }
}
