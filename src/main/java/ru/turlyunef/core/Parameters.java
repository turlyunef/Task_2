package ru.turlyunef.core;

public interface Parameters {

    boolean getFileTypeIsCharacters();

    boolean getSortingTypeIsDecrease();

    String getOutputFileName();

    String[] getInputFileNames();

    boolean getCheckParameters();

    void readParameters(String[] args);
}