package HomeWork1.Task3;

public class Test {
    public static void main(String[] args) {

        Box<Apple> appleBox = new Box<>(new Apple());
        Box<Apple> appleBox2 = new Box<>(new Apple());
        Box<Orange> orangeBox = new Box<>(new Orange());

        for (int i = 0; i < 20; i++) {
            appleBox.putFruitInBox(new Apple());
            appleBox2.putFruitInBox(new Apple());
            orangeBox.putFruitInBox(new Orange());
        }

        System.out.println(appleBox.boxInfo());
        System.out.println(appleBox2.boxInfo());
        System.out.println(orangeBox.boxInfo());

        System.out.println("Сравниваем " + appleBox.boxShortInfo() + " и " + appleBox2.boxShortInfo() + " - " + appleBox.compare(appleBox2));
        System.out.println("Сравниваем " + appleBox.boxShortInfo() + " и " + orangeBox.boxShortInfo() + " - " + appleBox.compare(orangeBox));

        System.out.println("Пересыпаем " + appleBox.boxShortInfo() + " в " + appleBox2.boxShortInfo());
        appleBox.putBoxInBox(appleBox2);

        System.out.println(appleBox.boxInfo());
        System.out.println(appleBox2.boxInfo());
    }
}
