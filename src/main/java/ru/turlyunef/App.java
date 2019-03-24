package ru.turlyunef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.core.Handler;
import ru.turlyunef.core.StartParameters;

public class App {
    private static Logger log = LoggerFactory.getLogger(App.class);

    public App() {
    }

    public static void main(String[] args) {
        log.info("Start the program");
        StartParameters arguments = new StartParameters();//class for storing the entered program parameters
        arguments.readParameters(args); // reading the entered program parameters
        if (arguments.getCheckParameters())
            Handler.mergeAllFiles(Handler.sortFiles(arguments.getInFiles(), arguments.getSortingTypeIsDecrease(),
                    arguments.getFileTypeIsCharacters()), arguments.getOutFile(), arguments.getSortingTypeIsDecrease(), arguments.getFileTypeIsCharacters()); //sorting all files to Output file
        Handler.deleteSortedInputFiles(arguments.getInFiles()); //Delete temp sorted files
        log.info("The program is completed");


    }
}