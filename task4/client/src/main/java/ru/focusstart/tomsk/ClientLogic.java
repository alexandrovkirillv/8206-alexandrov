package ru.focusstart.tomsk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

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
    private static Logger logger = Logger.getLogger("");
    private static Observer observer;


    public ClientLogic(String addr, int port, String nickName, Observer observer) {
        this.observer = observer;
        try {
            socket = new Socket(addr, port);
        } catch (IOException e) {
            logger.error("Connection refused", e);
        }
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new OutputStreamWriter(socket.getOutputStream());

            new ReadMsg().start();
        } catch (IOException e) {
            logger.error("Connection refused", e);
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

    private class ReadMsg extends Thread {

        @Override
        public void run() {
            String inWord;
            try {
                while (true) {
                    inWord = in.readLine();
                    System.out.println("inWord " + inWord);
                    Message inMessage = objectMapper.readValue(inWord, Message.class);
                    if (inMessage.getSystemMessage().equals("Nick already taken")) {
                        observer.setSupportMessage("Nick already taken");
                        downService();
                    } else if (inMessage.getSystemMessage().equals("welcome")) {
                        observer.openDisplay();
                        observer.setNickBox(inMessage.getListOfUsers());
                        System.out.println("HELL FROM CLIENT");
                        observer.sendMessage(inMessage);
                    }

                    if (inMessage.getSystemMessage().equals("OK")) {
                        observer.sendMessage(inMessage);
                    }
                    if (inMessage.getSystemMessage().equals("stop")) {
                        observer.setNickBox(inMessage.getListOfUsers());
                        observer.sendMessage(inMessage);
                    }

                }
            } catch (IOException | IllegalArgumentException | NullPointerException e) {
                logger.error("error",e);
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
                    out.write(objectMapper.writeValueAsString(new Message("Disconnected", nickName, "stop")) + "\n");
                    out.flush();
                    downService();
                    observer.onDisconnected();

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

