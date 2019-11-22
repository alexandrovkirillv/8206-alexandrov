package ru.focusstart.tomsk.figures;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Rectangle implements Figure {
    private final int[] parameters;
    private DecimalFormat df = new DecimalFormat("00.##");

    public Rectangle(int[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public List<String> getDescription() {
        List<String> description = new ArrayList<>();

        description.add(getName());
        description.add("Square: " + getSquare());
        description.add("Perimeter: " + getPerimeter());
        description.add("Short side: " + getParameters()[0]);
        description.add("Long side: " + getParameters()[1]);
        description.add("Diagonal: " + getLongestLineInFigure());
        
        return description;
    }

    @Override
    public String getName() {
        return "Type of figure: Rectangle";
    }

    @Override
    public double getSquare() {
        return parameters[0]* parameters[1];
    }

    @Override
    public double getPerimeter() {
        return (parameters[0] + parameters[1]) * 2;
    }

    @Override
    public int[] getParameters() {
        if (parameters[0] >= parameters[1]) {
            int tempI = parameters[0];
            parameters[0] = parameters[1];
            parameters[1] = tempI;
        }
        return parameters;

    }

    @Override
    public double getLongestLineInFigure() {
        return  Math.sqrt(parameters[0] * parameters[0] + parameters[1] * parameters[1]);
    }


}
