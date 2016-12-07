package cn.yanxi.adtList;

/**
 * Created by lcyanxi on 16-12-6.
 */
public class ListDaoImpl implements  ListDao{
    private Object[] elements;//数据元素数组
    private int size;   //线性表中数据元素个数
    private final  int  LEN =10;//数组的默认大小


    public ListDaoImpl() {
        System.out.println("初始化参数！！");
        this.elements = new Object[LEN];
        this.size = 0;
    }

    public int getSeize() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean contains(Object object) {
       if(indexOf(object)!=-1){
           return true;
       }
        return false;
    }

    public int indexOf(Object object) {
        for(int i=0;i<size;i++){
            if (object.equals(elements[i]))
                return i;
        }
        return -1;
    }

    public void insert(int i, Object object) throws OutOfBoundaryException {
        if (i<0||i>size){
           throw new  OutOfBoundaryException("对不起，指定的下标'"+i+"'越界");
        }
        if (size>=elements.length){
            expendSpace();
        }
        for(int j=size-1;j>i;j--){
            elements[j]=elements[j-1];
        }
        elements[i]=object;
        size++;
    }

    public void saveData(int i ,Object[] objects) {
        if (i>elements.length){
            expendSpace();
        }
       elements=objects;
        size=i;
    }

    public boolean insertBefore(Object p, Object object) {
        int i =indexOf(p);
        if(i<0) return false;
        insert(i,object);
        return true;
    }

    public boolean insertAfter(Object p, Object object) {
        int i=indexOf(p);
        if (i<0) return  false;
        insert(i+1,object);
        return true;
    }

    public Object remove(int i) throws OutOfBoundaryException {
        if (i<0||i>size){
            throw new OutOfBoundaryException("对不起，指定的下标'"+i+"'越界");
        }
        Object object=elements[i];
        for(int j=i;j<size-1;j++){
            elements[j]=elements[j+1];
        }
        elements[size-1]=null;
        size=size-1;
      return object;
    }

    public Object replace(int i, Object object) throws OutOfBoundaryException {
        if (i>size||i<0){
            throw new OutOfBoundaryException("对不起，指定的下标'"+i+"'越界");
        }
        Object obj=elements[i];
        elements[i]=object;
        return obj;
    }

    public Object get(int i) throws OutOfBoundaryException {
        if (i>size||i<0){
            throw new OutOfBoundaryException("对不起，指定的下标'"+i+"'越界");
        }
        Object object=elements[i];
        return object;
    }

    public Object[] gitAll() {
        Object[] objects=new Object[size];
        for(int i=0;i<size;i++){
            objects[i]=elements[i];
        }
        return objects;


    }

    //当线性表空间不足时扩容
    public void   expendSpace(){
        Object[] objects=new Object[elements.length*2];
        for(int i=0;i<elements.length;i++){
            objects[i]=elements[i];
            elements=objects;
        }
    }
}
