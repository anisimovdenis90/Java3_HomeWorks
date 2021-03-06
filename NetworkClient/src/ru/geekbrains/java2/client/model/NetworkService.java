package ru.geekbrains.java2.client.model;

import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.client.commands.*;
import ru.geekbrains.java2.client.controller.AuthEvent;
import ru.geekbrains.java2.client.controller.ClientController;
import ru.geekbrains.java2.client.controller.MessageHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class NetworkService {

    private final String host;
    private final int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private ClientController controller;

    private MessageHandler messageHandler;
    private AuthEvent successfulAuthEvent;
    private String nickname;

    public NetworkService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Создание подключения клиента к серверу
     *
     * @param controller - ссылка на контроллер клиента
     * @throws IOException - пробрасываем ошибку подключения
     */
    public void connect(ClientController controller) throws IOException {
        this.controller = controller;
        socket = new Socket(host, port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        runReadThread();
    }

    /**
     * Создание потока для чтения сообщений от сервера
     */
    private void runReadThread() {
        new Thread(() -> {
            while (true) {
                try {
                    Command command = (Command) in.readObject();
                    switch (command.getType()) {
                        case AUTH: {
                            // При успешной авторизации принимаем никнейм текущего контакта от сервера
                            AuthCommand commandData = (AuthCommand) command.getData();
                            nickname = commandData.getUsername();
                            String userID = commandData.getUserID();
                            // Передаем никнейм контроллеру
                            successfulAuthEvent.authIsSuccessful(nickname, userID);
                            break;
                        }
                        case MESSAGE: {
                            MessageCommand commandData = (MessageCommand) command.getData();
                            if (messageHandler != null) {
                                String message = commandData.getMessage();
                                String username = commandData.getUsername();
                                if (username != null) {
                                    message = username + ": " + message;
                                }
                                messageHandler.handle(message);
                            }
                            break;
                        }
                        case PRIVATE_MESSAGE: {
                            PrivateMessageCommand commandData = (PrivateMessageCommand) command.getData();
                            if (messageHandler != null) {
                                String message = commandData.getMessage();
                                String username = commandData.getSender();
                                if (username != null) {
                                    message = username + " лично вам: " + message;
                                }
                                messageHandler.handle(message);
                            }
                            break;
                        }
                        case TIMEOUT_MESSAGE: {
                            BroadcastMessageCommand commandData = (BroadcastMessageCommand) command.getData();
                            controller.updateTimeoutLabel(commandData.getMessage());
                            break;
                        }
                        case TIMEOUT_AUTH_ERROR: {
                            ErrorCommand commandData = (ErrorCommand) command.getData();
                            controller.showErrorAndClose(commandData.getErrorMessage());
                            break;
                        }
                        case ERROR:
                        case AUTH_ERROR: {
                            ErrorCommand commandData = (ErrorCommand) command.getData();
                            controller.showErrorMessage(commandData.getErrorMessage());
                            break;
                        }
                        case UPDATE_USERS_LIST: {
                            UpdateUsersListCommand commandData = (UpdateUsersListCommand) command.getData();
                            List<String> users = commandData.getUsers();
                            controller.updateUsersList(users);
                            break;
                        }
                        case CHANGE_NICKNAME_MESSAGE: {
                            MessageCommand commandData = (MessageCommand) command.getData();
                            String username = commandData.getUsername();
                            String message = commandData.getMessage();
                            nickname = username;
                            controller.setUserName(nickname);
                            controller.showMessage(message);
                            controller.updateNickname(nickname);
                            break;
                        }
                        default:
                            System.err.println("Неверный тип команды: " + command.getType());
                    }
                } catch (IOException e) {
                    System.out.println("Поток чтения был прерван!");
                    return;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Отправка сообщения клиентом
     *
     * @param command - объект с текстовой информацией
     * @throws IOException - пробрасываем исключение при отправке текстового сообщения
     */
    public void sendCommand(Command command) throws IOException {
        out.writeObject(command);
    }

    /**
     * Задает обработчик сообщений от сервера
     *
     * @param messageHandler - обработчик сообщений
     */
    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Задает событие, осуществляемое при успешной авторизации
     *
     * @param successfulAuthEvent - событие
     */
    public void setSuccessfulAuthEvent(AuthEvent successfulAuthEvent) {
        this.successfulAuthEvent = successfulAuthEvent;
    }

    /**
     * Закрытие подключения к серверу
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
