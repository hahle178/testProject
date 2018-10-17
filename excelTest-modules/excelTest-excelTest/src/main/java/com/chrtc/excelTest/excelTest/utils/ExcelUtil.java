package com.chrtc.excelTest.excelTest.utils;


import com.chrtc.excelTest.excelTest.domain.ExcelEntity;


import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {
    private static Logger logger = Logger.getLogger(ExcelUtil.class);

    /**
     * 读取excel文件
     *
     * @param in
     * @param
     * @return
     * @throws Exception
     */
    public static List<Map<String,Object>> getBankListByExcel(InputStream in, String extString,String fileName) throws Exception {
        Workbook work = null;
        InputStream is = FileMagic.prepareToCheckMagic(in);
        List<ExcelEntity> excelEntitys = new ArrayList<>();
        try {
            if(".xls".equals(extString)){
                work = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                work = new XSSFWorkbook(is);
            }else{
                work = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建Excel工作薄
         //work = getWorkbook(in);
       // work = new HSSFWorkbook(is);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        //创建哈希值算法的工具对象
        HashUtil hash=new HashUtil();
        //创建查重工具的对象
        RepeatUtil repeatUtil=new RepeatUtil();
        //创建日志工具的对象
        LogUtil log=new LogUtil();
        Sheet sheet = null;
        Row row = null;
        Row rowHead = null;
        Cell cell = null;
        String value = "";

        //用于存储字符串来转换为hash值
        String key="";
//        int hashcode=0;
//        Set<Integer> hashset =new HashSet<>();
        //用于判断是否重复,重复为true不重复为false;默认为不重复
        Boolean hashboolean=false;
        List<Map<String,Object>> dataList= new ArrayList<>();

        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            // 标题总列数
            rowHead = sheet.getRow(0);
            if(rowHead == null){
                continue;
            }

            int colNum = rowHead.getPhysicalNumberOfCells();
            String[] keyArray = new String[colNum];
            for (int k  = 0; k < colNum; k++) {
                keyArray[k] = getCellFormatValue(rowHead.getCell(k));
            }
            //遍历当前sheet中的所有行
            int lastRowNum = sheet.getLastRowNum();
            for (int j =1; j <= sheet.getLastRowNum(); j++) {
                Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
                key="";
                row = sheet.getRow(j);
                if (row == null ) {
                    continue;
                }

                int n = 0;
//                while(n<colNum){
//                    if(row.getCell(n)!=null){
//                        value=getCellFormatValue(row.getCell(n)).trim();
//                        key=key+value;
//                    }else{
//                        value=" ";
//                        key=key+value;
//                    }
//                    n++;
//                }
                //进行查重检查
//                hashcode=hash.toHash(key);
//                if(hashset.isEmpty()){
//                    hashset.add(hashcode);
//                }else {
//                        if( hashset.contains(hashcode)){
//                            hashboolean=true;
//                            }
//                         hashset.add(hashcode);
//                }

//                if (!hashboolean){
//                    n=0
                    int num=row.getLastCellNum();
                    //进行行数据判断,如果超出列的个数,输入日志进行记录
                    if (num<=colNum) {
                        while (n < colNum) {
                            //这里把列循环到Map
                            if (row.getCell(n) != null) {
                                value = getCellFormatValue(row.getCell(n)).trim();
                                dataMap.put(keyArray[n], value);
                            } else {
                                value = " ";
                                dataMap.put(keyArray[n], value);
                            }
                            key = key +"\t"+ value;
                            n++;
                        }
                        hashboolean = repeatUtil.fileRepeat(key);
                        value = "";
                        if (!hashboolean) {
                            dataList.add(dataMap);
                        } else {
                            //查重的日志处理
                            logger.fatal("文件名为"+fileName+"的第"+j+"行数据出现重复"+key);
                            log.ExcelLogWriting(fileName,j,key);
                            hashboolean = false;
                        }
//                }else{
//                    System.out.println("出现重复行");
//                    logger.info("出现重复行:"+key);
//                    hashboolean=false;
//                }


               /* ExcelEntity excelEntity = new ExcelEntity();
                //把每个单元格的值付给对象的对应属性
                if (row.getCell(0)!=null){
                    excelEntity.setCapturetime(String.valueOf(getCellValue(row.getCell(0))));
                }
                if (row.getCell(1)!=null){
                    excelEntity.setContent(String.valueOf(getCellValue(row.getCell(1))));
                }
                if (row.getCell(2)!=null){
                    excelEntity.setDatatype(String.valueOf(getCellValue(row.getCell(2))));
                }
                if (row.getCell(3)!=null){
                    excelEntity.setUrl(String.valueOf(getCellValue(row.getCell(3))));
                }
                //遍历所有的列(把每一行的内容存放到对象中)
                excelEntitys.add(excelEntity);*/
                    }else{
                        //将错位数据进行日志记录
                        while (n < num) {
                            //这里把列循环到Map
                            if (row.getCell(n) != null) {
                                value = getCellFormatValue(row.getCell(n)).trim();
                            } else {
                                value = " ";
                            }
                            key = key + "\t"+value;
                            n++;
                        }
                        //这里将key传到日志记录工具中进行写入
                        logger.fatal("文件名为"+fileName+"的第"+j+"行数据出现错位"+key);
                        log.ExcelLogWriting(fileName,j,key);
                    }
            }
        }
        return dataList;
    }


    /**
     *
     * @param inStr
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr) throws Exception {
        Workbook wb = null;
        wb = WorkbookFactory.create(inStr);
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }
    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private static String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        DecimalFormat df = new DecimalFormat("0");
                        String dfStr = df.format(cell.getNumericCellValue());
                        cellvalue = dfStr;
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }


}