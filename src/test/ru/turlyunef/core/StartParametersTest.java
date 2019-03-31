package ru.turlyunef.core;

import junit.framework.TestCase;

public class StartParametersTest extends TestCase {
    public void testReadParameters1() {
        Parameters startParameters = new StartParameters();
        String[] args = {"-2", "-s", "-d", "out.txt", "in1.txt", "in2.txt", "0", "fe"};
        startParameters.readParameters(args);
        assertTrue(startParameters.getFileTypeIsCharacters());
        assertTrue(startParameters.getSortingTypeIsDecrease());
        assertTrue(startParameters.getCheckParameters());
        assertEquals(startParameters.getOutputFileName(), "out.txt");
        assertSame(startParameters.getInputFileNames()[0], "in1.txt");
        assertSame(startParameters.getInputFileNames()[1], "in2.txt");
        assertEquals(startParameters.getInputFileNames().length, 2);
    }
    public void testReadParameters2() {
        Parameters startParameters = new StartParameters();
        String[] args = {"-2", "-i", "-a", "out.txt", "in1.txt", "0", "fe"};
        startParameters.readParameters(args);
        assertFalse(startParameters.getFileTypeIsCharacters());
        assertFalse(startParameters.getSortingTypeIsDecrease());
        assertTrue(startParameters.getCheckParameters());
        assertEquals(startParameters.getOutputFileName(), "out.txt");
        assertSame(startParameters.getInputFileNames()[0], "in1.txt");
        assertEquals(startParameters.getInputFileNames().length, 1);
    }
    public void testReadParameters3() {
        Parameters startParameters = new StartParameters();
        String[] args = {"-2", "-i", "-a", "out.txt"};
        startParameters.readParameters(args);
        assertFalse(startParameters.getCheckParameters());
    }
}