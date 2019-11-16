package ru.focusstart.tomsk;

import ru.focusstart.tomsk.file.WorkingWithFile;

public class Main {
    public static void main(String[] args) {


        WorkingWithFile readingFile = new WorkingWithFile();

        try {
            readingFile.readFile(args);
        }catch (Exception e){

        }


    }
}
