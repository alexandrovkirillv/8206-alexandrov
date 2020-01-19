package ru.focusstart.tomsk;

import java.io.IOException;

public class ClientStart {
    public static void main(String[] args) throws IOException, InterruptedException {
        View view = new View();
        new Thread(view).start();
        int access = 0;

        while (true) {
            Thread.sleep(1000);
            if (view.getAccess() == 3) {
                access = view.getAccess();
                break;
            }
        }
        if (access == 3) {
            view.startClient(view);
        }
    }
}
