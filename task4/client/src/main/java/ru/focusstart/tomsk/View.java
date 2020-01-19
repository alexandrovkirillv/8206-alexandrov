package ru.focusstart.tomsk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class View implements Observer{

    private static JTextField messageBox;
    private static JTextArea chatBox;
    private static JTextField userNameChooser;
    private static JTextField hostNameChooser;
    private static JTextField portNameChooser;
    private static JLabel supportMessage = new JLabel();
    private static JFrame startWindowFrame = new JFrame();
    private static JFrame chatFrame = new JFrame();


    View() {
        createWelcomeWindow();
    }

    private void createWelcomeWindow() {
        startWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton enterServer = new JButton("Enter Chat Server");
        enterServer.addActionListener(new enterServerButtonListener());
        startWindowFrame.add(BorderLayout.CENTER, createPanelWithTextFields());
        startWindowFrame.add(BorderLayout.SOUTH, enterServer);
        startWindowFrame.setSize(600, 600);
        startWindowFrame.setVisible(true);

    }

    private static JPanel createPanelWithTextFields() {
        userNameChooser = new JTextField(7);
        hostNameChooser = new JTextField(7);
        portNameChooser = new JTextField(7);
        JLabel chooseUserNameLabel = new JLabel("Pick a username:");
        JLabel chooseHostNameLabel = new JLabel("Enter a host:");
        JLabel choosePortNameLabel = new JLabel("Enter a port:");

        GridBagConstraints preCenter = new GridBagConstraints();
        preCenter.insets = new Insets(4, 0, 4, 0);
        preCenter.fill = GridBagConstraints.NONE;
        preCenter.gridwidth = GridBagConstraints.REMAINDER;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(chooseUserNameLabel, preCenter);
        panel.add(userNameChooser, preCenter);
        panel.add(chooseHostNameLabel, preCenter);
        panel.add(hostNameChooser, preCenter);
        panel.add(choosePortNameLabel, preCenter);
        panel.add(portNameChooser, preCenter);
        panel.add(supportMessage, preCenter);

        return panel;
    }

    public void displayChatWindow() {
        try {
            ClientNew.writeMessage(new Message("Connected", nickName,"OK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        startWindowFrame.setVisible(false);



        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        JButton sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new readMessage());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        JTextArea nickBox = new JTextArea();
        nickBox.setEditable(false);
        nickBox.setFont(new Font("Serif", Font.PLAIN, 15));
        nickBox.setLineWrap(true);
        nickBox.append(nickName);

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

        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setVisible(true);
        chatFrame.add(mainPanel);
        chatFrame.setSize(470, 300);

        chatFrame.setVisible(true);

        System.out.println("ALE");
    }

    @Override
    public void writeMsgFromServer(Message message) {
        chatBox.append(message.toString() + "\n");
        messageBox.requestFocusInWindow();
    }



    @Override
    public void onConnectionFailed(Exception e) {
        setSupportMessage("Connection Failed");
        System.out.println("Connection Failed");
        e.printStackTrace();
    }

    @Override
    public void onDisconnected() {
        chatFrame.setVisible(false);

    }


    private static class readMessage implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                // do nothing
            } else if (messageBox.getText().equals("stop")) {
                try {
                    ClientNew.writeMessage(new Message("Disconnected", nickName, "stop"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ClientNew.writeMessage(new Message(messageBox.getText(), nickName, "OK"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                messageBox.setText("");
            }
        }
    }


    private static void setSupportMessage(String string) {
        supportMessage.setText(string);
    }

    private static String nickName;

    static class enterServerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            nickName = userNameChooser.getText();
            String hostName = hostNameChooser.getText();
            String portName = portNameChooser.getText();

            int correctFields = 0;

            if (!portName.matches("[0-9]{4}")) {
                setSupportMessage("Wrong port!");
            } else {
                correctFields++;
            }
            if (nickName.length() < 1) {
                setSupportMessage("Field name cannot be empty");
            } else {
                correctFields++;

            }
            if (!hostName.matches("([0-9]{1,3}[.]){3}[0-9]{1,3}") && !hostName.equals("localhost")) {
                setSupportMessage("incorrect host");
            } else {
                correctFields++;
            }
            if (correctFields == 3) {
                try {
                    ClientNew.connect(new ConnectProperties(nickName, hostName, Integer.parseInt(portName)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}