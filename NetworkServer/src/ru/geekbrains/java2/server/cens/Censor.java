package ru.geekbrains.java2.server.cens;

import ru.geekbrains.java2.server.auth.AuthService;

public interface Censor extends AuthService {

    String messageCensor(String message);

}
