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
        new WriteMsg("connected", nickName, "OK").start();
    }

    private static void downService() {
        try {
            if (!socket.isClosed()) {
                View.shutdown();
                ClientLogic.socket.close();
                ClientLogic.in.close();
                ClientLogic.out.close();
                System.exit(0);
            }
        } catch (IOException ignored) {
        }
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
                        //ClientLogic.downService();
                    } else if (inMessage.getSystemMessage().equals("welcome")) {
                        View.setDisplay();
                        View.sendMessageListener(inMessage);
                    }

                    if (inMessage.getSystemMessage().equals("OK") || inMessage.getSystemMessage().equals("stop")) {
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
                    System.out.println("stop");
                    out.write(objectMapper.writeValueAsString(new Message("Stopped", nickName, "stop")) + "\n");
                    out.flush();
                    ClientLogic.downService();

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

