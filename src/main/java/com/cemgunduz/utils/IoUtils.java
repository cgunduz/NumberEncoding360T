package com.cemgunduz.utils;

import com.sun.deploy.util.StringUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cgunduz on 3/11/14.
 */
public class IoUtils {

    public static List<List<String>> mapFileToStringList(String path) {

        List<String> lines = null;
        try{

            FileInputStream inputStream = new FileInputStream(path);
            lines = IOUtils.readLines(inputStream);
            inputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        List<List<String>> stringListByLine = new ArrayList<List<String>>();

        for(String line : lines)
        {
            List<String> stringList = Arrays.asList(StringUtils.splitString(line, " "));
            stringListByLine.add(stringList);
        }

        return stringListByLine;
    }

    public static void mapStringListToFile(List<String> stringList)
    {
        mapStringListToFile(stringList, "output.txt");
    }

    public static void mapStringListToFile(List<String> stringList, String filename)
    {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(String line : stringList)
        {
            try {
                line += "\n";
                IOUtils.write(line, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
