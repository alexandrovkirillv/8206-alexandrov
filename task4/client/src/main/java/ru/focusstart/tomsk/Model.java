package ru.focusstart.tomsk;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Model {

    private static String[] dataList = new String[21];
    private static List<String> nicks=  new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(700, 700));
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Box box = Box.createVerticalBox();
        frame.add(box);

        Dimension size = new Dimension(100, 30);

        Font font = new Font("Verdana", Font.PLAIN, 11);

        JLabel label = new JLabel();
        label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        label.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        label.setPreferredSize(size);
        label.setFont(font);


        JLabel label2 = new JLabel();
        label2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        label2.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        label2.setPreferredSize(size);
        label2.setFont(font);
        label2.setText("Enter nickname");

        JButton button1 = new JButton("OK");
        JTextField enterField = new JTextField();

        button1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        button1.setAlignmentY(JComponent.CENTER_ALIGNMENT);

        enterField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        enterField.setAlignmentY(JComponent.CENTER_ALIGNMENT);

        enterField.setPreferredSize(size);

        button1.setPreferredSize(size);

        box.add(label2);
        box.add(label);
        box.add(enterField);
        box.add(button1);


        JPanel box1 = new JPanel();
        box1.setLayout(null);
        frame.add(box1);
        box1.setVisible(false);


        button1.addActionListener(e -> {
            String nick = enterField.getText();
            enterField.setText("");
            label2.setText("Enter server ip");
            //  JDialog dialog = createDialog();
            //  dialog.setVisible(true);
            button1.addActionListener(b -> {
                try {
                    InetAddress.getByName(enterField.getText());
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
                enterField.setText("");
                box.setVisible(false);
                dataList[0] = nick;
                JList<String> list = createList(dataList);
               // list.setLocation(20,20);
                box1.add(list);
                box1.add(enterField);
                box1.add(button1);
                frame.setContentPane(box1);
                box1.setVisible(true);
            });
        });

        frame.setVisible(true);


    }

    private static JList<String> createList(String[] dataList) {
        JList<String> list = new JList<>(dataList);

        return list;
    }

    private static JDialog createDialog() {
        JDialog dialog = new JDialog();
        JLabel label = new JLabel("Nick created");
        label.setPreferredSize(new Dimension(90, 30));
        dialog.add(label);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setSize(180, 90);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new GridBagLayout());
        JButton button1 = new JButton();
        button1.setText("OK");
        button1.setPreferredSize(new Dimension(70, 30));
        dialog.add(button1);
        button1.addActionListener(e -> {
            dialog.setVisible(false);
        });
        return dialog;
    }

}