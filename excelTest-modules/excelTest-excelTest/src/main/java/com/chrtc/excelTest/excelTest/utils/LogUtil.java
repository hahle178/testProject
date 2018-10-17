package com.chrtc.excelTest.excelTest.utils;
import java.io.*;
public class LogUtil {
    /**
     * 日志写出测试demo
     * @param fileName
     */
    public void ExcelLogWriting(String fileName,int row,String key) {
            try {
                //Whatever the file path is.
                String path="E://ExcelLogs/"+fileName+".txt";
                File statText = new File(path);
                FileOutputStream is = new FileOutputStream(statText,true);
                OutputStreamWriter osw = new OutputStreamWriter(is);
                Writer w = new BufferedWriter(osw);
                w.write("文件名为"+fileName+"的第"+row+"行数据出现重复"+key);
               ((BufferedWriter) w).newLine();
                w.close();
            } catch (IOException e) {
                System.err.println("Problem writing to the file statsTest.txt");
            }
        }

    }






