package ru.focusstart.tomsk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Properties;

class ServerLogic extends Thread {

    private Socket socket;
    private BufferedReader in;
    private OutputStreamWriter out;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static HashSet<String> listOfUsers = new HashSet<>();

    public ServerLogic(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new OutputStreamWriter(socket.getOutputStream());
        start();
    }

    @Override
    public void run() {
        String inWord;
        try {
            inWord = in.readLine();
            Message connectMessage = objectMapper.readValue(inWord, Message.class);
            if(!listOfUsers.add(connectMessage.getNickName())){
                connectMessage.setMessage("Nick already taken");
                this.send(connectMessage);
                return;
            }
            connectMessage.setListOfUsers(listOfUsers);
            for (ServerLogic vr : StartServer.serverList) {
                vr.send(connectMessage);
            }
            try {
                while (true) {
                    inWord = in.readLine();
                    System.out.println("Server received " + inWord);
                    if (inWord != null) {
                        Message inMessage = objectMapper.readValue(inWord, Message.class);
                        if (inMessage.getMessage().equals("Stopped")) {
                            for (ServerLogic vr : StartServer.serverList) {
                                vr.send(inMessage);
                            }
                            this.downService();
                            break;
                        }
                        for (ServerLogic vr : StartServer.serverList) {
                            vr.send(inMessage);
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (NullPointerException | InterruptedException e) {
                e.printStackTrace();
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
                for (ServerLogic vr : StartServer.serverList) {
                    if (vr.equals(this)) vr.interrupt();
                    StartServer.serverList.remove(this);
                }
            }
        } catch (IOException ignored) {
        }
    }
}

public class StartServer {
    private static Properties properties = new Properties();
    static LinkedList<ServerLogic> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {

        try (InputStream propertiesStream = StartServer.class.getResourceAsStream("/server.properties")) {
            if (propertiesStream != null) {
                properties.load(propertiesStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerSocket server = new ServerSocket(Integer.parseInt(properties.getProperty("server.port")));

        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    serverList.add(new ServerLogic(socket));
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}