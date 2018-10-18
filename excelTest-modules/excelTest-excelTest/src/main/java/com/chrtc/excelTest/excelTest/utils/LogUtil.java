package com.chrtc.excelTest.excelTest.utils;
import java.io.*;
import java.text.SimpleDateFormat;

public class LogUtil {
    /**
     * 日志写出测试demo
     * @param fileName
     */
    public void ExcelLogWriting(String fileName,int row,String key,int cod) {
            try {
                //Whatever the file path is.
                String path="E://ExcelLogs/"+fileName+".log";
                File statText = new File(path);
                FileOutputStream is = new FileOutputStream(statText,true);
                OutputStreamWriter osw = new OutputStreamWriter(is);
                Writer w = new BufferedWriter(osw);
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                switch (cod){
                    case 0:{
                        w.write(dateFormat+":文件名为"+fileName+"的第"+row+"行数据出现重复"+key);
                        break;
                    }
                    case 1:{
                        w.write(dateFormat+":文件名为"+fileName+"的第"+row+"行数据出现错位"+key);
                        break;
                    }
                }
               ((BufferedWriter) w).newLine();
                w.close();
            } catch (IOException e) {
                System.err.println("Problem writing to the file statsTest.txt");
            }
        }

    }






