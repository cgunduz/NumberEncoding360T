package com.cemgunduz.t360;

import com.cemgunduz.t360.encoder.Encoder;
import com.cemgunduz.utils.IoUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cgunduz on 3/11/14.
 */
public class App {

    // Test for the Encoder
    // Reads the input and creates an output file to write the answers to
    public static void main(String args[]) throws IOException {

        Encoder encoder = new Encoder();

        BufferedReader br = new BufferedReader(new FileReader("input.txt"));

        File file = new File("output.txt");
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        int caseNo = 0;
        String sCurrentLine;
        long time = System.currentTimeMillis();
        while ((sCurrentLine = br.readLine()) != null) {

            caseNo++;
            if(caseNo % 10 == 0)
                System.out.println(caseNo + " cases finished");

            List<String> resultSet = encoder.encodePhoneNumber(sCurrentLine);
            for(String result : resultSet)
                bw.write(sCurrentLine + ": " + result + "\n");
        }

        System.out.println("Execution took : " + (System.currentTimeMillis() - time) + " milli seconds");

        bw.close();
        br.close();
    }
}
