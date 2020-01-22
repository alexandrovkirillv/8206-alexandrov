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
    private static String inPutFileName = "";
    private static String figureType = "";

    public static void main(String[] args) throws Exception {
        try {
            checkingArguments(args);
            List<String> linesFromFile = readFile(inPutFileName);
            Figure figure = createFigure(parsingLines(linesFromFile));
            writeData(figure);
        } catch (ApplicationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkingArguments(String[] args) throws ApplicationException {
        if (args.length < 1) {
            throw new ApplicationException("No arguments");
        }

        inPutFileName = args[0];

        if (args.length > 1) {
            outPutFileName = args[1];
        }
    }

    private static List<String> readFile(String inPutFileName) throws ApplicationException {
        List<String> linesFromFile = new ArrayList<>();

        try (LineIterator lineIterator = new LineIterator(new FileReader(inPutFileName))) {
            while (lineIterator.hasNext()) {
                linesFromFile.add(lineIterator.nextLine());
            }
        } catch (IOException e) {
            throw new ApplicationException("File not found");
        }

        return linesFromFile;
    }

    private static int[] parsingLines(List<String> linesFromFile) throws ApplicationException {
        if (linesFromFile.isEmpty() | linesFromFile.size() < 2) {
            throw new ApplicationException("File is empty or no parameters");
        }

        int[] parameters = new int[linesFromFile.size() - 1];
        figureType = linesFromFile.get(0);

        try {
            for (int i = 1; i < linesFromFile.size(); i++) {
                parameters[i - 1] = Integer.parseInt(linesFromFile.get(i));
            }
        } catch (Exception e) {
            throw new ApplicationException("Data is incorrect");
        }
        if (linesFromFile.get(0).equals("RECTANGLE") & parameters.length < 2) {
            throw new ApplicationException("Only one parameter for Rectangle");
        }

        return parameters;
    }



    private static Figure createFigure(int[] parameters) throws Exception {
     Figure figure;

        if (figureType.equals("CIRCLE")) {
            figure = new Circle(parameters);
        } else if (figureType.equals("SQUARE")) {
            figure = new Square(parameters);
        } else if (figureType.equals("RECTANGLE")) {
            figure = new Rectangle(parameters);
        } else {
            throw new ApplicationException("Figure is not CIRCLE, SQUARE, RECTANGLE");
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

class ApplicationException extends Exception {
    public ApplicationException(String message) {
        super(message);
    }

}

