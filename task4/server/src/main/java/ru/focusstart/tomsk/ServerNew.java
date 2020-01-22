package ru.focusstart.tomsk;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ServerNew {

    private static List<Socket> clients = new ArrayList<>();
    private static List<BufferedReader> readers = new ArrayList<>();
    private static List<PrintWriter> writers = new ArrayList<>();
    private static ServerSocket serverSocket;
    private static ObjectMapper mapper = new ObjectMapper();
    private static List<String> listOfNicknames = new ArrayList<>();
    private static Logger logger = Logger.getLogger("");


    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        try (InputStream propertiesStream = ServerNew.class.getResourceAsStream("/server.properties")) {
            if (propertiesStream != null) {
                properties.load(propertiesStream);
            }
        }

        try {
            serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("server.port")));
        }catch (NumberFormatException e){
            logger.error("NumberFormatException", e);
            throw new NumberFormatException();
        }
        System.out.println("Server started");

        readAndWriteMessage();
        listenSockets();
    }

    private static void readAndWriteMessage() {
        Thread messageListenerThread = new Thread(() -> {
            boolean interrupted = false;
            while (!interrupted) {
                try {
                    String inMessageStr = null;
                    for (BufferedReader reader : readers) {
                        if (reader.ready()) {
                            inMessageStr = reader.readLine();
                            break;
                        }
                    }

                    if (inMessageStr != null) {
                        Message message = mapper.readValue(inMessageStr, Message.class);
                        if (listOfNicknames.contains(message.getNickName()) && message.getSystemMessage().equals("start")) {
                            message = new Message("", message.getNickName(), "Nick already taken");
                        } else if (message.getSystemMessage().equals("start")) {
                            listOfNicknames.add(message.getNickName());
                            message.setListOfNicknames(listOfNicknames);
                        }

                        if (message.getSystemMessage().equals("stop")) {
                            listOfNicknames.remove(message.getNickName());
                            message.setListOfNicknames(listOfNicknames);
                        }

                        System.out.println("new message received: " + inMessageStr);
                        for (PrintWriter writer : writers) {
                            writer.println(mapper.writeValueAsString(message));
                            writer.flush();
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        });
        messageListenerThread.start();
    }

    private static void listenSockets() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
            readers.add(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
            writers.add(new PrintWriter(clientSocket.getOutputStream()));
        }
    }
}

