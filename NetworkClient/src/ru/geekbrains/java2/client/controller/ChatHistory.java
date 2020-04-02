package ru.geekbrains.java2.client.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ChatHistory {
    private static final int COUNT_STRINGS = 100;
    private static final String ADD_NAME = "history_";
    private static final String FILE_EXTENSION = ".txt";

    private ClientController controller;
    private BufferedWriter fileWriter;
    private String fileName;
    private File fileHistory;

    public ChatHistory(ClientController controller, String nickname) {
        this.controller = controller;
        fileName = ADD_NAME + nickname + FILE_EXTENSION;
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
            System.err.println("Ошибка при создании потока записи в файл истории!");
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
            fileWriter.flush();
        } catch (IOException e) {
            System.err.println("Ошибка записи данных в файл истории!");
            e.printStackTrace();
        }
    }

    /**
     * Считывает количество строк из файла истории, заданное в COUNT_STRINGS
     */
    public void readHistory() {
        // если файл пустой, выходим
        if (fileHistory.length() == 0) {
            return;
        }
        try {
            // Получаем массив строк из файла истории, проверяем размерность
            List<String> stringsOfHistory = Files.readAllLines(Paths.get(fileName));
            int i = 0;
            if (stringsOfHistory.size() > COUNT_STRINGS) {
                i = stringsOfHistory.size() - COUNT_STRINGS;
            }
            // Выводим на печать строки из файла истории
            for (int j = i; j < stringsOfHistory.size(); j++) {
                controller.getClientChat().updateChatText(stringsOfHistory.get(j));
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения из файла истории!");
            e.printStackTrace();
        }
    }

    /**
     * Переименовывает файл истории при смене никнейма пользователем
     * @param newNickname - новый никнейм
     */
    public void renameFileHistory(String newNickname) {
        stopWriteChatHistory();
        fileName = ADD_NAME + newNickname + FILE_EXTENSION;
        File newFile = new File(fileName);
        if (fileHistory.renameTo(new File(fileName))) {
            System.out.println("Файл истории чата успешно переименован.");
        }
        fileHistory = newFile;
        startWriteChatHistory();
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
