package ru.focusstart.tomsk.figures;

import java.util.List;

public interface Figure {
    String getName();

    double getSquare();

    double getPerimeter();

    int [] getParameters();

    double getLongestLineInFigure();

    List<String> getDescription();
}
