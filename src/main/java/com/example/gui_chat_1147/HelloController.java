package com.example.gui_chat_1147;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class HelloController {
    DataOutputStream out;
    ArrayList<String> usersName = new ArrayList<>();
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    @FXML
    private TextArea onlineUsersTextArea;
    @FXML
    private Button connectBtn;
    @FXML // Обработчк кнопки отправить
    protected void handlerSend(){
        String text = textField.getText();
        try {
            out.writeUTF(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.appendText(text+"\n");// Нужно добавлять текст, а не заменять
        textField.clear(); // Очистить textField
    }
    @FXML
    protected void handlerConnection(){
        try {
            Socket socket = new Socket("127.0.0.1", 8188);
            // Поток ввода
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            // Поток вывода
            out = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String response = "";
                            Object object = ois.readObject();
                            System.out.println(object.getClass());
                            if(object.getClass().equals(usersName.getClass())){
                                usersName = (ArrayList<String>) object;
                                System.out.println(usersName);
                                onlineUsersTextArea.clear();
                                for(String userName : usersName){
                                    onlineUsersTextArea.appendText(userName+"\n");
                                }
                            }else{
                                response = object.toString();
                                textArea.appendText(response+"\n");
                            }
                            /*String response = in.readUTF();*/
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("Соединение разорвано");
                    }
                }
            });
            thread.start();
            connectBtn.setDisable(true);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}