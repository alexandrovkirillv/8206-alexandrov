package ru.focusstart.tomsk.figures;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Rectangle implements Figure {
    private final int[] side;
    private DecimalFormat df = new DecimalFormat("00.##");

    public Rectangle(int[] side) {
        this.side = side;
    }

    @Override
    public List<String> getDescription() {
        List<String> description = new ArrayList<>();

        description.add(printName());
        description.add("Square: " + printSquare());
        description.add("Perimeter: " + printPerimeter());
        description.add("Short side: " + printSide()[0]);
        description.add("Long side: " + printSide()[1]);
        description.add("Diagonal: " + printLongestLineInFigure());
        
        return description;
    }

    @Override
    public String printName() {
        return "Type of figure: Rectangle";
    }

    @Override
    public float printSquare() {
        try {
            int longSide = side[1];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println();
            System.out.println("Print 2 sides for Rectangle!");
        }
        return side[0]*side[1];
    }

    @Override
    public float printPerimeter() {
        return (side[0] + side[1]) * 2;
    }

    @Override
    public int[] printSide() {
        if (side[0] >= side[1]) {
            int tempI = side[0];
            side[0] = side[1];
            side[1] = tempI;
        }
        return side;

    }

    @Override
    public double printLongestLineInFigure() {
        return  Math.sqrt(side[0] * side[0] + side[1] * side[1]);
    }


}
