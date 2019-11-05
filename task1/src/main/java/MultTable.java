import java.util.InputMismatchException;
import java.util.Scanner;

public class MultTable {

    public static void main(String[] args) {


        int sizeArray = scanSizeOfTable();

        int[][] multTableArray = fillTheTable(sizeArray);

        printTable(multTableArray, sizeArray);


    }

    private static int scanSizeOfTable() {

        Scanner scanner = new Scanner(System.in);
        int sizeArray = 0;
        do {
            try {
                System.out.println("Enter size of multiplication table (from 1 to 32)");
                sizeArray = scanner.nextInt();
            } catch (InputMismatchException e) {

                System.out.println("Please enter integer");
                System.exit(1);
            }
        }
        while ((sizeArray > 32) | (sizeArray < 1));
        return sizeArray;
    }

    private static int[][] fillTheTable(int sizeArray) {

        int[][] multTableArray = new int[sizeArray][sizeArray];

        for (int i = 0; i < sizeArray; i++) {
            for (int j = 0; j < sizeArray; j++) {
                multTableArray[i][j] = (i + 1) * (j + 1);
            }

        }
        return multTableArray;
    }

    private static void printTable(int[][] multTableArray, int sizeArray) {

        String tableFormatter = "";
        String lineSplitter = "";

        if (sizeArray > 0 & sizeArray < 4) {
            tableFormatter = "%1s";
            lineSplitter = "-";

        } else if (sizeArray >= 4 & sizeArray < 10) {
            tableFormatter = "%2s";
            lineSplitter = "--";
        } else if (sizeArray >= 10 & sizeArray < 32) {
            tableFormatter = "%3s";
            lineSplitter = "---";
        } else if (sizeArray == 32) {
            tableFormatter = "%4s";
            lineSplitter = "----";
        }


        for (int i = 0; i < sizeArray; i++) {

            System.out.println("");
            for (int j = 0; j < sizeArray; j++) {

                System.out.printf(tableFormatter, multTableArray[i][j]);
                if (j < sizeArray - 1) {
                    System.out.printf("%1s", "|");
                }

            }

            System.out.println("");
            for (int j = 0; j < sizeArray; j++) {

                if (i < sizeArray - 1) {
                    System.out.print(lineSplitter);
                    if (j < sizeArray - 1) {
                        System.out.print("+");
                        ;
                    }
                }

            }

        }
    }
}


