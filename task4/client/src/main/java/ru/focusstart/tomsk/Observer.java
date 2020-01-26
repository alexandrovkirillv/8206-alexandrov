package ru.focusstart.tomsk;

import java.util.List;

public interface Observer {

    void onConnectionFailed(Exception e);

    void onDisconnected();

    void onConnected(List<String> listOfNicknames);


    void displayChatWindow();

    void writeMsgFromServer(Message message);

    void setSupportMessage(String string);

    void updateNickBox(List<String> listOfNicknames);
}
