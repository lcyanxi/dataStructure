package com.douguo.ndc.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2019/1/22
 */
public class HbaseJavaClientUtil {

    private static final Object LOCK=new Object();
    private static final String QUORUM = "dg-hd-1001,dg-hd-1002,dg-hd-1003";
    private static final String CLIENTPORT = "2181";
    private static Configuration conf = null;
    private static Connection conn = null;

   private HbaseJavaClientUtil(){}

   public static Connection getInstance(){
       if (conn==null){
           synchronized (LOCK){
               if (null==conn){
                   conf =  HBaseConfiguration.create();
                   conf.set("hbase.zookeeper.quorum", QUORUM);
                   conf.set("hbase.zookeeper.property.clientPort", CLIENTPORT);
                   ExecutorService pool = Executors.newFixedThreadPool(100);
                   try {
                      conn= ConnectionFactory.createConnection(conf,pool);
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }
           }
       }
       return conn;
   }


    public static void main(String[] args) throws Exception {

        // 创建表
        String tableName = "blog2";
        //String[] family = {"article", "author"};
        String[] family = {"info"};
         creatTable(tableName, family);

        // 为表添加数据

        String[] column1 = {"title", "content", "tag"};
        String[] value1 = {
            "Head First HBase",
            "HBase is the Hadoop database. Use it when you need random, realtime read/write access to your Big Data.",
            "Hadoop,HBase,NoSQL"};
        String[] column2 = {"name", "nickname"};
        String[] value2 = {"nicholas", "lee"};
       // addData("rowkey1", "blog2", column1, value1, column2, value2);
       // addData("rowkey2", "blog2", column1, value1, column2, value2);
       // addData("rowkey3", "blog2", column1, value1, column2, value2);

      //  System.out.println(getResultScann("blog2"));
        // 遍历查询
        //System.out.println(getResultScann("blog2", "rowkey1", "rowkey3"));
        // 根据row key范围遍历查询
        //  getResultScann("blog2", "rowkey4", "rowkey5");

        // 查询
        //System.out.println(getResult("blog2", "rowkey1"));

        // 查询某一列的值
        // getResultByColumn("blog2", "rowkey1", "author", "name");

        // 更新列
        //updateTable("blog2", "rowkey1", "author", "name", "lichang");

        // 查询某一列的值
        //getResultByColumn("blog2", "rowkey1", "author", "name");

        // 查询某列的多版本
        //getResultByVersion("blog2", "rowkey1", "author", "name");

        // 删除一列
        //deleteColumn("blog2", "rowkey1", "author", "nickname");

        // 删除所有列
        //deleteAllColumn("blog2", "rowkey1");

        // 删除表
        // deleteTable("blog2");
    }


    /**
     * 创建表
     * @param tableName 表名
     * @param family 列族列表
     * @throws Exception
     */
    public static void creatTable(String tableName, String[] family)
        throws Exception {
        Admin admin=conn.getAdmin();
        HTableDescriptor desc = new HTableDescriptor(tableName);
        for (String s:family) {
            desc.addFamily(new HColumnDescriptor(s));
        }
        if (admin.tableExists(TableName.valueOf(tableName))) {
            System.out.println("table Exists!");
            System.exit(0);
        } else {
            admin.createTable(desc);
            System.out.println("create table Success!");
        }
    }



    /**
     * 为表添加数据（适合知道有多少列族的固定表）
     * @param rowKey 行健
     * @param tableName 表名
     * @param column1 第一个列族列表
     * @param value1 第一个列的值的列表
     */
    public static void addData(String rowKey, String tableName, String[] column1, String[] value1) {
        // 设置rowkey
        Put put = new Put(Bytes.toBytes(rowKey));
        // HTabel负责跟记录相关的操作如增删改查等
        try{
            Table table = HbaseJavaClientUtil.getInstance().getTable(TableName.valueOf(tableName));
            // 获取表// 获取所有的列族
            HColumnDescriptor[] columnFamilies = table.getTableDescriptor().getColumnFamilies();

            for (HColumnDescriptor columnDescriptor:columnFamilies) {
                // 获取列族名
                String familyName = columnDescriptor.getNameAsString();
                // article列族put数据
                if (familyName.equals("info")) {
                    for (int j = 0; j < column1.length; j++) {
                        put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(column1[j]), Bytes.toBytes(value1[j]));
                    }
                }
            }
            table.put(put);
            System.out.println("add data Success!");
        }catch (Exception e){
            System.out.println("add data error!");
            e.printStackTrace();
        }

    }


    /**
     * 根据rwokey查询
     *
     * @param tableName 表名
     * @param rowKey    行健
     * @return  data
     * @throws IOException 异常
     */
    public static List getResult(String tableName, String rowKey) throws IOException {
        Get get = new Get(Bytes.toBytes(rowKey));
        // 获取表
        Table table = HbaseJavaClientUtil.getInstance().getTable(TableName.valueOf(tableName));
        Result result = table.get(get);
        return dataUtil(result);
    }

    /**
     * 遍历查询hbase表
     * @param tableName 表名
     * @throws IOException 异常
     */
    public static List getResultScann(String tableName) throws IOException {
        Scan scan = new Scan();
        ResultScanner rs = null;
        Table table = HbaseJavaClientUtil.getInstance().getTable(TableName.valueOf(tableName));
        List list=new ArrayList();
            rs = table.getScanner(scan);
            List rowList=new ArrayList();
            for (Result r : rs) {
                for (Cell kv : r.listCells()) {
                    Map<String,Object> map = new HashMap<String,Object>(16);
                    map.put("row", Bytes.toString(kv.getRowArray()));
                    map.put("family", Bytes.toString(kv.getFamilyArray()));
                    map.put("qualifier", Bytes.toString(kv.getQualifierArray()));
                    map.put("value", Bytes.toString(kv.getValueArray()));
                    map.put("Timestamp", kv.getTimestamp());
                    rowList.add(map);
                }
                list.add(rowList);
            }

        return list;
    }


    /**
     *  遍历查询hbase表
     * @param tableName
     * @param startRowkey 开始行健
     * @param stopRowkey  结束行健
     * @return
     * @throws IOException
     */
    public static List getResultScann(String tableName, String startRowkey, String stopRowkey) throws IOException {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(startRowkey));
        scan.setStopRow(Bytes.toBytes(stopRowkey));
        ResultScanner rs = null;
        Table table = HbaseJavaClientUtil.getInstance().getTable(TableName.valueOf(tableName));
        List list=new ArrayList();
            rs = table.getScanner(scan);
            for (Result r : rs) {
                list.add(dataUtil(r));
            }
        return list;
    }



    /**
     * 查询表中的某一列
     * @param tableName  表名
     * @param rowKey     行键
     * @param familyName 列族名
     * @param columnName 列名
     * @return
     */
    public static List<Map<String,Object>> getResultByColumn(String tableName, String rowKey, String familyName, String columnName) throws Exception{
        Table table= HbaseJavaClientUtil.getInstance().getTable(TableName.valueOf(tableName));
        try{
            Get get = new Get(Bytes.toBytes(rowKey));
            // 获取指定列族和列修饰符对应的列
            get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
            Result result = table.get(get);
            return dataUtil(result);
        }catch (Exception e){
            System.out.println("query data error!");
        }
        return null;

    }


    /**
     * 更新表中的某一列
     * @param tableName  表名
     * @param rowKey     行键
     * @param familyName 列族名
     * @param columnName 列名
     * @param value
     * @throws IOException
     */
    public static void updateTable(String tableName, String rowKey, String familyName, String columnName, String value)
        throws IOException {
        Table table = HbaseJavaClientUtil.getInstance().getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName),Bytes.toBytes(value));
        table.put(put);
        System.out.println("update table Success!");
    }

    /**
     * 查询某列数据的多个版本
     * @param tableName 表名
     * @param rowKey    行键
     * @param familyName 列族名
     * @param columnName 列名
     * @throws IOException
     */
    public static List getResultByVersion(String tableName, String rowKey, String familyName, String columnName) throws IOException {
        Table table = HbaseJavaClientUtil.getInstance().getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
        get.setMaxVersions(5);
        Result result = table.get(get);
        return dataUtil(result);
    }

    /**
     * 删除指定的列
     * @param tableName 表名
     * @param rowKey   行键
     * @param falilyName 列族名
     * @param columnName 列名
     * @throws IOException
     */
    public static void deleteColumn(String tableName, String rowKey,String falilyName, String columnName) throws IOException {
        Table table = HbaseJavaClientUtil.getInstance().getTable(TableName.valueOf(tableName));
        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
        deleteColumn.deleteColumns(Bytes.toBytes(falilyName),Bytes.toBytes(columnName));
        table.delete(deleteColumn);
        System.out.println(falilyName + ":" + columnName + "is deleted!");
    }


    /**
     *  删除所有列
     * @param tableName 表名
     * @param rowKey 行键
     * @throws IOException
     */
    public static void deleteAllColumn(String tableName, String rowKey)
        throws IOException {
        Table table = HbaseJavaClientUtil.getInstance().getTable(TableName.valueOf(tableName));
        Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
        table.delete(deleteAll);
        System.out.println("all columns are deleted!");
    }


    /**
     *  删除表
     * @param tableName 表名
     * @throws IOException
     */
    public static void deleteTable(String tableName) throws IOException {
        Admin admin= HbaseJavaClientUtil.getInstance().getAdmin();
        admin.disableTable(TableName.valueOf(tableName));
        admin.deleteTable(TableName.valueOf(tableName));
        System.out.println(tableName + "is deleted!");
    }

    private static List dataUtil(Result result){
        List list=new ArrayList();
        if (result.isEmpty()){
            return list;
        }

        for (Cell kv : result.listCells()) {
            Map<String,Object> map = new HashMap<>(16);
            map.put("family", Bytes.toString(kv.getFamilyArray()));
            map.put("qualifier", Bytes.toString(kv.getQualifierArray()));
            map.put("value", Bytes.toString(kv.getValueArray()));
            map.put("Timestamp", kv.getTimestamp());
            list.add(map);
        }
        return list;
    }
}
