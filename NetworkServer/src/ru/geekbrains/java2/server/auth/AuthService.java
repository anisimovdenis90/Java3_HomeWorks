package ru.geekbrains.java2.server.auth;

public interface AuthService {

    String[] getUserNickAndIDByLoginAndPassword(String login, String password);
    int changeNickname(String oldNickname, String newNickname);

    void start();
    void stop();

}
