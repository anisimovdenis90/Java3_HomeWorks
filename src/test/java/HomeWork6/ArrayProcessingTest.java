package HomeWork6;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class ArrayProcessingTest {

    private final int[] testArray1;
    private final int[] resultArray1;
    private final int[] testArrayException1;
    private final int[] tesTrueArray2;
    private final int[] testFalseArray2;

    public ArrayProcessingTest(int[] testArray1, int[] resultArray1, int[] testArrayException1, int[] tesTrueArray2, int[] testFalseArray2) {
        this.testArray1 = testArray1;
        this.resultArray1 = resultArray1;
        this.testArrayException1 = testArrayException1;
        this.tesTrueArray2 = tesTrueArray2;
        this.testFalseArray2 = testFalseArray2;
    }

    @Parameterized.Parameters
    public static Collection<int[][]> data() {
        return Arrays.asList(new int[][][]{
                {new int[] {1, 2, 4, 4, 2, 3, 4, 1, 7},     // Тестовый массив для задания №1
                        new int[] {1, 7},                   // Правильный результат для задания №1
                        new int[] {1, 2, 3, 1},             // Тестовый массив на RuntimeException для задания №1
                        new int[] {1, 1, 1, 4, 4, 1, 4, 4}, // Тестовый массив на true для задания №2
                        new int[] {1, 1, 1, 1, 1, 1}},      // Тестовый массив на false для задания №2
                {new int[] {2, 4, 5, 6, 1, 7},
                        new int[] {5, 6, 1, 7},
                        new int[] {2, 1, 5, 6, 7, 1},
                        new int[] {4, 1, 4, 4},
                        new int[] {1, 4, 4, 1, 1, 4, 3}},
                {new int[] {1, 4, 5, 4, 9, 3, 2, 1},
                        new int[] {9, 3, 2, 1},
                        new int[] {1},
                        new int[] {1, 1, 1, 4},
                        new int[] {4, 4, 4, 4}},
                {new int[] {9, 6, 5, 3, 4, 3},
                        new int[] {3},
                        new int[] {0, 3, 2},
                        new int[] {1, 4, 1, 4, 4, 4},
                        new int[] {1, 4, 1, 4, 1, 1, 4, 3}}
        });
    }

    @Test
    public void testNumbersAfterLastFour () {
        Assert.assertArrayEquals(resultArray1, ArrayProcessing.numbersAfterLastFour(testArray1));
    }

    @Test(expected = RuntimeException.class)
    public void testNumbersAfterLastFourExeption () {
        ArrayProcessing.numbersAfterLastFour(testArrayException1);
    }

    @Test
    public void testIsConsistOfNumbersTrue () {
        Assert.assertTrue(ArrayProcessing.isConsistOfNumbers(tesTrueArray2));
    }

    @Test
    public void testIsConsistOfNumbersFalse () {
        Assert.assertFalse(ArrayProcessing.isConsistOfNumbers(testFalseArray2));
    }

}
