package ru.geekbrains.java2.server;

public class ServerApp {

    private static final int DEFAULT_PORT = 8189;

    public static void main(String[] args) {
        int port = getServerPort(args);
        new NetworkServer(port, true).start();
    }

    /**
     * Метод получения номера порта из аргумента
     * @param args - аргумент
     * @return int - номер порта
     */
    private static int getServerPort(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат порта, использован порт по умолчанию");
            }
        }
        return port;
    }
}
