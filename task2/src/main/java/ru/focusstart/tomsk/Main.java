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

    public static void main(String[] args) throws Exception {

        List<String> linesFromFile = readFile(args);
        Figure figure = createFigure(parsingLines(linesFromFile), linesFromFile);
        writeData(figure);
    }

    private static List<String> readFile(String[] args) {
        List<String> linesFromFile = new ArrayList<>();

        if (args.length < 1) {
            System.out.println("No arguments");
            throw new ArrayIndexOutOfBoundsException();
        }

        try (LineIterator lineIterator = new LineIterator(new FileReader(args[0]))) {
            while (lineIterator.hasNext()) {
                linesFromFile.add(lineIterator.nextLine());
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
        if (args.length > 1) {
            outPutFileName = args[1];
        }

        return linesFromFile;
    }


    private static int[] parsingLines(List<String> linesFromFile) {
        if (linesFromFile.isEmpty() | linesFromFile.size() < 2) {
            System.out.println("File is empty or no parameters");
            throw new NullPointerException();
        }

        int[] parameters = new int[linesFromFile.size() - 1];

        try {
            for (int i = 1; i < linesFromFile.size(); i++) {
                parameters[i - 1] = Integer.parseInt(linesFromFile.get(i));
            }
        } catch (Exception e) {
            System.out.println("Data is incorrect");
        }

        if (linesFromFile.get(0).equals("RECTANGLE") & parameters.length < 2) {
            System.out.println("Only one parameter for Rectangle");
            throw new ArrayIndexOutOfBoundsException();
        }

        return parameters;
    }

    private static Figure createFigure(int[] parameters, List<String> linesFromFile) throws Exception {
        Figure figure;

        if (linesFromFile.get(0).equals("CIRCLE")) {
            figure = new Circle(parameters);
        } else if (linesFromFile.get(0).equals("SQUARE")) {
            figure = new Square(parameters);
        } else if (linesFromFile.get(0).equals("RECTANGLE")) {
            figure = new Rectangle(parameters);
        } else {
            System.out.println("Figure is not CIRCLE, SQUARE, RECTANGLE");
            throw new Exception();
        }

        return figure;
    }

    private static void writeData(Figure figure) throws IOException {
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
    }
}

