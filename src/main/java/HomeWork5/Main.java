package HomeWork5;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static final int CARS_COUNT = 4;

    // Использую ExecutorService для потоков
    public static final ExecutorService raceExecutor = Executors.newFixedThreadPool(CARS_COUNT);

    // Для одновременного старта использую CyclicBarrier
    // Увеличиваю количество разрешений на один для ожидания в методе main
    public static final CyclicBarrier startBarrier = new CyclicBarrier(CARS_COUNT + 1);

    // Для ожидания завершения гонки использую CountDownLatch
    public static final CountDownLatch finishBarrier = new CountDownLatch(CARS_COUNT);

    // Для определения победителя использую AtomicBoolean
    public static final AtomicBoolean isWinner = new AtomicBoolean(false);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        // В тоннеле используется семафор передаю кол-во разрешений
        Race race = new Race(new Road(60), new Tunnel(CARS_COUNT / 2), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            raceExecutor.execute(cars[i] = new Car(race, 20 + (int) (Math.random() * 10), startBarrier, finishBarrier, isWinner));
        }
        // Ожидаем начала всех участников
        startBarrier.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        // Ожидаем финиширования всех участников
        finishBarrier.await();

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

        raceExecutor.shutdown();
    }
}
