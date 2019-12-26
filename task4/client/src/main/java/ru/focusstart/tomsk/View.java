package ru.focusstart.tomsk;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Set;

public class View implements Observer, Runnable {

    private static String appName = "Chat";
    private static JFrame newFrame = new JFrame(appName);
    private static JTextField messageBox;
    private static JTextArea chatBox;
    private static JTextField userNameChooser;
    private static JTextField hostNameChooser;
    private static JTextField portNameChooser;
    private static JLabel supportMessage;
    private static JFrame preFrame;
    private static JTextArea nickBox = new JTextArea();
    private static Logger logger = Logger.getLogger("");


    public int getAccess() {
        return access;
    }

    @Override
    public void run() {
        preDisplay();
    }

//    public void start() {
//            @Override
//                    SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    UIManager.setLookAndFeel(UIManager
//                            .getSystemLookAndFeelClassName());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//             preDisplay();
//            }
//        });
//    }

    private void preDisplay() {
        newFrame.setVisible(false);
        preFrame = new JFrame(appName);
        preFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userNameChooser = new JTextField(7);
        hostNameChooser = new JTextField(7);
        portNameChooser = new JTextField(7);
        JLabel chooseUserNameLabel = new JLabel("Pick a username:");
        JLabel chooseHostNameLabel = new JLabel("Enter a host:");
        JLabel choosePortNameLabel = new JLabel("Enter a port:");
        supportMessage = new JLabel();
        JButton enterServer = new JButton("Enter Chat Server");
        enterServer.addActionListener(new enterServerButtonListener());
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preCenter = new GridBagConstraints();
        preCenter.insets = new Insets(4, 0, 4, 0);
        preCenter.fill = GridBagConstraints.NONE;
        preCenter.gridwidth = GridBagConstraints.REMAINDER;


        prePanel.add(chooseUserNameLabel, preCenter);
        prePanel.add(userNameChooser, preCenter);
        prePanel.add(chooseHostNameLabel, preCenter);
        prePanel.add(hostNameChooser, preCenter);
        prePanel.add(choosePortNameLabel, preCenter);
        prePanel.add(portNameChooser, preCenter);
        prePanel.add(supportMessage, preCenter);

        preFrame.add(BorderLayout.CENTER, prePanel);
        preFrame.add(BorderLayout.SOUTH, enterServer);
        preFrame.setSize(600, 600);
        preFrame.setVisible(true);

    }

    public void openDisplay() {
        preFrame.setVisible(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        JButton sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new readMessageButtonListener());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);


        nickBox.setEditable(false);
        nickBox.setLayout(new GridLayout(125, 1));
        nickBox.setFont(new Font("Serif", Font.PLAIN, 15));
        nickBox.setLineWrap(true);
        nickBox.setWrapStyleWord(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(nickBox), BorderLayout.EAST);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(470, 300);
        newFrame.setVisible(true);
    }

    public void setNickBox(Set<String> listOfUsers) {
        nickBox.setText("");
        for (String listOfUser : listOfUsers) {
            nickBox.append(listOfUser + "\n");
        }
    }


    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {
        newFrame.setVisible(false);
        newFrame.dispose();
        System.exit(0);
    }

    @Override
    public void onConnectionFailed() {

    }


    public void sendMessage(Message message) {
        if (message.getMessage().length() > 0) {
           // System.out.println(message.getMessageTime() + message.getNickName() + message.getMessage());
            chatBox.append(message.toString() + "\n");
        }
        messageBox.requestFocusInWindow();
    }

    private static class readMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                // do nothing
            } else if (messageBox.getText().equals("clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                new ClientLogic.WriteMsg(messageBox.getText(), nickName, "OK").start();
                messageBox.setText("");
            }
        }
    }

    void sendMessageListener(Message message) {

    }


    public void setSupportMessage(String string) {
        supportMessage.setText(string);
    }

    private static String nickName;
    private static String hostName;
    private static String portName;
    private static int access;

    class enterServerButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            nickName = userNameChooser.getText();
            hostName = hostNameChooser.getText();
            portName = portNameChooser.getText();


            if (!portName.matches("[0-9]{4}")) {
                setSupportMessage("Wrong port!");
            } else access++;
            if (nickName.length() < 1) {
                setSupportMessage("Field name cannot be empty");
            } else access++;
            if (!hostName.matches("([0-9]{1,3}[.]){3}[0-9]{1,3}") && !hostName.equals("localhost")) {
                setSupportMessage("incorrect host");
            } else access++;
            System.out.println("access " + access);

        }
    }


    void startClient(View view) throws IOException {
        try {
            new ClientLogic(hostName, Integer.parseInt(portName), nickName, this);
        } catch (NullPointerException e) {
            logger.error("Connection refused", e);
        }

    }
}