package ru.focusstart.tomsk;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MultTableTest {

    private final BufferedReader reader = mock(BufferedReader.class);
    private final MultTable mockMultTable = mock(MultTable.class);
    private final PrintStream printStream = mock(PrintStream.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFillTheTable() {
        int[][] expected = new int[2][2];
        expected[0][0] = 1;
        expected[0][1] = 2;
        expected[1][0] = 2;
        expected[1][1] = 4;

        assertArrayEquals(MultTable.fillTheTable(2), expected);
        // when(mockMultTable.fillTheTable(2)).thenReturn(expected);   - почему этот тест не поехал?
    }

    @Test
    public void testScanSizeOfTable() throws IOException {

        when(reader.readLine()).thenReturn("13");
        assertEquals(13, MultTable.scanSizeOfTable(reader));
        verify(reader).readLine();

    }

    @Test
    public void testPrintGreeting() {

        MultTable.printGreeting(printStream);
        verify(printStream).print("Enter size of multiplication table (from 1 to 32): ");

    }

    @Test
    public void testReadSizeWithIllegalSize() throws IOException {

        when(reader.readLine()).thenReturn("s");
        thrown.expect(IllegalArgumentException.class);
        mockMultTable.scanSizeOfTable(reader);


    }


}