package ru.focusstart.tomsk.figures;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.PI;

public class Circle implements Figure {
    private int[] parameters;
    private DecimalFormat df = new DecimalFormat("00.##");

    public Circle(int[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public List<String> getDescription() {
        List<String> description = new ArrayList<>();

        description.add(getName());
        description.add("Square: " + df.format(getSquare()));
        description.add("Perimeter: " + df.format(getPerimeter()));
        description.add("Radius: " + parameters[0]);
        description.add("Diameter: " + getLongestLineInFigure());

        return description;
    }

    @Override
    public String getName() {
        return "Type of figure: Circle";
    }

    @Override
    public double getSquare() {
        return PI * (parameters[0]) * (parameters[0]);
    }

    @Override
    public double getPerimeter() {
        return 2 * PI * (parameters[0]);
    }

    @Override
    public int[] getParameters() {
        return parameters;
    }

    @Override
    public double getLongestLineInFigure() {
        return parameters[0] * 2;
    }
}
