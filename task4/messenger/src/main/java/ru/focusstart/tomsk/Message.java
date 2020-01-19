package ru.focusstart.tomsk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private String messageTime;
    private String message;
    private String nickName;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private String systemMessage;

    Message() {
    }

    Message(String message, String nickName, String systemMessage) {
        this.messageTime = DATE_TIME_FORMATTER.format(LocalDateTime.now());
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

    @Override
    public String toString() {
        return messageTime + nickName + message;
    }
}
