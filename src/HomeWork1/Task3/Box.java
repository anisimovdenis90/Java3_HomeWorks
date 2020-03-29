package HomeWork1.Task3;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private static int count = 0;
    private int number;
    private ArrayList<T> quantityOfFruits;
    private T type;

    // Конструкторы
    public Box(T fruit) {
        count++;
        this.quantityOfFruits = new ArrayList<>();
        this.quantityOfFruits.add(fruit);
        this.type = fruit;
        this.number = count;
    }

    // Возвращает АрайЛист фруктов в корзине
    public ArrayList<T> getFruits() {
        return this.quantityOfFruits;
    }

    // Очищает корзину от фруктов
    public void clear() {
        this.quantityOfFruits.clear();
    }

    // Помещает фрукт в корзину
    public void putFruitInBox(T fruit) {
        this.quantityOfFruits.add(fruit);
    }

    // Помещает АрайЛист фруктов в корзину
    public void putFruitsInBox(ArrayList<T> fruits) {
        this.quantityOfFruits.addAll(fruits);
    }

    // Пересыпает содержимое коробки в коробку
    public void putBoxInBox(Box<T> box2) {
        box2.putFruitsInBox(this.getFruits());
        this.clear();
    }

    // Возвращает вес коробки с фруктами
    public float getWeight() {
        float boxWeight = 0.0f;
        if (this.quantityOfFruits.size() == 0) {
            return 0.0f;
        } else  {
            for (T fruit : this.quantityOfFruits) {
                boxWeight += fruit.getWeight();
            }
            return (boxWeight);
        }
    }

    // Сравнивает вес коробок
    public boolean compare(Box<?> box2) {
        return (this.getWeight() == box2.getWeight());
    }

    public String boxInfo() {
        return "Коробка " + number + " с " + this.type.getFruitType() + ", кол-во: " + this.quantityOfFruits.size() + ", вес коробки: " +  this.getWeight();
    }

    public String boxShortInfo() {
        return "Коробка " + number + " с " + this.type.getFruitType();
    }
}
