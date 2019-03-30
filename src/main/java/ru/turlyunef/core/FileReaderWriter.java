package ru.turlyunef.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class FileReaderWriter {
    private static Logger log = LoggerFactory.getLogger(FileReaderWriter.class);

    public static void writeArrayToFile(String[] array, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String x : array
            ) {
                writer.append(x);
                writer.append("\r\n");
                writer.flush();

            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }


    public static ArrayList<String> convertFileToArrayList(String fileName) {
        ArrayList<String> listFromFile = new ArrayList<String>();
        try (FileInputStream fstream = new FileInputStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
            String strLine;
            while (true) {
                strLine = br.readLine();
                if (strLine != null) listFromFile.add(strLine);
                else break;
            }
        } catch (IOException e) {
            log.error("File read error");
        }
        return listFromFile;
    }

    public static void appendStrToFile(String str, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(str);
            writer.append("\r\n");
            writer.flush();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }


}
