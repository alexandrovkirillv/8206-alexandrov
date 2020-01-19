package ru.focusstart.tomsk;

public interface Observer {


    public void onConnectionFailed(Exception e);
    public void onDisconnected();
    void displayChatWindow();
    void writeMsgFromServer(Message message);
}
