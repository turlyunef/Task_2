package ru.turlyunef.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.core.MergeExceptions.MissingParametersException;

import java.io.File;

public class StartParameters {
    private boolean fileTypeIsCharacters; //Type of the data for sorting data in the files
    private boolean sortingTypeIsDecrease = false; //The default sorting type is to addition, if user enter decrease, then sortingTypeIsDecrease = true and change algorithm of program
    private String OutFile = null; //Name of the output file
    private String[] InFiles; //Name of the input files
    private int txtFilesCounter = 0; //Counter of the input files in console for checking entering
    private boolean checkParameters = false;

    private static Logger log = LoggerFactory.getLogger(StartParameters.class);

    public void readParameters(String[] args) {
        try {
            checkTxtFileNamesCounter(args);
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
                    if (OutFile == null) {
                        OutFile = x;
                    } else {
                        InFiles[j] = x;
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

    private void checkTxtFileNamesCounter(String[] args) throws MissingParametersException {
        for (String x : args) {
            if (x.lastIndexOf(".txt") != -1) txtFilesCounter++;
        }
        if (txtFilesCounter < 2) {
            log.error("File names is missed");
            throw new MissingParametersException();
        } else {
            InFiles = new String[txtFilesCounter - 1];
        }
    }


    private void checkArguments() throws MissingParametersException {
        if (OutFile == null) {
            log.error("Output file name is missed");
            throw new MissingParametersException();
        }

        if (InFiles.length < 1) {
            log.error("Input files name is missed");
            throw new MissingParametersException();
        } else {
            setCheckParameters(true);
            File file = new File(getOutFile());
            if (file.delete()) ;
        }

    }

    public void infoArguments() {
        log.debug("fileTypeIsCharacters = " + getFileTypeIsCharacters());
        log.debug("SortingTypeIsDecrease = " + getSortingTypeIsDecrease());
        log.debug("Output file name = " + getOutFile());
        try {
            for (String y : getInFiles()
            ) {
                log.debug("input file name = " + y);
            }
        } catch (NullPointerException e) {
        }
    }

    public boolean getFileTypeIsCharacters() {
        return fileTypeIsCharacters;
    }

    public boolean getSortingTypeIsDecrease() {
        return sortingTypeIsDecrease;
    }

    public String getOutFile() {
        return OutFile;
    }

    public String[] getInFiles() {
        return InFiles;
    }

    public boolean getCheckParameters() {
        return checkParameters;
    }

    public void setCheckParameters(boolean checkParameters) {
        this.checkParameters = checkParameters;
    }
}
