import java.util.ArrayList;
import java.util.Arrays;

public class Task2 {

    public static void main(String[] args) {
        String[] stringsArray = {"H", "E", "L", "L", "O", " ", "W", "O", "R", "L", "D"};
        System.out.println("Массив: " + Arrays.toString(stringsArray));
        ArrayList<String> list = arrayToList(stringsArray);
        System.out.println("ArrayList: " + list);

        Integer[] integerArray = {1, 2, 3, 4, 5, 6};
        System.out.println("Массив: " + Arrays.toString(integerArray));
        ArrayList<Integer> list2 = arrayToList(integerArray);
        System.out.println("ArrayList: " + list2);

        Integer[] ar = null;
        System.out.println("Массив: " + Arrays.toString(ar));
        ArrayList<Integer> list3 = arrayToList(ar);
        System.out.println("ArrayList: " + list3);
    }

    public static <T> ArrayList<T> arrayToList(T[] array) {
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
