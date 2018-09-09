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

## 2.3 ==快速排序==
快排优点：
- 实现简单，适用于各种不通过输入数据并且在一般应用中比其他排序算法快得多。
- 原地排序（只需要一个很小的辅助栈）
- 将长度N的数组排序所需的时间和NlgN成正比。
- 快排的内循环比大多数排序算法的都要小

快排缺点：
- 非常脆弱，在实现中要非常小心才能避免低劣的性能。

基本算法：<br>
将一个数组分为两个子数组，将两部分独立排序。当两个子数组有序时，整个数组也就自然有序了。<br>
(选出一个哨兵，不大于这个值的数都移动到数组左边，大于这个值的数都移动到右边，以此递归。)<br>


```
public class Quick{
    public static void sort(Comparable[] a){
        StdRandom.shuffle(a); //消除对输入的依赖
        sort(a, 0, a.length-1);
    }
    private static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo)    return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }
    private static int partition(Comparable[] a, int lo, int hi){
        int i = lo, j = hi+1;
        Comparable v = a[lo];
        while(true){
            while(less(a[++i], v))  if(i == hi) break;
            while(less(v, a[--j]))  if(j == lo) break;
            if(i >= j)  break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }
}
```
随机将数组打乱是值得的，这样能够防止出现最坏的情况，并且能使运行时间可以预计。


改进快排算法：<br>
- 对于小数组，快排比插入排序要慢。所以在排序小数组时应切换到插入排序
```
    if (hi <= lo+M) { Insertion.sort(a, lo, hi); return; }
```
- 三取样切分：取样大小为3，并用大小居中的元素来切分数组
- 熵最优排序（三向切分）


## 2.4 优先队列
堆有序：一棵二叉树中每个结点都大于等于它的两个子节点
<br>
一棵大小为N的完全二叉树的高度为lgN（向下取整）
<br>
堆的算法：

```
public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;   //基于堆的完全二叉树的数组表现形式
    private int N = 0;
    
    public MaxPQ (int maxN) {
        pq = (Key[]) new Comparable[maxN+1];
    }
    
    public boolean isEmpty () { return N==0; }
    
    public int size () { return N; }
    
    public void insert (Key v) {
        pq[++N] = v;
        swim(N);
    }
    
    public Key delMax () {
        Key max = pq[1];    //从根结点得到最大元素
        exch(1, N--);   //将根结点和最后一个结点交换
        pq[N+1] = null; //防止对象游离
        sink(1);
        return max;
    }
    
    private boolean less(int i, int j){
        return pq[i].compareTo(pq[j]) < 0;
    }
    
    private void exch(int i, int j){
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
    
    /**
     * 由下至上堆有序化（上浮）
     * 从子节点依次向上遍历，如果父节点的值小于子节点的值，则将其互换，直到父节点大于子节点为止。
     */
    private void swim(int k){
        while (k>1 && less(k/2, k)){
            exch(k/2, k);
            k = k/2;
        }
    }
    
    /**
     * 由上至下堆有序化（下沉）
     * 从父节点依次向下遍历，如果父节点的值小于子节点的值，就将其互换，直到父节点大于子节点为止。
     * 和 swim 的差别在于，每个父节点都可能有两个子节点，那么就要比较两个子节点的大小，将较大的一个和父节点交换
     */
    private void sink(int k){
        while(2*k <= N){
            int j = 2*k;
            if(j < N && less(j, j+1))   j++;
            if(!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
}
```
<br>

**堆排序**<br>


```
public static void sort(Comparable[] a){
    int N = a.length;
    for(int k == N/2; k>=1; k--)    
        sink(a, k, N);  //排序
    while(N > 1){
        exch(a, 1, N--);
        sink(a, 1, N);
    }
}
```