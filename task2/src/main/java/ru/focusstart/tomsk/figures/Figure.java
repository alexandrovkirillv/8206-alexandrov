package ru.focusstart.tomsk.figures;

import java.util.List;

public interface Figure {
    void printName();

    void printSquare();

    void printPerimeter();

    void printSide();

    void printLongestLineInFigure();

    List<String> getResult();
}
