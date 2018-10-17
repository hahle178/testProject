package com.chrtc.excelTest.excelTest.utils;
import java.io.*;
public class LogUtil {

        public void ExcelLogWriting(String fileName) {
            try {
                //Whatever the file path is.
                String path="E://ExcelLogs/"+fileName+".txt";
                File statText = new File(path);
                FileOutputStream is = new FileOutputStream(statText);
                OutputStreamWriter osw = new OutputStreamWriter(is);
                Writer w = new BufferedWriter(osw);


               ((BufferedWriter) w).newLine();
                w.close();
            } catch (IOException e) {
                System.err.println("Problem writing to the file statsTest.txt");
            }
        }

    }






