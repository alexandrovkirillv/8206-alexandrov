package ru.focusstart.tomsk;

import java.util.Set;

public interface Observer {
    public void onConnected();

    public void onDisconnected();

    public void onConnectionFailed();

    public void sendMessage(Message message);

    public void setSupportMessage(String message);

    public void openDisplay();

    public void setNickBox(Set<String> listOfUsers);
}
