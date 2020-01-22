package ru.focusstart.tomsk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Message {

    private String messageTime;
    private String message;
    private String nickName;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private String systemMessage;
    private List<String> listOfNicknames = Collections.synchronizedList(new ArrayList<>());

    Message() {
    }

    Message(String message, String nickName, String systemMessage) {
        this.messageTime = DATE_TIME_FORMATTER.format(LocalDateTime.now());
        this.message = message;
        this.nickName = " <" + nickName + "> ";
        this.systemMessage = systemMessage;
    }


    public List<String> getListOfNicknames() {
        return listOfNicknames;
    }

    public void setListOfNicknames(List<String> listOfNicknames) {
        this.listOfNicknames = listOfNicknames;
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

    @Override
    public String toString() {
        return messageTime + nickName + message;
    }
}
