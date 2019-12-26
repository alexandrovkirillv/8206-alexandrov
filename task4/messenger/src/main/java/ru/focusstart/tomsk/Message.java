package ru.focusstart.tomsk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;


public class Message {
//    public static void main(String[] args) {
//
//        Set<String> tpm = new HashSet<>();
//        tpm.add("hello");
//
//        Set<String> listOfUsers = Collections.unmodifiableSet(tpm);
//        System.out.println(listOfUsers);
//        tpm.add("heldsflo");
//
//
//        System.out.println(listOfUsers);
//
//    }

    private String messageTime;
    private String message;
    private String nickName;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private String systemMessage;
    private Set<String> listOfUsers;

    public Set<String> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(Set<String> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    Message() {
    }

    Message(String message, String nickName, String systemMessage) {
        this.messageTime = dtf.format(LocalDateTime.now());
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
