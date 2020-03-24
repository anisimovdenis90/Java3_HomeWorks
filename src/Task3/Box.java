package Task3;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    private ArrayList<T> quantityOfFruits;

    // Конструкторы
    public Box() {
        this.quantityOfFruits = new ArrayList<>();
    }

    public Box(ArrayList<T> quantityOfFruits) {
        this.quantityOfFruits = quantityOfFruits;
    }

    // Возвращает АрайЛист фруктов в корзине
    public ArrayList<T> getFruits() {
        return quantityOfFruits;
    }

    // Очищает корзину от фруктов
    public void clear() {
        quantityOfFruits.clear();
    }

    // Помещает фрукт в корзину
    public void putFruitInBox(T fruit) {
        quantityOfFruits.add(fruit);
    }

    // Помещает АрайЛист фруктов в корзину
    public void putFruitsInBox(ArrayList<T> fruits) {
        quantityOfFruits.addAll(fruits);
    }

    // Пересыпание коробку в коробку
    public void putBoxInBox(Box<T> box2) {
        box2.putFruitsInBox(this.getFruits());
//        box2.getFruits().addAll(this.quantityOfFruits);
        this.clear();
    }

    // Возвращает вес коробки с фруктами
    public float getWeight() {
        if (quantityOfFruits.size() == 0) {
            return 0;
        } else  {
            return (quantityOfFruits.get(0).getWeight() * quantityOfFruits.size());
        }
    }

    // Сравнивает вес коробок
    public boolean compare(Box<?> box2) {
        return (this.getWeight() == box2.getWeight());
    }

    public String boxInfo() {
        return "Количество фруктов в корзине: " + this.quantityOfFruits.size() + " Вес корзины: " +  this.getWeight();
    }
}
