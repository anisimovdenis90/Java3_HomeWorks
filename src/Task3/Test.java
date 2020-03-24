package Task3;

public class Test {
    public static void main(String[] args) {

        Box<Apple> appleBox = new Box<>();
        Box<Apple> appleBox2 = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        for (int i = 0; i < 20; i++) {
            appleBox.putFruitInBox(new Apple());
            appleBox2.putFruitInBox(new Apple());
            orangeBox.putFruitInBox(new Orange());
        }

        System.out.println("Корзина с яблоками 1: " + appleBox.boxInfo());
        System.out.println("Корзина с яблоками 2: " + appleBox2.boxInfo());
        System.out.println("Корзина с апельсинами 1: " + orangeBox.boxInfo());

        System.out.println("Сравниваем корзину с яблоками 1 с корзиной с яблоками 2: " + appleBox.compare(appleBox2));
        System.out.println("Сравниваем корзину с яблоками 1 с корзиной с апельсинами: " + appleBox.compare(orangeBox));

        System.out.println("Пересыпаем корзину с яблоками 1 в корзину с яблоками 2");
        appleBox.putBoxInBox(appleBox2);

        System.out.println("Корзина с яблоками 1: " + appleBox.boxInfo());
        System.out.println("Корзина с яблоками 2: " + appleBox2.boxInfo());
    }
}
