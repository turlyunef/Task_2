package ru.turlyunef.merge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.core.FileChanger;
import ru.turlyunef.core.Parameters;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class Sort {
    private static Logger log = LoggerFactory.getLogger(MergeSortHandler.class);
    private Parameters parameters;

    public Sort() {
    }

    public Sort(Parameters parameters) {
        this.parameters = parameters;
    }

    //merge two string arrays "a" and "b" into one array "result"
    public String[] doMerge(String[] a, String[] b) {
        String[] result = new String[a.length + b.length];
        for (int i = 0, j = 0, k = 0; k < a.length + b.length; k++) {
            if (i == a.length) {
                result[k] = b[j];
                j++;
            } else if (j == b.length) {
                result[k] = a[i];
                i++;
            } else if (parameters.getFileTypeIsCharacters()) { //Work with characters
                if ((a[i].length() > 1) | ((b[j].length() > 1))) {
                    log.warn("The data in the line contains more than one character, sorting done by the first character in the string");
                }
                char aChar = a[i].charAt(0);
                char bChar = b[j].charAt(0);
                if (((int) aChar) >= ((int) bChar) ^ parameters.getSortingTypeIsDecrease()) {
                    result[k] = b[j];
                    j++;
                } else {
                    result[k] = a[i];
                    i++;
                }
            } else { //Work with numbers
                try {
                    parseInt(a[i]);
                } catch (NumberFormatException e) {
                    log.warn("The data in file is not a number, " + e.getMessage() + ". It is changed to 0");
                    a[i] = "0";
                }
                try {
                    parseInt(b[j]);
                } catch (NumberFormatException e) {
                    log.warn("The data in file is not a number, " + e.getMessage() + ". It is changed to 0");
                    b[j] = "0";
                }
                if ((parseInt(a[i]) >= parseInt(b[j])) ^ parameters.getSortingTypeIsDecrease()) {
                    result[k] = b[j];
                    j++;
                } else {
                    result[k] = a[i];
                    i++;
                }
            }
        }
        return result;
    }

    //Sorting an unsorted array. It used the method doMerge for sorting
    public String[] doSort(String[] array) {
        if (array.length < 2) return array;
        else {
            int rightA = array.length / 2 - 1; //right border of the first subarray "A"
            String[] A;
            String[] B;
            A = Arrays.copyOfRange(array, 0, rightA + 1);
            B = Arrays.copyOfRange(array, rightA + 1, array.length);

            return doMerge(doSort(A), doSort(B));
        }
    }

    public void mergeTwoFilesToOutFile(String fileName1, String fileName2, String OutFileName) {
        try (FileInputStream fstream1 = new FileInputStream(fileName1);
             FileInputStream fstream2 = new FileInputStream(fileName2);
             BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
             BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2))) {
            String strLine1, strLine2;
            strLine1 = br1.readLine();
            strLine2 = br2.readLine();
            while (true) {
                if (strLine1 == null) {
                    while (true) {
                        FileChanger.appendStrToFile(strLine2, OutFileName);
                        strLine2 = br2.readLine();
                        if (strLine2 == null) {
                            break;
                        }
                    }
                    break;
                }
                if (strLine2 == null) {
                    while (true) {
                        FileChanger.appendStrToFile(strLine1, OutFileName);
                        strLine1 = br1.readLine();
                        if (strLine1 == null) {
                            break;
                        }
                    }
                    break;
                } else if (parameters.getFileTypeIsCharacters()) { //Work with characters
                    char aChar = strLine1.charAt(0);
                    char bChar = strLine2.charAt(0);
                    if (((int) aChar <= (int) bChar) ^ parameters.getSortingTypeIsDecrease()) {
                        FileChanger.appendStrToFile(strLine1, OutFileName);
                        strLine1 = br1.readLine();
                    } else {
                        FileChanger.appendStrToFile(strLine2, OutFileName);
                        strLine2 = br2.readLine();
                    }
                } else { //Work with numbers
                    if ((parseInt(strLine1) <= parseInt(strLine2)) ^ parameters.getSortingTypeIsDecrease()) {
                        FileChanger.appendStrToFile(strLine1, OutFileName);
                        strLine1 = br1.readLine();
                    } else {
                        FileChanger.appendStrToFile(strLine2, OutFileName);
                        strLine2 = br2.readLine();
                    }
                }
            }
        } catch (IOException e) {
            log.error("File read error");
        }
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }
}