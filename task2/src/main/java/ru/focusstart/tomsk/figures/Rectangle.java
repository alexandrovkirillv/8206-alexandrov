package ru.focusstart.tomsk.figures;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Rectangle implements Figure {
    private int[] side;
    private List<String> result = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("00.##");

    public Rectangle(int[] side) {
        this.side = side;
    }

    @Override
    public List<String> getResult() {
        return result;
    }

    @Override
    public void printName() {
        result.add("Type of figure: Rectangle");
        printSquare();
    }

    @Override
    public void printSquare() {
        try {
            int longSide = side[1];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println();
            System.out.println("Print 2 sides for Rectangle!");
        }
        result.add("Square: " + side[0] * side[1]);
        printPerimeter();
    }

    @Override
    public void printPerimeter() {
        result.add("Perimeter: " + (side[0] + side[1]) * 2);
        printSide();
    }

    @Override
    public void printSide() {
        if (side[0] >= side[1]) {
            int tempI = side[0];
            side[0] = side[1];
            side[1] = tempI;
        }
        result.add("Short side: " + side[0]);
        result.add("Long side: " + side[1]);
        printLongestLineInFigure();
    }

    @Override
    public void printLongestLineInFigure() {
        result.add("Diagonal: " +  df.format((float) Math.sqrt(side[0] * side[0] + side[1] * side[1])));
    }
}
