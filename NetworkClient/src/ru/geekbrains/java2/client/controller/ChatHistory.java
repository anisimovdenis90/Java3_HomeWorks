package ru.geekbrains.java2.client.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ChatHistory {
    private static final int COUNT_STRINGS = 100;
    private static final String ADD_NAME = "history_";

    private ClientController controller;
    private String fileName;
    private File fileHistory;
    private BufferedWriter fileWriter;

    public ChatHistory(ClientController controller, String nickname) {
        this.controller = controller;
        fileName = ADD_NAME + nickname + ".txt";
        this.fileHistory = new File(fileName);
        startWriteChatHistory();
    }

    /**
     * Запускает выходной поток для записи в файл
     */
    public void startWriteChatHistory() {
        try {
            fileWriter = new BufferedWriter(new FileWriter(fileHistory, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Записывает историю чата в файл
     * @param message - сообщение для записи
     */
    public void writeHistory(String message) {
        try {
            fileWriter.write(message);
            fileWriter.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка записи данных в файл истории!");
            e.printStackTrace();
        }
    }

    /**
     * Считывает историю чата из файла
     */
    public void readHistory() {
        try {
            List<String> stringsOfHistory = Files.readAllLines(Paths.get(fileName));
            int i = 0;
            if (stringsOfHistory.size() > COUNT_STRINGS) {
                i = stringsOfHistory.size() - COUNT_STRINGS;
            }
            for (int j = i; j < stringsOfHistory.size(); j++) {
                controller.getClientChat().appendMessage(stringsOfHistory.get(j));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Останавливает поток записи
     */
    public void stopWriteChatHistory() {
        try {
            if (fileWriter != null) fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
