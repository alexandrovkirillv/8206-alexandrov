package ru.focusstart.tomsk.figures;

import java.util.List;

public interface Figure {
    String printName();

    float printSquare();

    float printPerimeter();

    int [] printSide();

    double printLongestLineInFigure();

    List<String> getDescription();
}
