package ru.focusstart.tomsk.file;

import org.apache.commons.io.LineIterator;
import ru.focusstart.tomsk.figures.Circle;
import ru.focusstart.tomsk.figures.Figure;
import ru.focusstart.tomsk.figures.Rectangle;
import ru.focusstart.tomsk.figures.Square;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkingWithFile {
    private String outPutFileName = "";
    private List<String> linesFromFile = new ArrayList<>();

    public void readFile(String[] args) throws IOException {
        try {
            LineIterator lineIterator = new LineIterator(new FileReader("./task2/src/test/resources/" + (args[0])));
            while (lineIterator.hasNext()) {
                linesFromFile.add(lineIterator.nextLine());
            }
            lineIterator.close();
        } catch (Exception e) {
            System.out.println("File not found");
        }
        try {
            if (!args[1].isEmpty()) {
                outPutFileName = args[1];
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        parsingLines(linesFromFile);
    }

    private void parsingLines(List<String> linesFromFile) throws IOException {
        Figure figure = null;
        int[] side = new int[0];

        try {
            side = new int[linesFromFile.size() - 1];

            for (int i = 1; i < linesFromFile.size(); i++) {
                side[i - 1] = Integer.parseInt(linesFromFile.get(i));
            }
            side[0] = Integer.parseInt(linesFromFile.get(1));
        } catch (Exception e) {
            System.out.println("Data is incorrect");
        }

        try {
            if (linesFromFile.get(0).equals("CIRCLE")) {
                figure = new Circle(side);
                figure.printName();
            } else if (linesFromFile.get(0).equals("SQUARE")) {
                figure = new Square(side);
                figure.printName();
            } else if (linesFromFile.get(0).equals("RECTANGLE")) {
                figure = new Rectangle(side);
                figure.printName();
            } else {
                System.out.println("Figure is not CIRCLE, SQUARE, RECTANGLE");
            }
        } catch (Exception e) {

        }
        writeData(figure);
    }

    private void writeData(Figure figure) throws IOException {
        if (outPutFileName.equals("")) {
            for (String s : figure.getDescription()) {
                System.out.println(s);
            }
        } else {
            Writer printWriter = new PrintWriter(new File("./task2/src/test/resources/" + outPutFileName));
            for (String s : figure.getDescription()) {
                System.out.println(s);
                printWriter.write(s+"\n");
            }
            printWriter.flush();
            printWriter.close();
        }
    }
}
