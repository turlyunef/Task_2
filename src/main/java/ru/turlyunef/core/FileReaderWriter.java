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

    public static void mergeTwoArray(String fileName1, String fileName2, String OutFileName, boolean sortingTypeIsDecrease, boolean fileTypeIsCharacters) {

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
                        appendStrToFile(strLine2, OutFileName);
                        strLine2 = br2.readLine();
                        if (strLine2 == null) break;
                    }
                    break;
                }
                if (strLine2 == null) {
                    while (true) {
                        appendStrToFile(strLine1, OutFileName);
                        strLine1 = br1.readLine();
                        if (strLine1 == null) break;
                    }
                    break;
                } else if (fileTypeIsCharacters) { //Work with characters


                    char aChar = strLine1.charAt(0);
                    char bChar = strLine2.charAt(0);

                    if (((int) aChar <= (int) bChar) ^ sortingTypeIsDecrease) {
                        appendStrToFile(strLine1, OutFileName);
                        strLine1 = br1.readLine();
                    } else {
                        appendStrToFile(strLine2, OutFileName);
                        strLine2 = br2.readLine();
                    }

                } else { //Work with numbers
                    if ((parseInt(strLine1) <= parseInt(strLine2)) ^ sortingTypeIsDecrease) {
                        appendStrToFile(strLine1, OutFileName);
                        strLine1 = br1.readLine();
                    } else {
                        appendStrToFile(strLine2, OutFileName);
                        strLine2 = br2.readLine();
                    }

                }

            }
        } catch (IOException e) {
            log.error("File read error");
        }
    }
}
