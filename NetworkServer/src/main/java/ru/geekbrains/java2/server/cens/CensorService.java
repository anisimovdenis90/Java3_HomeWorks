package ru.geekbrains.java2.server.cens;

import ru.geekbrains.java2.server.NetworkServer;
import ru.geekbrains.java2.server.auth.DataBaseAuthService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CensorService extends DataBaseAuthService implements Censor {

    private static final String BAD_WORD_REPLACER = "<censured>";
    private static final String DB_TABLE_NAME = "cens_words";
    private static final String DB_COLUMN_NAME = "bad_word";
    private static final String SQL_FIRST = String.format("SELECT * FROM %s WHERE %s", DB_TABLE_NAME, DB_COLUMN_NAME);

    public CensorService() {
//        System.out.println("Включена цензурная фильтрация");
        NetworkServer.getInfoLogger().info("Включена цензурная фильтрация");
    }

    /**
     * Проверяет присутствие слова в переданном сообщении
     * в базе данных цензурных слов
     *
     * @param message - переданное на проверку сообщение
     * @return String - обработанное сообщение
     */
    @Override
    public String messageCensor(String message) {
        String[] wordsToCheck = message.toLowerCase().split("\\s+");
        for (String word : wordsToCheck) {
            if ((word.length() > 2)) {
                if (findWordInDB(word.replace("ё", "е"))) {
//                    System.out.println("Сервис цензуры сработал!");
                    NetworkServer.getInfoLogger().info("Сервис цензуры сработал!");
                    message = message.replace(word, word.length() > 4 ?
                            String.format("%s%s%s", word.substring(0, 2), BAD_WORD_REPLACER, word.substring(word.length() - 2)) :
                            String.format("%c%s%c", word.charAt(0), BAD_WORD_REPLACER, word.charAt(word.length() - 1)));
                }
            }
        }
        return message;
    }

    /**
     * Проверяет наличие переданного слова в базе данных,
     * возвращает true, если есть и false, если нет.
     *
     * @param wordToCheck - слово на проверку
     * @return boolean
     */
    private synchronized boolean findWordInDB(String wordToCheck) {
        if (super.getStatement() != null) {
            try {
                final ResultSet resultSet = super.getStatement().executeQuery(String.format(SQL_FIRST + " = '%s'", wordToCheck));
                return resultSet.next();
            } catch (SQLException e) {
//                System.err.println("Ошибка получения данных сервером цензуры");
                NetworkServer.getFatalLogger().fatal("Ошибка получения данных сервером цензуры", e);
                return false;
            }
        } else {
//            System.err.println("Цензура не работает, отсутствует связь с базой данных!");
            NetworkServer.getInfoLogger().error("Цензура не работает, отсутствует связь с базой данных!");
        }
        return false;
    }

    @Override
    public boolean isCensured(String message) {
        return message.equals(messageCensor(message));
    }
}
