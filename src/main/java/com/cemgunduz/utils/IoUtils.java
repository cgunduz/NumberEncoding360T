package com.cemgunduz.utils;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cgunduz on 3/11/14.
 */
public class IoUtils {

    public static List<List<String>> mapFileToStringList(String path) {

        List<String> lines = new ArrayList<String>();

        try{

            BufferedReader br = new BufferedReader(new FileReader(path));

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
                lines.add(sCurrentLine);

            br.close();
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
}
