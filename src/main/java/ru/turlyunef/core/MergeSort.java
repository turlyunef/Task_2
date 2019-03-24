package ru.turlyunef.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class MergeSort {
    private static Logger log = LoggerFactory.getLogger(Handler.class);

    //Merge two string arrays "a" and "b" into one array "result"
    public static String[] doMerge(String[] a, String[] b, boolean sortingTypeIsDecrease, boolean fileTypeIsCharacters) {
        String[] result = new String[a.length + b.length];
        for (int i = 0, j = 0, k = 0; k < a.length + b.length; k++) {
            if (i == a.length) {
                result[k] = b[j];
                j++;
            } else if (j == b.length) {
                result[k] = a[i];
                i++;
            } else if (fileTypeIsCharacters) { //Work with characters

                if ((a[i].length() > 1) | ((b[j].length() > 1))) {
                    log.warn("The data in the line contains more than one character, sorting done by the first character in the string");
                }
                char aChar = a[i].charAt(0);
                char bChar = b[j].charAt(0);

                if (((int) aChar) >= ((int) bChar) ^ sortingTypeIsDecrease) {
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

                if ((parseInt(a[i]) >= parseInt(b[j])) ^ sortingTypeIsDecrease) {
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
    public static String[] doSort(String[] array, boolean sortingTypeIsDecrease, boolean fileTypeIsCharacters) {
        if (array.length < 2) return array;
        else {
            int rightA = (int) array.length / 2 - 1; //right border of the first subarray "A"
            int leftB = rightA + 1; //left border of the second subarray "B"
            String A[] = new String[rightA + 1];
            String B[] = new String[array.length - A.length];
            A = Arrays.copyOfRange(array, 0, rightA + 1);
            B = Arrays.copyOfRange(array, rightA + 1, array.length);

            return doMerge(doSort(A, sortingTypeIsDecrease, fileTypeIsCharacters), doSort(B, sortingTypeIsDecrease, fileTypeIsCharacters), sortingTypeIsDecrease, fileTypeIsCharacters);
        }
    }
}


