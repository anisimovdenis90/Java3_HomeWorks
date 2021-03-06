package ru.geekbrains.java2.client;

import ru.geekbrains.java2.client.controller.ClientController;

import java.io.IOException;

/**
 * Обеспечивает запуск клиентского приложения с заданным адресом сервера и портом подключения
 */
public class NetworkClient {

    public static void main(String[] args) {
        try {
            ClientController clientController = new ClientController("localhost", 8189);
            clientController.runApplication();
        } catch (IOException e) {
            System.err.println("Ошибка подключения к серверу! Проверьте настройки сети");
        }
    }
}
