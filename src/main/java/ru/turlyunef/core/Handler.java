package ru.turlyunef.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

public class Handler {
    private static Logger log = LoggerFactory.getLogger(Handler.class);

    public static String sortFile(String fileName, boolean sortingTypeIsDecrease, boolean fileTypeIsCharacters) {
        ArrayList<String> list = new ArrayList<String>();
        list = FileReaderWriter.convertFileToArrayList(fileName);
        String[] array = list.toArray(new String[list.size()]);
        String tempSortedFileName = "Sorted_" + fileName;
        FileReaderWriter.writeArrayToFile(MergeSort.doSort(array, sortingTypeIsDecrease, fileTypeIsCharacters), tempSortedFileName);
        return tempSortedFileName;

    }

    public static String[] sortFiles(String[] fileNames, boolean sortingTypeIsDecrease, boolean fileTypeIsCharacters) {
        String[] tempSortedFilesNames = new String[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            tempSortedFilesNames[i] = sortFile(fileNames[i], sortingTypeIsDecrease, fileTypeIsCharacters);
            log.debug("file " + fileNames[i] + " has been sorted");
        }
        return tempSortedFilesNames;
    }

    public static void mergeAllFiles(String[] inputFileNames, String outputFileNames, boolean sortingTypeIsDecrease, boolean fileTypeIsCharacters) {
        if (inputFileNames.length == 1) {
            File file = new File(inputFileNames[0]);
            File file2 = new File(outputFileNames);
            file.renameTo(file2);
            log.debug("file " + inputFileNames[0] + " has been renamed to " + outputFileNames);
            log.info("sorting was successful!");
            return;
        } else {
            int mergeCounter = (int) inputFileNames.length / 2 + (inputFileNames.length % 2);
            log.debug("mergeCounter = " + mergeCounter);
            String[] tempOutFileNames = new String[mergeCounter];
            int j = 0;
            for (int i = 0; i < (int) inputFileNames.length / 2; i++, j += 2) {
                log.debug("i=" + i + " j=" + j);
                tempOutFileNames[i] = "tempFile" + mergeCounter + "" + i + ".txt";
                log.debug("mergeTwoArray(" + inputFileNames[j] + " " + inputFileNames[j + 1] + " to " + tempOutFileNames[i]);
                FileReaderWriter.mergeTwoArray(inputFileNames[j], inputFileNames[j + 1], tempOutFileNames[i], sortingTypeIsDecrease, fileTypeIsCharacters);
            }
            if ((inputFileNames.length % 2) == 1) {
                tempOutFileNames[mergeCounter - 1] = inputFileNames[inputFileNames.length - 1];
                log.debug("(inputFileNames.length % 2) == 1");
            }
            mergeAllFiles(tempOutFileNames, outputFileNames, sortingTypeIsDecrease, fileTypeIsCharacters);

            //Delete temp files:
            for (String x : tempOutFileNames
            ) {
                File file = new File(x);
                if (file.delete()) {
                    log.debug(x + " has been deleted");
                } else
                    log.debug(x + " has NOT been deleted");
            }
        }
    }

    public static void deleteSortedInputFiles(String[] fileNames) {
        try {
            for (String x : fileNames
            ) {
                File file = new File("Sorted_" + x);
                if (file.delete()) {
                    log.debug("Sorted_" + x + " has been deleted");
                } else
                    log.debug("Sorted_" + x + " has NOT been deleted");
            }
        }catch (NullPointerException e){
            log.debug("Sorted input files do not exist");
        }
    }
}
