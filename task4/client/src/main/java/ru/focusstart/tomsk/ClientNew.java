package ru.focusstart.tomsk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

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
        AtomicReference<Boolean> isStart = new AtomicReference<>(false);

        messageListenerThread = new Thread(() -> {
            while (!messageListenerThread.isInterrupted()) {
                try {
                    String inMessageStr = reader.readLine();
                    Message inMessage = mapper.readValue(inMessageStr, Message.class);
                    if (inMessage.getSystemMessage().equals("Nick already taken") && isStart.get().equals(false)) {
                        observer.setSupportMessage("Nick already taken");
                        break;
                    } else if (inMessage.getSystemMessage().equals("start") && isStart.get().equals(false)) {
                        isStart.set(true);
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
                } catch (IOException ignored) {
                    logger.info("Exit");
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
