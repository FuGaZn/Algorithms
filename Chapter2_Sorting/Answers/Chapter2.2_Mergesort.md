2.2.10<br>
按降序将a[]的后半部分复制到aux[]，然后将其归并回a[]，这样可以去掉内循环中检测某半边是否用尽的代码。

```
public static void merge(int[] a, int lo, int mid, int hi) {
    int i = lo, j = hi;
    for (int k = lo; k <= mid; k++) {
        aux[k] = a[k];
    }
    for (int k = mid + 1; k <= hi; k++) {
        aux[k] = a[hi - k + mid + 1];
    }
    for (int k = lo; k <= hi; k++) {
        if ((aux[j] < aux[i])) a[k] = aux[j--];
        else a[k] = aux[i++];
    }
}
```
2.2.11<br>
**对自顶向下归并算法的三个改进**
- 加快小数组的排序顺序
- 检测数组是否已经有序(?a[mid]<a[mid+1])
- 在递归中互换a[]和aux[]来避免数组复制

```
public class MergeInsSort {
    public static final int CUTOFF = 5; //插入排序处理数组长度
    private int[] array;

    public void sort(int[] a) {
        array = a.clone();
        mergeSort(array, a, 0, a.length - 1);

    }

    //核心算法, 对dst进行排序
    public void mergeSort(int[] src, int[] dst, int down, int up) {
        //改进,小规模用插入排序,结束条件
        if (up - down <= CUTOFF) {
            insertionSort(dst, down, up);
            return;
        }
        int mid = (up - down) / 2 + down;
        mergeSort(dst, src, down, mid); //左半边排序，交换输入数组和辅助数组角色
        mergeSort(dst, src, mid + 1, up); //右半边排序，结果：src中有序

        if(src[mid] < src[mid + 1]) { //是否已经有序
            System.arraycopy(src, down, dst, down, up-down+1);
            return;
        }
        merge(src, dst, down, mid, up);
    }

    //一个数组左右半边分别有序，src归并到dst
    public void merge(int[] src, int[] dst, int down, int mid, int up) {
        assert isSorted(src, down, mid);  //断言，左右半边均有序
        assert isSorted(src, mid+1,up);

        int i = down, j = mid + 1;
        for (int k = down; k <= up; k++) {
            if (i > mid) dst[k] = src[j++]; //左半边用尽，取右半边元素
            else if (j > up) dst[k] = src[i++];
            else if (src[i] < src[j]) //左半边元素比右半边小
                dst[k] = src[i++];
            else dst[k] = src[j++];
        }
        assert isSorted(dst, down, up);
    }

    //插入排序
    public void insertionSort(int[] a, int down, int up) {
        for (int i = down+1; i <= up; i++) {
            for (int j = i; j >= down+1 && a[j] < a[j-1]; j--) {
                swap(a, j, j-1);
            }
        }
    }

    //交换两个元素
    public void swap(int[] a,int i,int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    //判断down到up的元素是否有序
    public boolean isSorted(int[] a, int down, int up) {
        for (int i = down+1; i <= up; i++) {
            if (a[i] < a[i - 1])
                return false;
        }
        return true;
    }
}
```

2.2.17 链表排序<br>
对链表排序的最佳方法。因为它不需要额外的空间，且运行时间是线性对数级别的。

```

public class Node{
    Comparable info;
    Node next;
}

public class Merge{
    Node head;
    Node tail;

    public Node merge_sort(Node head){
        if (head == null || head.next == null) {
            return head;
        }

        Node middle = getMiddle(head); //get the middle of the list
        Node sHalf = middle.next; middle.next = null; //split the list into two halfs

        return merge(merge_sort(head),merge_sort(sHalf)); //recurse on that;
    }

    public Node merge(Node a, Node b){
        Node dummyHead, curr; dummyHead = new Node(); curr = dummyHead;
        while (a!=null && b!=null){
            if (!less(b.info, a.info)) {curr.next = a; a = a.next;}
            else { curr.next = b; b = b.next;}
            curr=curr.next;
        }
        curr.next = (a == null) ? b : a;
        return dummyHead.next;
    }

    public Node getMiddle(Node head){
        if (head == null) {return head;}
        Node slow, fast; slow = fast = head;
        while(fast.next != null && fast.next.next != null){
            slow = slow.next; fast = fast.next.next;
        }

        return slow;
    }


    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }
}
```
