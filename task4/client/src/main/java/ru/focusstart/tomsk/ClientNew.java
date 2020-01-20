package ru.focusstart.tomsk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientNew {

    private static Socket socket;
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static ObjectMapper mapper = new ObjectMapper();
    private static Observer observer;


    public ClientNew(Observer observer){
        this.observer = observer;

    }


    static void connect(ConnectProperties connectProperties) throws IOException {
        try {
            socket = new Socket(connectProperties.getHostName(), connectProperties.getPortName());
        } catch (IOException e) {
            observer.onConnectionFailed(e);
        }

        if (socket != null) {
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            observer.displayChatWindow();

            readMessage();
        }
    }

    private static void readMessage() {
        Thread messageListenerThread = new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    String inMessageStr = reader.readLine();
                    Message inMessage = mapper.readValue(inMessageStr, Message.class);

                    System.out.println(inMessage.toString());
                    observer.writeMsgFromServer(inMessage);


                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        messageListenerThread.start();

    }

    static void writeMessage(Message message) throws IOException {

        String outMessage = mapper.writeValueAsString(message);
        writer.println(outMessage);
        writer.flush();

        if (message.getSystemMessage().equals("stop")) {

            observer.onDisconnected();
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
            writer.close();
            reader.close();
        }

    }
}
