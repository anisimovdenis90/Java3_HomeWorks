package HomeWork5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    // Добавил Semaphore для ограничения
    private Semaphore semaphore;

    public Tunnel(int permitsOnCars) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        // Создаем семафор с полученным ограничением и честной очередью
        this.semaphore = new Semaphore(permitsOnCars, true);
    }

    @Override
    public void go(Car c) {
//        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                // Захват семафора
                semaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                // Освобождение семафора
                semaphore.release();
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
