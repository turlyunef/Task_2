package ru.turlyunef.Merge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.core.FileReaderWriter;
import ru.turlyunef.core.Parameters;
import ru.turlyunef.core.SortStrategy;

import java.io.File;
import java.util.ArrayList;

public class MergeSortHandler implements SortStrategy {
    private static Logger log = LoggerFactory.getLogger(MergeSortHandler.class);
    Sort mergeSorter = new Sort();
    Parameters parameters;

    public MergeSortHandler(Parameters parameters){
        this.parameters = parameters;
        mergeSorter.parameters = parameters;
    }

    public String sortFile(String fileName) {
        ArrayList<String> list = new ArrayList<String>();
        list = FileReaderWriter.convertFileToArrayList(fileName);
        String[] array = list.toArray(new String[list.size()]);
        String tempSortedFileName = "Sorted_" + fileName;
        FileReaderWriter.writeArrayToFile(mergeSorter.doSort(array), tempSortedFileName);
        return tempSortedFileName;

    }

    public String[] sortFiles(String[] fileNames) {
        String[] tempSortedFilesNames = new String[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            tempSortedFilesNames[i] = sortFile(fileNames[i]);
            log.debug("file " + fileNames[i] + " has been sorted");
        }
        return tempSortedFilesNames;
    }

    public void makeOutFile(){
        mergeAllFiles(sortFiles(parameters.getInFiles()), parameters.getOutFile());
        deleteSortedInputFiles(parameters.getInFiles()); //Delete temp sorted files
    }


    public void mergeAllFiles(String[] inputFileNames, String outputFileNames) {
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
                log.debug("mergeTwoFilesToOutFile(" + inputFileNames[j] + " " + inputFileNames[j + 1] + " to " + tempOutFileNames[i]);
                mergeSorter.mergeTwoFilesToOutFile(inputFileNames[j], inputFileNames[j + 1], tempOutFileNames[i]);
            }
            if ((inputFileNames.length % 2) == 1) {
                tempOutFileNames[mergeCounter - 1] = inputFileNames[inputFileNames.length - 1];
                log.debug("(inputFileNames.length % 2) == 1");
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
