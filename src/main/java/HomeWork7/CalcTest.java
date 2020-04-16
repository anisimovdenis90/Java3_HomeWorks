package HomeWork7;

import HomeWork7.testsuite.RunTestClasses;
import HomeWork7.testsuite.annotations.AfterSuite;
import HomeWork7.testsuite.annotations.BeforeSuite;
import HomeWork7.testsuite.annotations.Test;

public class CalcTest {

    Calc calc;

    public static void main(String[] args) {
        RunTestClasses.start(CalcTest.class);
    }

    @BeforeSuite
    public void createCalc() {
        System.out.println("Подготовка к тесту");
        calc = new Calc();
        System.out.println("Объект создан");
    }

    @Test(priority = Test.MethodPriority.ONE)
    public void testCalcAdd() {
        System.out.print("Тест с приоритетом 1. Сумма: ");
        System.out.println(calc.add(2, 3) == 5);
    }

    @Test(priority = Test.MethodPriority.EIGHT)
    public void testCalcSub() {
        System.out.print("Тест с приоритетом 8. Вычитание: ");
        System.out.println(calc.sub(3, 2) == 1);
    }

    @Test(priority = Test.MethodPriority.THREE)
    public void testCalcDiv() {
        System.out.print("Тест с приоритетом 3. Деление: ");
        System.out.println(calc.div(4, 2) == 2);
    }

    @Test
    public void testCalcMul() {
        System.out.print("Тест с приоритетом 5. Умножение: ");
        System.out.println(calc.mul(3, 2) == 6);
    }

    @AfterSuite
    public void removeCalc() {
        calc = null;
        System.out.println("Тест окончен!");
    }
}
