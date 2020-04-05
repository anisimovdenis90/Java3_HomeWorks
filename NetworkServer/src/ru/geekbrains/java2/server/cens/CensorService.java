package ru.geekbrains.java2.server.cens;

import ru.geekbrains.java2.server.auth.DataBaseAuthService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CensorService {

    private static final String BAD_WORD_REPLACER = "<censured>";
    private static final String DB_TABLE_NAME = "cens_words";
    private static final String DB_COLUMN_NAME = "bad_word";
    private static final String SQL_FIRST = String.format("SELECT * FROM %s WHERE %s", DB_TABLE_NAME, DB_COLUMN_NAME);

    private DataBaseAuthService dataBase;
    private ResultSet resultSet;

    public CensorService(DataBaseAuthService dataBase) {
        this.dataBase = dataBase;
        System.out.println("Сервис цензуры запущен");
    }

    /**
     * Проверяет присутствие слова в переданном сообщении
     * в базе данных цензурных слов
     *
     * @param message - переданное на проверку сообщение
     * @return String - обработанное сообщение
     */
    public String censor(String message) {
        String[] wordsToCheck = message.toLowerCase().split("\\s+");
        for (String word : wordsToCheck) {
            if ((word.length() > 2)) {
                if (findWordInDB(word.replace("ё", "е"))) {
                    System.out.println("Сервис цензуры сработал!");
                    message = message.replace(word, BAD_WORD_REPLACER);
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
    private boolean findWordInDB(String wordToCheck) {
        if (dataBase.getStatement() != null) {
            try {
                resultSet = dataBase.getStatement().executeQuery(String.format(SQL_FIRST + " = '%s'", wordToCheck));
                resultSet.next();
                if(resultSet.getString(DB_COLUMN_NAME) != null)
                    return true;
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }
}
