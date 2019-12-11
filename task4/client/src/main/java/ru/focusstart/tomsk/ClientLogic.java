package ru.focusstart.tomsk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class ClientLogic {

    private static Socket socket;
    private static BufferedReader in;
    private static OutputStreamWriter out;
    private static ObjectMapper objectMapper = new ObjectMapper();


    public ClientLogic(String addr, int port, String nickName) throws IOException {
        try {
            socket = new Socket(addr, port);
        } catch (IOException e) {
            System.err.println("Socket failed");
        }
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new OutputStreamWriter(socket.getOutputStream());

            new ReadMsg().start();
        } catch (IOException e) {
            ClientLogic.downService();
        }
        new WriteMsg("Connected", nickName, "OK").start();

    }

    private static void downService() {
        try {
            if (!socket.isClosed()) {
                ClientLogic.socket.close();
                ClientLogic.in.close();
                ClientLogic.out.close();
            }
        } catch (IOException ignored) {
        }
    }

    private static void downWindow() {
        View.shutdown();
    }

    private static class ReadMsg extends Thread {

        @Override
        public void run() {
            String inWord;
            try {
                while (true) {
                    inWord = in.readLine();
                    Message inMessage = objectMapper.readValue(inWord, Message.class);
                    if (inMessage.getSystemMessage().equals("Nick already taken")) {
                        View.setSupportMessage("Nick already taken");
                        downService();
                    } else if (inMessage.getSystemMessage().equals("welcome")) {
                        View.setDisplay();
                        View.setNickBox(inMessage.getListOfUsers());
                        View.sendMessageListener(inMessage);
                    }

                    if (inMessage.getSystemMessage().equals("OK")) {
                        View.sendMessageListener(inMessage);
                    }
                    if (inMessage.getSystemMessage().equals("stop")) {
                        View.setNickBox(inMessage.getListOfUsers());
                        View.sendMessageListener(inMessage);
                    }

                }
            } catch (IOException e) {
                ClientLogic.downService();
            }
        }
    }

    public static class WriteMsg extends Thread {
        String word;
        String nickName;
        String systemMessage;

        WriteMsg(String word, String nickName, String systemMessage) {
            this.word = word;
            this.nickName = nickName;
            this.systemMessage = systemMessage;
        }

        @Override
        public void run() {
            try {
                if (word.equals("stop")) {
                    ;
                    out.write(objectMapper.writeValueAsString(new Message("Disconnected", nickName, "stop")) + "\n");
                    out.flush();
                    downService();
                    downWindow();

                } else {
                    String result = objectMapper.writeValueAsString(new Message(word, nickName, "OK"));
                    out.write(result + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                ClientLogic.downService();

            }
        }

    }
}

