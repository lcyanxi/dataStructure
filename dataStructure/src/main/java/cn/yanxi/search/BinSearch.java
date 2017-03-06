package cn.yanxi.search;

/**
 * Created by lcyanxi on 17-3-1.
 * 这半查找适合于不经常变动并且查找频繁的有序表
 */
public class BinSearch {
    public static boolean binSearch(int [] s,int low,int high,int key){
        while(low<=high){
            int mid=(low+high)/2;
            if(s[mid]==key) return true;
            else  if(s[mid]<key) low=mid+1;
            else high=mid-1;
        }
     return false;
    }

}
