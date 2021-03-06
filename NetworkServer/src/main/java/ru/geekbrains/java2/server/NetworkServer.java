package ru.geekbrains.java2.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.server.auth.AuthService;
import ru.geekbrains.java2.server.auth.DataBaseAuthService;
import ru.geekbrains.java2.server.cens.Censor;
import ru.geekbrains.java2.server.cens.CensorService;
import ru.geekbrains.java2.server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkServer {

    // Создаю логгеры
    private static final Logger infoLogger = LogManager.getLogger(NetworkServer.class.getName());
    private static final Logger fatalLogger = LogManager.getRootLogger();

    private int port;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final ExecutorService executor;
    private final AuthService authService;

    private Censor censor;

    public NetworkServer(int port, boolean enableCensor) {
        this.port = port;
        this.executor = Executors.newCachedThreadPool();
        if (enableCensor) {
            this.authService = this.censor = new CensorService();
        } else {
            this.authService = new DataBaseAuthService();
        }
    }

    public String censure(String message) {
        if (censor != null) {
            return censor.messageCensor(message);
        } else {
            return message;
        }
    }

    public Censor getCensor() {
        return censor;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public static Logger getInfoLogger() {
        return infoLogger;
    }

    public static Logger getFatalLogger() {
        return fatalLogger;
    }

    /**
     * Метод запуска сетевого сервера
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
//            System.out.println("Сервер был успешно запущен на порту " + port);
            infoLogger.info("Сервер был успешно запущен на порту " + port);
            //  Запускаем сервис авторизации
            authService.start();
//            System.out.println("Сервис авторизации успешно запущен");
            infoLogger.info("Сервис авторизации успешно запущен");
            //  Создаем список подключений
            while (true) {
//                System.out.println("Ожидание подключения клиента...");
                infoLogger.info("Ожидание подключения клиента...");
                Socket clientSocket = serverSocket.accept();
//                System.out.println("Клиент подключился");
                infoLogger.info("Клиент подключился");
                //  Создаем обработчик клиента
                createClientHandler(clientSocket);
            }
        } catch (IOException e) {
//            System.out.println("Ошибка при работе сервера");
//            e.printStackTrace();
            fatalLogger.error("Ошибка при работе сервера", e);
        } finally {
            // Отдельно останавливаем сервис авторизации
            authService.stop();
            executor.shutdown();
        }
    }

    private void createClientHandler(Socket clientSocket) {
        executor.execute(new ClientHandler(this, clientSocket));
    }

    /**
     * Возвращает сервис авторизации из конструктора сетевого сервера
     *
     * @return - AuthService - сервис авторизации
     */
    public AuthService getAuthService() {
        return authService;
    }

    /**
     * Отправка сообщения всем подключенным клиентам
     *
     * @param message      - String сообщения для отправки
     * @throws IOException - пробрасываем исключение
     */
    public synchronized void broadcastMessage(Command message, ClientHandler owner) throws IOException {
        for (ClientHandler client : clients) {
            if (client != owner) {
                client.sendMessage(message);
            }
        }
    }

    /**
     * Метод отправки сообщения конкретному пользователю
     *
     * @param receiver     - никнейм контакта, кому отправляется сообщение
     * @param command      - объект с сообщением и ником отправителя
     * @throws IOException - пробрасываем исключение
     */
    public synchronized void sendPrivateMessage(String receiver, Command command) throws IOException {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(receiver)) {
                client.sendMessage(command);
                break;
            }
        }
    }

    /**
     * Добавление подключения в список
     *
     * @param clientHandler - на вход подключение клиента
     */
    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        clients.add(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    /**
     * Удаление подключения из списка
     *
     * @param clientHandler - подключение клиента
     */
    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        clients.remove(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    /**
     * Метод возвращает коллекцию подключенных к серверу пользователей
     *
     * @return - List пользователей
     */
    private List<String> getAllUsernames() {
//        return clients.stream()
//                .map(client -> client.getNickname())
//                .collect(Collectors.toList());
        List<String> usernames = new LinkedList<>();
        for (ClientHandler client : clients) {
            usernames.add(client.getNickname());
        }
        return usernames;
    }

    /**
     * Проверка на использование никнейма
     *
     * @param username - проверяемый никнейма
     * @return - boolean
     */
    public boolean isNicknameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
