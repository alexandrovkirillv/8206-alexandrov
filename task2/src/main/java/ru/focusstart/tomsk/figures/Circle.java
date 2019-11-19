package ru.focusstart.tomsk.figures;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Circle implements Figure {
    private int[] side;
    private static final float PI = 3.14f;
    private List<String> result = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("00.##");

    public Circle(int[] side) {
        this.side = side;
    }

    @Override
    public List<String> getDescription() {
        return result;
    }

    @Override
    public void printName() {
        result.add("Type of figure: Circle");
        printSquare();
    }

    @Override
    public void printSquare() {
        result.add("Square: " + df.format(PI * (side[0]) * (side[0])));
        printPerimeter();
    }

    @Override
    public void printPerimeter() {
        result.add("Perimeter: " + df.format(2 * PI * (side[0])));
        printSide();
    }

    @Override
    public void printSide() {
       result.add("Radius: " + side[0]);
       printLongestLineInFigure();
    }

    @Override
    public void printLongestLineInFigure() {
        result.add("Diameter: " + side[0] * 2);
    }


}
