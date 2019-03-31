package ru.turlyunef.merge;

import junit.framework.TestCase;
import ru.turlyunef.core.Parameters;

import java.util.Arrays;

public class SortTest extends TestCase {
    public class FakeParameters implements Parameters {
        private boolean fileTypeIsCharacters;
        private boolean sortingTypeIsDecrease;
        private String OutFileName;
        private String[] InFileNames;
        private boolean checkParameters;

        public FakeParameters(boolean fileTypeIsCharacters, boolean sortingTypeIsDecrease, String OutFileName, String[] InFileNames) {
            this.fileTypeIsCharacters = fileTypeIsCharacters;
            this.sortingTypeIsDecrease = sortingTypeIsDecrease;
            this.OutFileName = OutFileName;
            this.InFileNames = new String[InFileNames.length];
            this.InFileNames = InFileNames;
            this.checkParameters = true;
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
            return OutFileName;
        }

        @Override
        public String[] getInputFileNames() {
            return InFileNames;
        }

        @Override
        public boolean getCheckParameters() {
            return checkParameters;
        }

        @Override
        public void readParameters(String[] args) {

        }
    }

    public void testDoMerge1() {
        String[] inFileNames = {"in1.txt", "in2.txt"};
        Parameters parameters = new FakeParameters(false, false, "Out.txt", inFileNames);

        Sort sorter = new Sort(parameters);
        String[] testArray1 = {"5", "2", "1", "8", "7", "3", "4", "9", "10", "6"};
        String[] testArray2 = {"19", "16", "20", "11", "13", "12", "14", "15", "18", "17"};
        String[] expectedResult = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        String[] actualResult = sorter.doMerge(sorter.doSort(testArray1), sorter.doSort(testArray2));
        assertTrue(Arrays.equals(actualResult, expectedResult));
    }

    public void testDoMerge2() {
        String[] inFileNames = {"in1.txt", "in2.txt"};
        Parameters parameters = new FakeParameters(true, true, "Out.txt", inFileNames);
        Sort sorter = new Sort(parameters);
        String[] testArray1 = {"d", "e", "a", "f", "g", "b", "c"};
        String[] testArray2 = {"h"};
        String[] expectedResult = {"h", "g", "f", "e", "d", "c", "b", "a"};
        String[] actualResult = sorter.doMerge(sorter.doSort(testArray1), sorter.doSort(testArray2));
        assertTrue(Arrays.equals(actualResult, expectedResult));
    }
}