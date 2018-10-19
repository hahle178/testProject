package com.chrtc.excelTest.excelTest.utils;

import com.chrtc.excelTest.excelTest.domain.BcpMessage;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.*;

public class TxtUtil {
    /**
     * 五位自增序列号
     */
    private static int sn = 10001;
    private static Logger logger = Logger.getLogger("excelfile");
    public synchronized int getNextSN() {
        return ++sn;
    }

    public int getCurrentSN() {
        return sn;
    }

    private List<Map<String, Object>> ListByFile = new LinkedList<>();

    public List<Map<String, Object>> readFile(File file,int Column) {
        BufferedReader reader = null;
        //用于查重的参数
        RepeatUtil repeatUtil=new RepeatUtil();
        //默认为不重复;
        Boolean hashBoolean=false;
        //错位判断参数,默认为不错位
        Boolean isEmpty=false;
        List<Map<String, Object>> dataList = new LinkedList<>();
        String fileName=file.getName();
        String content="";
        String AllContent="";
        try {
            if (file.isDirectory()) {
                readFile(file,Column);
            } else {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                //读取file中的内容
                while ((tempString = reader.readLine()) != null) {
                    //保存每一行
                    content="";
                    AllContent="";
                    JSONObject jsonObject = JSONObject.fromObject(tempString);
                    Iterator it = jsonObject.keys();
                    Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
                    //如果不重复就执行
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        content=jsonObject.getString(key);
                        if (content==null|content.equals("")){
                            content="空";
                            isEmpty=true;
                        }
                        AllContent=AllContent+"\t"+content;
                        dataMap.put(key, content);
                    }
                    line++;
                    hashBoolean=repeatUtil.fileRepeat(AllContent);
                    if(jsonObject.size()!=Column){
                        logger.fatal("文件名为"+fileName+"的第"+line+"行数据出现列数缺失,数据内容为:"+AllContent);
                        continue;
                    }
                    if(!isEmpty){
                        if (!hashBoolean){
                            dataList.add(dataMap);
                        }else{
                            logger.fatal("文件名为"+fileName+"的第"+line+"行数据出现重复,数据内容为:"+AllContent);
                        }
                    }else{
                        logger.fatal("文件名为"+fileName+"的第"+line+"行数据出现错位,数据内容为:"+AllContent);
                        isEmpty=false;
                    }


                }

                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return dataList;
    }


    public Map<String, Object> readTitle(File file) {
        BufferedReader reader = null;
        List<Map<String, Object>> titleList = new LinkedList<>();
        Map<String, Object> titleMap = new LinkedHashMap<String, Object>();
        try {
            if (file.isDirectory()) {
                readTitle(file);
            } else {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                //读取file中的内容

                while ((tempString = reader.readLine()) != null) {

                    //保存每一行
                    JSONArray titleArray = JSONArray.fromObject(tempString);
                    if (titleArray.size() > 0) {
                        for (int i = 0; i < titleArray.size(); i++) {
                            JSONObject job = titleArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                            titleMap.put((String) job.get("dataCode"), job.get("dataName"));
                        }
                    }
                    //titleList.add(titleMap);
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return titleMap;
    }

    //生成xml文件
    public void createIndexXml(String xmlPath, LinkedList bcpMessages) throws IOException {
        File file = new File(xmlPath);//写入文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // List<XmlFILE> bcp_index = xmlFILEService.findByNo("BCP_INDEX");
        //List<XmlFILE> BCP_DESCRIBE = xmlFILEService.findByNo("BCP_DESCRIBE");
        // List<XmlFILE> BCP_DESCRIBE_INFO = xmlFILEService.findByNo("BCP_DESCRIBE_INFO");
        //   List<XmlFILE> BCP_DATA = xmlFILEService.findByNo("BCP_DATA");
        // List<XmlFILE> BCP_DATA_STRUCTURE = xmlFILEService.findByNo("BCP_DATA_STRUCTURE");


        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("MESSAGE");
        Element DATASET = root.addElement("DATASET").addAttribute("name", "COMMON_010017").addAttribute("rmk", "数据文件索引信息");
        Element data = DATASET.addElement("DATA");

        Element DATASET1 = data.addElement("DATASET");
        DATASET1.addAttribute("name", "COMMON_010013").addAttribute("rmk", "BCP格式文件描述信息");
        //Element DATA1 = DATASET1.addElement("DATA");

        //for (Object bcpMessage:bcpMessages) {
        for (int i = 0; i < bcpMessages.size(); i++) {
            BcpMessage bcpMessage1 = (BcpMessage) bcpMessages.get(i);
            /*for (XmlFILE xmlFILE : BCP_DESCRIBE_INFO) {
                DATA1.addElement("ITEM").addAttribute("key", xmlFILE.getKey1()).addAttribute("val", xmlFILE.getVal()).addAttribute("rmk", xmlFILE.getRmk());
            }*/
            Element DATA1 = DATASET1.addElement("DATA");

            DATA1.addElement("ITEM").addAttribute("key", "10A0024").addAttribute("val", "\\t").addAttribute("rmk", "列分隔符(缺少值时默认为制表符\\t)");
            DATA1.addElement("ITEM").addAttribute("key", "10A0025").addAttribute("val", "\\n").addAttribute("rmk", "行分隔符(缺少值时默认为制表符\\n)");
            DATA1.addElement("ITEM").addAttribute("key", "01A0004").addAttribute("val", "SOURCE_0002").addAttribute("rmk", "数据集代码");
            DATA1.addElement("ITEM").addAttribute("key", "02E0016").addAttribute("val", "").addAttribute("rmk", "数据来源");
            DATA1.addElement("ITEM").addAttribute("key", "07B0013").addAttribute("val", "").addAttribute("rmk", "安全专用产品厂家组织机构代码");
            DATA1.addElement("ITEM").addAttribute("key", "06A0008").addAttribute("val", "").addAttribute("rmk", "数据采集地");
            DATA1.addElement("ITEM").addAttribute("key", "10A0027").addAttribute("val", "1").addAttribute("rmk", "数据起始行，可选项。不填写默认为第一行");
            DATA1.addElement("ITEM").addAttribute("key", "10A0028").addAttribute("val", "UTF-8").addAttribute("rmk", "可选项,默认为UTF-8。BCP格式文件编码格式（采用不带格式的编码方式。如：UTF-8无BOM）");

            Element DATASET2 = DATA1.addElement("DATASET").addAttribute("name", "COMMON_010014").addAttribute("rmk", "BCP数据文件信息");
            //生成BCP数据文件信息
            //for (Object bcpMessage : bcpMessages) {
            // BcpMessage bcpMessage1 = (BcpMessage) bcpMessage;
            Element DATA2 = DATASET2.addElement("DATA");
            DATA2.addElement("ITEM").addAttribute("key", "H040003").addAttribute("val", bcpMessage1.getPath()).addAttribute("rmk", "文件路径");
            DATA2.addElement("ITEM").addAttribute("key", "H010020").addAttribute("val", bcpMessage1.getName()).addAttribute("rmk", "文件名");
            DATA2.addElement("ITEM").addAttribute("key", "I010034").addAttribute("val", bcpMessage1.getCount() + "").addAttribute("rmk", "记录行数");
            // }

            //生成BCP格式文件数据结构
            Element DATASET3 = DATA1.addElement("DATASET").addAttribute("name", "COMMON_010015").addAttribute("rmk", "BCP格式文件数据结构");

            /*for (Map<String, Object> stringObjectMap : bankListByExcel1) {
                Element DATA3 = DATASET3.addElement("DATA");
                for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
                    DATA3.addElement("ITEM").addAttribute("key", "H040003").addAttribute("eng", entry.getKey()).addAttribute("chn", "ss").addAttribute("rmk", "rr");
                }
            }*/
            Element DATA3 = DATASET3.addElement("DATA");
            //Map<String, Object> stringObjectMap = ListByFile.get(i);
            Map<String, Object> stringObjectMap = ListByFile.get(i);
            for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
                DATA3.addElement("ITEM").addAttribute("key", "H040003").addAttribute("eng", entry.getKey()).addAttribute("chn", (String) entry.getValue()).addAttribute("rmk", "rr");
            }
        }

        OutputFormat format = OutputFormat.createPrettyPrint();  //转换成字符串
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
        writer.write(document);
        writer.close();
    }

    public void createZIP(String bcpPath, String zipPath) throws Exception {
        String dataSendSysIden = "101";//数据发送方系统标识
        String dataSendDevIden = "102";//数据发送方机构标识
        String dataReceSysIden = "103";//数据接收方系统标识
        String dataReceDevIden = "104";//数据接收方机构标识
        long currentTimeMillisZIP = System.currentTimeMillis();//绝对秒数

        int nextSNZIP = getNextSN();//五位自增序列号,避重序列号

        String zipName = dataSendSysIden + "_" + dataSendDevIden + "_" + dataReceSysIden + "_" + dataReceDevIden + "_" + currentTimeMillisZIP + "_" + nextSNZIP + ".zip";
        CompressedFileUtil.compressedFile(bcpPath, zipPath, zipName);

    }


}
