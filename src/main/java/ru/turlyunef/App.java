package ru.turlyunef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.Merge.MergeSortHandler;
import ru.turlyunef.core.Parameters;
import ru.turlyunef.core.SortStrategy;
import ru.turlyunef.core.StartParameters;

public class App {
    private static Logger log = LoggerFactory.getLogger(App.class);

    public App() {
    }

    public static void main(String[] args) {
        log.info("Start the program");
        Parameters arguments = new StartParameters();//class for storing the entered program parameters

        arguments.readParameters(args); // reading the entered program parameters

        if (arguments.getCheckParameters()){
            SortStrategy handler = new MergeSortHandler(arguments);
            handler.makeOutFile(); //sorting all files to Output file
        }

        log.info("The program is completed");


    }
}