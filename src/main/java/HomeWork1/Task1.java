package HomeWork1;

import java.util.Arrays;

public class Task1 {

    public static void main(String[] args) {
        Integer[] integerArray = {1, 2, 3, 4, 5, 6};
        System.out.println("Массив до обработки: " + Arrays.toString(integerArray));
        shiftElementOfArray(integerArray, 0, 3);
        System.out.println("Массив после обработки: " + Arrays.toString(integerArray));

        String[] stringsArray = {"H", "E", "L", "L", "O", " ", "W", "O", "R", "L", "D"};
        System.out.println("Массив до обработки: " + Arrays.toString(stringsArray));
        shiftElementOfArray(stringsArray, 0, stringsArray.length - 1);
        System.out.println("Массив после обработки: " + Arrays.toString(stringsArray));
    }

    // Меняет местами два элемента переданного массива
    public static <T> void shiftElementOfArray(T[] array, int firstElement, int secondElement) {
        if (firstElement >= array.length || secondElement >= array.length || firstElement < 0 || secondElement < 0) {
            throw new ArrayIndexOutOfBoundsException("Неверные индексы элементов");
        } else if (firstElement == secondElement) {
            return;
        }
        T temp = array[firstElement];
        array[firstElement] = array[secondElement];
        array[secondElement] = temp;
    }
}
