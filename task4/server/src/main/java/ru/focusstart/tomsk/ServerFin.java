package ru.focusstart.tomsk;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

public class ServerFin {
    private ArrayList<MessageService> clients = new ArrayList<>();
    private Logger logger = Logger.getLogger("");

    ServerFin() {
        Socket clientSocket = null;
        ServerSocket server = null;

        try {
            server = new ServerSocket(Integer.parseInt(getProperties().getProperty("server.port")));
            logger.info("Server Started");
            while (true) {
                clientSocket = server.accept();
                // создаём обработчик клиента, который подключился к серверу
                // this - это наш сервер
                MessageService client = new MessageService(clientSocket, this);
                clients.add(client);
                // каждое подключение клиента обрабатываем в новом потоке
                new Thread(client).start();
            }
        } catch (NumberFormatException | IOException e) {
            logger.error("properties not found", e);
        }finally {
            try {
                clientSocket.close();
                System.out.println("Сервер остановлен");
                server.close();
            } catch (IOException e) {
                logger.error("Server stopped", e);
            }
        }
    }

    void removeClient(MessageService client) {
        clients.remove(client);
    }

    void sendMessageToAllClients(String msg) {
        for (MessageService o : clients) {
            o.sendMsg(msg);
        }
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream propertiesStream = StartServer.class.getResourceAsStream("/server.properties")) {
            if (propertiesStream != null) {
                properties.load(propertiesStream);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        return properties;
    }
}

class StartServerFin {
    public static void main(String[] args) {
        new ServerFin();
    }
}