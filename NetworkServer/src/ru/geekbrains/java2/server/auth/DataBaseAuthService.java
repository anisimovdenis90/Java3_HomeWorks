package ru.geekbrains.java2.server.auth;

import java.sql.*;

public class DataBaseAuthService implements AuthService {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    // Новый путь к драйверу последней версии MySQL
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/";
    private String username = "root";
    private String password = "gtr120519";
    private String dbname = "chat_users";

    // Для подключения к базе требуется задать часовой пояс
    private String timeZoneConfiguration = "?serverTimezone=Europe/Moscow&useSSL=false";

    public Statement getStatement() {
        return statement;
    }

    /**
     * Организует подключение к базе данных пользователей
     */
    @Override
    public void start() {
        try {
            // Регистрация драйвера в DriverManager
            Class.forName(driver);
            connection = DriverManager.getConnection(url + dbname + timeZoneConfiguration, username, password);
            statement = connection.createStatement();
            System.out.println("Подключение к базе данных установлено");
        } catch (ClassNotFoundException e) {
            System.err.println("Ошибка загрузки драйвера базы данных!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных!");
            e.printStackTrace();
        }
    }

    /**
     * Возвращает никнейм пользователя при авторизации
     *
     * @param login    - логин, введенный пользователем
     * @param password - пароль, введенный пользователем
     * @return String  - никнейм пользователя
     */
    @Override
    public String[] getUserNickAndIDByLoginAndPassword(String login, String password) {
        String[] userNickAndID = new String[2];
        try {
            resultSet = statement.executeQuery(String.format("SELECT id, nickname FROM users WHERE login = '%s' AND password = '%s'", login, password));
            while (resultSet.next()) {
                userNickAndID[0] = "id" + resultSet.getString("id");
                userNickAndID[1] = resultSet.getString("nickname");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения данных из базы");
            e.printStackTrace();
        }
        return userNickAndID;
    }

    /**
     * Изменяет никнейм пользователя в базе данных
     *
     * @param oldNickname - старый никнейм пользователя
     * @param newNickname - новый никнейм
     * @return int - кол-во измененных строк
     */
    @Override
    public int changeNickname(String oldNickname, String newNickname) {
        int result = 0;
        try {
            result = statement.executeUpdate(String.format("UPDATE users SET nickname = '%s' WHERE nickname = '%s'", newNickname, oldNickname));
        } catch (SQLException e) {
            System.err.println("Ошибка изменения данных в базе");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Закрывает соединение с базой данных
     */
    @Override
    public void stop() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println("Ошибка закрытия соединения с базой данных");
            e.printStackTrace();
        }
    }
}
