package com.example.gui_chat_1147;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;

    @FXML // Обработчк кнопки отправить
    protected void handlerSend(){
        String text = textField.getText();
        textArea.appendText(text+"\n");// Нужно добавлять текст, а не заменять
        textField.clear(); // Очистить textField
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}