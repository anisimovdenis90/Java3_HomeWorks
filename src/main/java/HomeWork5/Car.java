package HomeWork5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Car implements Runnable {

    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier startBarrier;
    private CountDownLatch finishedBarrier;
    private AtomicBoolean isWinner;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier startBarrier, CountDownLatch finishedBarrier, AtomicBoolean isWinner) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.startBarrier = startBarrier;
        this.finishedBarrier = finishedBarrier;
        this.isWinner = isWinner;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            // Ожидаем готовности участников
            startBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        // Определяем победителя
        if (isWinner.compareAndSet(false, true)) {
            System.out.println(this.name + " WIN!");
        }
        finishedBarrier.countDown();
    }
}
