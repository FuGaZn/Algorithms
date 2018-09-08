# 第二章 排序
## 2.1
排序算法类的模板<br>

```
boolean less(Comparable v, Comparable w){
    return v.compareTo(w)<0;
}
void exch(Comparable[] a, int i, int j){
    Comparable t = a[i];
    a[i] = a[j];
    a[j] = t;
}
```


选择排序<br>
思想：遍历，找到最小的数，将其和数组第一个位置的元素交换位置，再在剩下的元素中重复这一操作。

```
public class Selection{
    public static void sort(Comparable[] a){
        int N = a.length;
        for(int i=0; i<N; i++){
            int min = i;
            for(int j = i+1; j<N; j++){
                if(less(a[j], a[min]){
                    min = j;
                }
            }
            exch(a, i, min);
        }
    }
}
```
选择排序的特点<br>

- 对于长度N的数组，需要大约N^2/2次比较和N次交换
- 运行时间和数组内元素的顺序无关
- 数据移动是最少的（一共移动N次）


插入排序<br>
思想：插入排序中，数组的左边是已经排好序的，数组右边是等待被插入的。每次遍历右边，如果存在小于左边最大值的元素，便将其插入在左边合适的位置。

```
public class Insertion{
    public static void sort(Comparable[] a){
        int N = a.length;
        for(int i=1; i<N; i++){
            for(int j=i; j>0 && less(a[j], a[j-1]); j--){
                exch(a, j, j-1);
            }
        }
    }
}
```
插入排序的特点<br>
- 对于随机排列的长度为N且主键不重复的数组，平均情况下需要 ~N^2/4次比较以及 ~N^2/4次交换。最坏情况需要 ~N^2/2次比较以及 ~N^2/2次交换，最好情况需要N-1次交换和0次交换。

希尔排序<br>
思想：基于插入排序的快速排序算法。使数组中**任意间隔**为n的元素都是有序的，这样的数组被称为h有序数组。

```
public class shell{
    public static void sort(Comparable[] a){
        int N = a.length;
        int h = 1;
        while(h < N/3)
            h = 3*h+1;
        while(h >= 1){
            for(int i=h; i<N; i++){
                for(int j = i; j>=h && less(a[j], a[j-h]); j--){
                    exch(a, j, j-h);
                }
            }
            h = h/3;
        }
    }
}
```
希尔排序的特点：
- 数组越大，希尔排序的优势越大。
- 使用递增序列1,4,13,40,121,364...的希尔排序所需的比较次数不会超出N的若干倍乘以递增序列的长度。
- 希尔排序的优点：代码量小，不占用额外空间，对于中等大小的数组来说速度足够用。即使是更高效的算法，除了对于很大的N，速度也不会比希尔排序快太多。


## 2.2
自顶向下归并排序

```
public class Merge{
    private static Comparable[] aux;
    public static void sort(Comparable[] a){
        aux = new Comparable[a.length];
        sort(a, 0, a.length-1);
    }
    public static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo)    return;
        int mid = lo + (hi - lo)/2;
        sort(a, lo, mid);
        sort(a, mid+1, hi);
        merge(a, lo, mid, hi);
    }
    public static void merge(Comparable[] a, int lo, int mid, int hi){
        int i = lo, j = mid+1;
        for(int k=lo; k<=hi; k++){
            aux[k] = a[k];
        }
        for(int k = lo; k<=hi; k++){
            if      (i > mid)               a[k] = aux[j++];
            else if (j > hi )               a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                            a[k] = aux[i++];  
        }
    }
}
```
自顶向下归并排序的特点
- 对于长度N的数组，需要1/2 NlgN至 NlgN次比较。

对算法的几个改进<br>
- 对小规模子数组使用插入排序
- 如果a[mid]<=a[mid+1]，说明数组已经有序，可以跳过merge
- 不将元素复制到辅助数组，在递归调用的每个层次交换输入数组和辅助数组的角色。

[改进代码](https://www.cnblogs.com/songdechiu/p/6607341.html)<br>

自底向上归并排序


```
public class MergeBU{
    private static Comparable[] aux;
    public static void sort(Comparable[] a){
        int N = a.length;
        aux = new Comparable[N];
        for(int sz = 1; sz < N; sz = sz+sz)
            for((int lo = 0; lo < N-sz; lo += sz+sz)
                merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
    }
}
```
排序算法的复杂度<br>
没有任何基于比较的算法能够保证使用少于lg(N!)~NlgN次比较将长度为N的数组排序。
<br>

## 2.3
