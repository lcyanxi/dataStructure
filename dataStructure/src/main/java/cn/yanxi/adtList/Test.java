package cn.yanxi.adtList;

import java.util.Scanner;

/**
 * Created by lcyanxi on 16-12-6.
 */
public class Test {

   private static  ListDao  listDao =new ListDaoImpl();
   private static Scanner scanner;


    public static void main(String args[]){
        scanner=new Scanner(System.in);

        while (true){
            try {
                showMenu();
                int i=scanner.nextInt();
                switch (i){
                    case 0:
                        gitSize();
                        break;
                    case 1:
                        saveData();
                        break;
                    case 2:
                        getAllData();
                        break;
                    case 3:
                        insert();
                        break;
                    case 4:
                          remove();
                          break;
                    case 5:
                        replace();
                        break;
                    case 6:
                        contains();
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println("对不起，你输入的指令有问题！！");
                        break;
                }
            }catch (Exception e ){
                System.out.println("对不起，我出错了！！！");
                e.printStackTrace();
            }
        }

    }
    public static  void showMenu(){
        System.out.println(
                "************JAVA版本的线性表***********************\n" +
                        "  您可以根据如下提示进行操作：\n" +
                        "按数字“0”查看当前线性表的元素个数\n" +
                        "按数字“1”初始化一组数据\n" +
                        "按数字“2”获取当前线性表所有元素\n"+
                        "按数字“3”添加指定位置\n" +
                        "按数字“4”进行删除操作\n" +
                        "按数字“5”进行替换操作\n" +
                        "按数字“6”判断是否已经存在一个元素\n" +
                        "按数字“7”获得指定位置的元素\n"+
                        "按数字“8”退出程序\n"+
                "***************************************************"
        );
    }

    public static void saveData(){
        System.out.println("请输入你要保存数据的个数：");
        int i=scanner.nextInt();
         System.out.println("请输入你要保存的数据：");
         Object[] objects=new Object[i];
         for (int j=0;j<i;j++){
             int data=scanner.nextInt();
             objects[j]=data;
         }
         listDao.saveData(i,objects);
        System.out.println("save success!!!");
    }

    public static void remove(){
        System.out.println("请输入你要删除元素的位子：");
        int i=scanner.nextInt();
        listDao.remove(i);
    }

    public static void replace(){
        System.out.println("请输入你要替换元素的位置：");
        int i=scanner.nextInt();
        System.out.println("请输入你要替换的元素：");
        int data=scanner.nextInt();
        listDao.replace(i,data);
    }

    public static  void gitSize() {
        System.out.println("当前线性表元素个数为："+listDao.getSeize());
        return;
    }

    public static void insert(){
        System.out.println("请输入你要插入的位子：");
        int i=scanner.nextInt();
        System.out.println("请输入你要插入的数据：");
        int data=scanner.nextInt();
        listDao.insert(i,data);
        System.out.println("添加成功！！！");
    }

    public static void getAllData(){
        Object[] objects=listDao.gitAll();
        System.out.print("当前线性表元素为：");
        for (int i=0;i<objects.length;i++){
            System.out.print(" "+objects[i]);
        }
        System.out.println("\n");
    }

    public static void contains(){
        System.out.println("请输入你要判断的元素：");
        int data=scanner.nextInt();
        System.out.println(listDao.contains(data));
    }

}
