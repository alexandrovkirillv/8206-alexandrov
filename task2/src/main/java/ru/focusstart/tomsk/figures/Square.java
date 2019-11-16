package ru.focusstart.tomsk.figures;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Square implements Figure {
    private int[] side;
    private List<String> result = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("00.##");

    @Override
    public List<String> getResult() {
        return result;
    }

    public Square(int[] side) {
        this.side = side;
    }

    @Override
    public void printName() {
        result.add("Type of figure: Square");
        printSquare();
    }

    @Override
    public void printSquare() {
        result.add("Square: " + side[0] * side[0]);
        printPerimeter();
    }

    @Override
    public void printPerimeter() {
        result.add("Perimeter: " + side[0] * 4);
        printSide();
    }

    @Override
    public void printSide() {
        result.add("Side: " + side[0]);
        printLongestLineInFigure();
    }

    @Override
    public void printLongestLineInFigure() {
        result.add("Diagonal: " +   df.format((float) (side[0] * Math.sqrt(2))));
    }

}
