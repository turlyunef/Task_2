package ru.turlyunef.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.core.mergeExceptions.MissingParametersException;

import java.io.File;

public class StartParameters implements Parameters {
    private static Logger log = LoggerFactory.getLogger(StartParameters.class);
    private boolean fileTypeIsCharacters;
    private boolean sortingTypeIsDecrease = false; //The default sorting type is to addition
    private String OutputFileName = null;
    private String[] InputFileNames;
    private int txtAllFilesCounter = 0;
    private boolean checkParameters = false;

    public void readParameters(String[] args) {
        try {
            checkTxtAllFileNamesCounter(args);
            int j = 0;
            for (String x : args) {
                if (x.equals("-i")) {
                    this.fileTypeIsCharacters = false;
                    log.debug("Data type assigned to numeric");
                }
                if (x.equals("-s")) {
                    this.fileTypeIsCharacters = true;
                    log.debug("Data type assigned to characters");
                }
                if (x.equals("-a")) {
                    this.sortingTypeIsDecrease = false;
                    log.debug("Sorting type assigned to addition");
                }
                if (x.equals("-d")) {
                    this.sortingTypeIsDecrease = true;
                    log.debug("Sorting type assigned to decrease");
                }
                if (x.lastIndexOf(".txt") != -1) {
                    if (OutputFileName == null) {
                        OutputFileName = x;
                    } else {
                        InputFileNames[j] = x;
                        j++;
                    }
                }
            }
            checkArguments();
            log.info("Entered program parameters is read from console and correct");
            infoArguments();
        } catch (MissingParametersException e) {
            log.error("Parameters should contain fileTypeIsCharacters (\"-i\" - for sorting numbers, -s - for sorting characters), output and input files with the extension .txt");
        }
    }

    private void checkTxtAllFileNamesCounter(String[] args) throws MissingParametersException {
        for (String x : args) {
            if (x.lastIndexOf(".txt") != -1) {
                txtAllFilesCounter++;
            }
        }
        if (txtAllFilesCounter < 2) {
            log.error("File names is missed");
            throw new MissingParametersException();
        } else {
            InputFileNames = new String[txtAllFilesCounter - 1];
        }
    }


    public void checkArguments() throws MissingParametersException {
        if (OutputFileName == null) {
            log.error("Output file name is missed");
            throw new MissingParametersException();
        }
        if (InputFileNames.length < 1) {
            log.error("Input files name is missed");
            throw new MissingParametersException();
        } else {
            setCheckParameters(true);
            File file = new File(getOutputFileName());
            file.delete();
        }
    }

    public void infoArguments() {
        log.debug("fileTypeIsCharacters = " + getFileTypeIsCharacters());
        log.debug("SortingTypeIsDecrease = " + getSortingTypeIsDecrease());
        log.debug("Output file name = " + getOutputFileName());
        try {
            for (String y : getInputFileNames()
            ) {
                log.debug("Input file name = " + y);
            }
        } catch (NullPointerException e) {
            log.debug("Input file name is not exist");
        }
    }

    @Override
    public boolean getFileTypeIsCharacters() {
        return fileTypeIsCharacters;
    }

    @Override
    public boolean getSortingTypeIsDecrease() {
        return sortingTypeIsDecrease;
    }

    @Override
    public String getOutputFileName() {
        return OutputFileName;
    }

    @Override
    public String[] getInputFileNames() {
        return InputFileNames;
    }

    @Override
    public boolean getCheckParameters() {
        return checkParameters;
    }

    public void setCheckParameters(boolean checkParameters) {
        this.checkParameters = checkParameters;
    }
}
