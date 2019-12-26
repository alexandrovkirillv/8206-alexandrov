package ru.focusstart.tomsk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

class ServerLogic implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private OutputStreamWriter out;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Set<String> listOfUsers = new HashSet<>();
    private static Set<String> unmodifiableSet = Collections.unmodifiableSet(listOfUsers);

    public ServerLogic(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new OutputStreamWriter(socket.getOutputStream());

    }

    @Override
    public void run() {
        Logger logger = Logger.getLogger("");
        String inWord;
        try {
            inWord = in.readLine();
            Message connectMessage = objectMapper.readValue(inWord, Message.class);
            System.out.println("READ");

            if (!listOfUsers.add(connectMessage.getNickName())) {
                connectMessage.setSystemMessage("Nick already taken");
                send(connectMessage);
                return;
            } else {
                for (ServerLogic vr : StartServer.serverList) {
                    connectMessage.setSystemMessage("welcome");
                    connectMessage.setListOfUsers(unmodifiableSet);
                    vr.send(connectMessage);
                }
            }
            try {
                while (true) {
                    inWord = in.readLine();
                    System.out.println("Server received " + inWord);
                    if (inWord != null) {
                        Message inMessage = objectMapper.readValue(inWord, Message.class);

                        if (inMessage.getSystemMessage().equals("stop")) {
                            listOfUsers.remove(inMessage.getNickName());
                            inMessage.setListOfUsers(unmodifiableSet);
                            for (ServerLogic vr : StartServer.serverList) {
                                vr.send(inMessage);
                            }
                            this.downService();
                            break;
                        }
                        for (ServerLogic vr : StartServer.serverList) {
                            send(inMessage);
                        }
                    } else if (inWord.equals(null)) {
                        this.socket.close();
                    }
                    Thread.sleep(100);
                }
            } catch (NullPointerException | InterruptedException e) {
                logger.error("Socket closed incorrect", e);
            }
        } catch (IOException e) {
            this.downService();
        }
    }

    private void send(Message msg) {
        try {
            out.write(objectMapper.writeValueAsString(msg) + "\n");
            out.flush();
        } catch (IOException ignored) {
        }

    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
//                for (ServerLogic vr : StartServer.serverList) {
//                    vr.socket.close();
//                    StartServer.serverList.remove(this);
//                }
            }
        } catch (IOException | ConcurrentModificationException ignored) {
        }
    }
}

public class StartServer {
    private static Properties properties = new Properties();
    static LinkedList<ServerLogic> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger("");
        ServerSocket server = null;
        try (InputStream propertiesStream = StartServer.class.getResourceAsStream("/server.properties")) {
            if (propertiesStream != null) {
                properties.load(propertiesStream);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        try {
            server = new ServerSocket(Integer.parseInt(properties.getProperty("server.port")));
        } catch (NumberFormatException e) {
            logger.info("properties not found", e);
        }

        if (server != null) {
            System.out.println("Server Started");
            try {
                while (true) {
                    Socket socket = server.accept();

                    System.out.println("Current thread: " + Thread.currentThread().getName());
                    try {
                        serverList.add(new ServerLogic(socket));
                        System.out.println("Current thread new: " + Thread.currentThread().getName());
                    } catch (IOException e) {
                        socket.close();
                    }
                }
            } finally {

                server.close();
            }
        }
    }
}