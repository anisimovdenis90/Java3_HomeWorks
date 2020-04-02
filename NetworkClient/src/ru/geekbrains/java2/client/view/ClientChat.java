package ru.geekbrains.java2.client.view;

import ru.geekbrains.java2.client.controller.ClientController;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ClientChat extends JFrame {

    private JPanel mainPanel;
    private JList<String> usersList;
    private JTextField messageTextField;
    private JButton sendButton;
    private JTextArea chatText;
    private JTextField nicknameText;
    private JButton changeNickButton;
    // Ссылка на контроллер
    private ClientController controller;
    private String selectedContact = "All";

    public ClientChat(ClientController controller) {
        this.controller = controller;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);
        addListeners();
        // При закрытии окна отключаем клиента от сервера
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.sendEndMessage();
                controller.shutdown();
            }
        });
    }

    /**
     * Добавление действий на кнопку и поле ввода текста
     */
    private void addListeners() {
        sendButton.addActionListener(e -> ClientChat.this.sendMessage());
        messageTextField.addActionListener(e -> sendMessage());
        changeNickButton.addActionListener(e -> ClientChat.this.sendChangeNicknameMessage());
    }

    /**
     * отправляет сообщение о смене никнейма
     */
    private void sendChangeNicknameMessage() {
        String newNickname = nicknameText.getText().trim();
        if (newNickname.length() < 3) {
            showError("Слишком короткое имя пользователя.");
            return;
        }
        controller.sendChangeNickNameMessage(newNickname);
        nicknameText.setText(null);
    }

    /**
     * Метод отправки сообщения из окна чата
     */
    private void sendMessage() {
        String message = messageTextField.getText().trim();
        if (message.isEmpty()) {
            return;
        }
        // Отправка сообщения всем
        if (usersList.getSelectedIndex() < 1) {
            appendMessage("Я: " + message);
            controller.sendMessageToAllUsers(message);
        }
        // Отправка сообщения выбранному контакту
        else {
            String username = usersList.getSelectedValue();
            appendMessage(String.format("Я лично %s: %s", username, message));
            controller.sendPrivateMessage(username, message, controller.getUsername());
        }
        // Очищаем окно ввода текста после отправки сообщения
        messageTextField.setText(null);
    }

    /**
     * Метод обновления окна чата в отдельном потоке
     *
     * @param message - текст полученного сообщения
     */
    public void appendMessage(String message) {
        controller.getChatHistory().writeHistory(message);
        SwingUtilities.invokeLater(() -> {
            updateChatText(message);
        });
    }

    public void updateChatText(String message) {
        chatText.append(message + System.lineSeparator());
    }

    /**
     * Отображает предупреждающее окно
     *
     * @param message - описание ошибки
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка!", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Обновление списка контактов
     *
     * @param users - на вход коллекция контактов
     */
    public void updateUsers(List<String> users) {
        SwingUtilities.invokeLater(() -> {
            DefaultListModel<String> model = new DefaultListModel<>();
            model.addAll(users);
            usersList.setModel(model);
        });
    }

    public void updateNickname(String nickname) {
        SwingUtilities.invokeLater(() -> ClientChat.this.setTitle(nickname));
    }
}
