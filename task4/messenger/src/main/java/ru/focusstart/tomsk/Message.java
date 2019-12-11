package ru.focusstart.tomsk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class Message {

    private String messageTime;
    private String message;
    private String nickName;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now;
    private String systemMessage;
    private HashSet<String> listOfUsers;


    Message() {
    }

    Message(String message, String nickName, String systemMessage) {
        this.messageTime = dtf.format(now = LocalDateTime.now());
        this.message = message;
        this.nickName = " <" + nickName + "> ";
        this.systemMessage = systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getNickName() {
        return nickName;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setListOfUsers(HashSet<String> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    public HashSet<String> getListOfUsers() {
        return listOfUsers;
    }

    @Override
    public String toString() {
        return messageTime + nickName + message;
    }
}
