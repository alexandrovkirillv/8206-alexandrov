package ru.focusstart.tomsk;

import org.apache.commons.io.LineIterator;
import ru.focusstart.tomsk.figures.Circle;
import ru.focusstart.tomsk.figures.Figure;
import ru.focusstart.tomsk.figures.Rectangle;
import ru.focusstart.tomsk.figures.Square;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String outPutFileName = "";

    public static void main(String[] args) throws IOException {

        List<String> linesFromFile = readFile(args);
        Figure figure = createFigure(parsingLines(linesFromFile), linesFromFile);
        try {
            writeData(figure);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Wrong parameters");
        }

    }

    private static List<String> readFile(String[] args) {

        List<String> linesFromFile = new ArrayList<>();

        try (LineIterator lineIterator = new LineIterator(new FileReader(args[0]))) {
            while (lineIterator.hasNext()) {
                linesFromFile.add(lineIterator.nextLine());
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
        try {
            outPutFileName = args[1];
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        return linesFromFile;
    }


    private static int[] parsingLines(List<String> linesFromFile) {

        int[] parameters = new int[0];

        try {
            parameters = new int[linesFromFile.size() - 1];

            for (int i = 1; i < linesFromFile.size(); i++) {
                parameters[i - 1] = Integer.parseInt(linesFromFile.get(i));
            }

        } catch (Exception e) {
            System.out.println("Data is incorrect");
        }
        return parameters;
    }

    private static Figure createFigure(int[] parameters, List<String> linesFromFile) {
        Figure figure = null;

        try {
            if (linesFromFile.get(0).equals("CIRCLE")) {
                figure = new Circle(parameters);
                figure.getName();
            } else if (linesFromFile.get(0).equals("SQUARE")) {
                figure = new Square(parameters);
                figure.getName();
            } else if (linesFromFile.get(0).equals("RECTANGLE")) {
                figure = new Rectangle(parameters);
                figure.getName();
            } else {
                System.out.println("Figure is not CIRCLE, SQUARE, RECTANGLE");
            }
        } catch (Exception e) {

        }

        return figure;
    }

    private static void writeData(Figure figure) throws IOException {

        try {
            if (outPutFileName.equals("")) {
                for (String s : figure.getDescription()) {
                    System.out.println(s);
                }
            } else {
                try (Writer printWriter = new PrintWriter(new File(outPutFileName))) {
                    for (String s : figure.getDescription()) {
                        System.out.println(s);
                        printWriter.write(s + "\n");
                    }
                }
            }
        } catch (NullPointerException e) {

        }
    }
}

