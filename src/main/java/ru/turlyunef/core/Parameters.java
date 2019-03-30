package ru.turlyunef.core;

import ru.turlyunef.core.MergeExceptions.MissingParametersException;

public interface Parameters {
    void readParameters(String[] args);

    void checkArguments() throws MissingParametersException;

    void infoArguments();

    boolean getFileTypeIsCharacters();

    boolean getSortingTypeIsDecrease();

    String getOutFile();

    String[] getInFiles();

    boolean getCheckParameters();


}
