package Task3;

public class Apple extends Fruit {

    public Apple(float weight) {
        super(weight);
    }

    public Apple() {
        super(1.0f);
    }
    public float getWeight() {
        return super.getWeight();
    }

    @Override
    public String getFruitType() {
        return this.getClass().getSimpleName();
    }
}
