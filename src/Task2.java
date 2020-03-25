import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task2 {

    public static void main(String[] args) {
        String[] stringsArray = {"H", "E", "L", "L", "O", " ", "W", "O", "R", "L", "D"};
        System.out.println("Массив: " + Arrays.toString(stringsArray));
        List<String> list = arrayToList(stringsArray);
        System.out.println("ArrayList: " + list);

        Integer[] integerArray = {1, 2, 3, 4, 5, 6};
        System.out.println("Массив: " + Arrays.toString(integerArray));
        List<Integer> list2 = arrayToList(integerArray);
        System.out.println("ArrayList: " + list2);

        Integer[] ar = null;
        System.out.println("Массив: " + Arrays.toString(ar));
        List<Integer> list3 = arrayToList(ar);
        System.out.println("ArrayList: " + list3);
    }

    // Преобразует переданный массив в ArrayList
    public static <T> List<T> arrayToList(T[] array) {
        ArrayList<T> list = new ArrayList<>();
        if (array == null) {
            return null;
        } else {
            for (T element : array) {
                list.add(element);
            }
            return list;
        }
    }
}
