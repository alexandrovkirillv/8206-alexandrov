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
            downService();
        }
        new WriteMsg("connected", nickName).start();

    }

    private static void downService() {
        try {
            if (!socket.isClosed()) {
                View.shutdown();
                socket.close();
                in.close();
                out.close();
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
                    if(inMessage.getMessage().equals("Nick already taken")){

                    }
                    new View.sendMessageListener(inMessage).actionPerformed();
                }
            } catch (IOException e) {
                downService();
            }
        }
    }

    public static class WriteMsg extends Thread {
        String word;
        String nickName;

        WriteMsg(String word, String nickName) {
            this.word = word;
            this.nickName = nickName;
        }

        @Override
        public void run() {
            try {
                if (word.equals("stop")) {
                    out.write(objectMapper.writeValueAsString(new Message("Stopped", nickName)));
                    out.flush();
                    ClientLogic.downService();

                } else {
                    String result = objectMapper.writeValueAsString(new Message(word, nickName));
                    out.write(result + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                ClientLogic.downService();

            }
        }

    }
}

