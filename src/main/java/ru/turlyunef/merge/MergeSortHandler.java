package ru.turlyunef.merge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.core.FileChanger;
import ru.turlyunef.core.Parameters;
import ru.turlyunef.core.SortStrategy;

import java.io.File;
import java.util.ArrayList;

public class MergeSortHandler implements SortStrategy {
    private static Logger log = LoggerFactory.getLogger(MergeSortHandler.class);
    Sort mergeSorter = new Sort();
    private Parameters parameters;

    public MergeSortHandler(Parameters parameters) {
        this.parameters = parameters;
        mergeSorter.setParameters(parameters);
    }

    public static void deleteTempSortedInputFiles(String[] fileNames) {
        try {
            for (String x : fileNames
            ) {
                File file = new File("Sorted_" + x);
                if (file.delete()) {
                    log.debug("Sorted_" + x + " has been deleted");
                } else
                    log.debug("Sorted_" + x + " has NOT been deleted");
            }
        } catch (NullPointerException e) {
            log.debug("Sorted input files do not exist");
        }
    }

    public String sortFile(String fileName) {
        ArrayList<String> list = FileChanger.convertFileToArrayList(fileName);
        String[] array = list.toArray(new String[list.size()]);
        String tempSortedFileName = "Sorted_" + fileName;
        FileChanger.writeArrayToFile(mergeSorter.doSort(array), tempSortedFileName);

        return tempSortedFileName;
    }

    public String[] sortFiles(String[] fileNames) {
        String[] tempSortedFilesNames = new String[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            tempSortedFilesNames[i] = sortFile(fileNames[i]);
            log.debug("File " + fileNames[i] + " has been sorted");
        }
        return tempSortedFilesNames;
    }

    public void makeOutFile() {
        mergeAllFiles(sortFiles(parameters.getInputFileNames()), parameters.getOutputFileName());
        deleteTempSortedInputFiles(parameters.getInputFileNames());
    }

    public void mergeAllFiles(String[] inputFileNames, String outputFileNames) {
        if (inputFileNames.length == 1) {
            File file = new File(inputFileNames[0]);
            File file2 = new File(outputFileNames);
            file.renameTo(file2);
            log.debug("File " + inputFileNames[0] + " has been renamed to " + outputFileNames);
            log.info("Sorting was successful!");
            return;
        } else {
            int mergeCounter = inputFileNames.length / 2 + (inputFileNames.length % 2);
            log.debug("MergeCounter = " + mergeCounter);
            String[] tempOutFileNames = new String[mergeCounter];
            int j = 0;
            for (int i = 0; i < inputFileNames.length / 2; i++, j += 2) {
                log.debug("i=" + i + " j=" + j);
                tempOutFileNames[i] = "tempFile" + mergeCounter + "" + i + ".txt";
                log.debug("MergeTwoFilesToOutFile(" + inputFileNames[j] + " " + inputFileNames[j + 1] + " to " + tempOutFileNames[i]);
                mergeSorter.mergeTwoFilesToOutFile(inputFileNames[j], inputFileNames[j + 1], tempOutFileNames[i]);
            }
            if ((inputFileNames.length % 2) == 1) {
                tempOutFileNames[mergeCounter - 1] = inputFileNames[inputFileNames.length - 1];
                log.debug("(InputFileNames.length % 2) == 1");
            }
            mergeAllFiles(tempOutFileNames, outputFileNames);

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
}