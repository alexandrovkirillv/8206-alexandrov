package ru.focusstart.tomsk;

import java.io.IOException;

public class StartClient {
    public static void main(String[] args) throws IOException {
        View view = new View();
        ClientNew clientNew = new ClientNew(view);
    }
}
