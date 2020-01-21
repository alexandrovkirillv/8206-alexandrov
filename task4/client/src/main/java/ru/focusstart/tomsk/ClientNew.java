package ru.focusstart.tomsk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

class ClientNew {

    private static Socket socket;
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static ObjectMapper mapper = new ObjectMapper();
    private static Observer observer;
    private static Thread messageListenerThread;
    private static Logger logger = Logger.getLogger("");


    ClientNew(Observer observer) {
        ClientNew.observer = observer;
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

            readAndParseMessage();
        }
    }

    private static void readAndParseMessage() {
        AtomicInteger startCounter = new AtomicInteger();
        messageListenerThread = new Thread(() -> {
            while (!messageListenerThread.isInterrupted()) {
                try {
                    String inMessageStr = reader.readLine();
                    Message inMessage = mapper.readValue(inMessageStr, Message.class);
                    if (inMessage.getSystemMessage().equals("Nick already taken") && startCounter.get() == 0) {
                        observer.setSupportMessage("Nick already taken");
                        break;
                    } else if (inMessage.getSystemMessage().equals("start") && startCounter.get() == 0) {
                        startCounter.getAndIncrement();
                        observer.onConnected(inMessage.getListOfNicknames());
                    }

                    if (inMessage.getSystemMessage().equals("start")) {
                        observer.updateNickBox(inMessage.getListOfNicknames());
                    }

                    if (inMessage.getSystemMessage().equals("stop")) {
                        observer.updateNickBox(inMessage.getListOfNicknames());
                    }

                    System.out.println(inMessage.toString());
                    if (!inMessage.getMessage().equals("")) {
                        observer.writeMsgFromServer(inMessage);
                    }
                } catch (IOException e) {
                    logger.error("IOException", e);
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
            closeClient();
        }

    }

    private static void closeClient() throws IOException {
        messageListenerThread.interrupt();
        writer.close();
        reader.close();
        socket.close();
        observer.onDisconnected();
    }
}
