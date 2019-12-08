package ru.focusstart.tomsk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

public class Message {

    private String messageTime;
    private String message;
    private String nickName;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now;
    private HashSet<String> listOfUsers;

    Message(){
    }

    Message(String message, String nickName){
        this.messageTime=dtf.format(now = LocalDateTime.now());
        this.message=message;
        this.nickName=" <" + nickName + "> ";
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getMessage() {
        return message;
    }

    public void setListOfUsers(HashSet<String> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    public HashSet<String> getListOfUsers() {
        return listOfUsers;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public String toString() {
        return messageTime + nickName + message;
    }
}
