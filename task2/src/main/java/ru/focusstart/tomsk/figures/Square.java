package ru.focusstart.tomsk.figures;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Square implements Figure {
    private int[] parameters;

    private DecimalFormat df = new DecimalFormat("00.##");

    @Override
    public List<String> getDescription() {
        List<String> description = new ArrayList<>();

        description.add(getName());
        description.add("Square: " + getSquare());
        description.add("Perimeter: " + getPerimeter());
        description.add("Side: " + parameters[0]);
        description.add("Diagonal: " + df.format(getLongestLineInFigure()));

        return description;
    }

    public Square(int[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String getName() {
        return "Type of figure: Square";
    }

    @Override
    public double getSquare() {
        return parameters[0] * parameters[0];
    }

    @Override
    public double getPerimeter() {
        return parameters[0] * 4;
    }

    @Override
    public int[] getParameters() {
        return parameters;
    }

    @Override
    public double getLongestLineInFigure() {
        return parameters[0] * Math.sqrt(2);
    }

}
