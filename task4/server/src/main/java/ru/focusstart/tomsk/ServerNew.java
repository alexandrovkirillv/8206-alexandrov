package ru.focusstart.tomsk;


import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        try (InputStream propertiesStream = ServerNew.class.getResourceAsStream("/server.properties")) {
            if (propertiesStream != null) {
                properties.load(propertiesStream);
            }
        }

        serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("server.port")));
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
                        System.out.println("new message received: " +inMessageStr);
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

    private static void shutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                serverSocket.close();
                for (Socket socket : clients) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }));
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

