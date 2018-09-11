2.4.3<br>
有序数组：每次插入时排序，删除时返回最后一个值并删除。<br>
无序数组：从末端插入，删除时遍历数组找到最大值，将其和最后一个值交换，然后返回最后一个值并排序

2.4.15<br>

```
// is pg[i] bigger than pg[j];
private boolean greater(int i, int j){
    //
}

// is pq[1..N] a min heap?
private boolean isMinHeap() {
    return isMinHeap(1);
}

// is subtree of pq[1..n] rooted at k a min heap?
private boolean isMinHeap(int k) {
    if (k > n) return true;
    int left = 2*k;
    int right = 2*k + 1;
    if (left  <= n && greater(k, left))  return false;
    if (right <= n && greater(k, right)) return false;
    return isMinHeap(left) && isMinHeap(right);
}
```
2.4.26 **无需交换的堆**<br>
参见2.1.25，主要重写了swim和sink方法

```
private void swim(int k) {
    Key tmp = pq[k];
    while (k > 1 && less(k / 2, k)) {
        pq[k] = pq[k / 2];
        k = k / 2;
        pq[k] = tmp;
    }
}
private void sink(int k) {
    Key tmp = pq[k];
    while (2 * k <= N) {
        int j = 2 * k;
        if (j < N && less(j, j + 1)) j++;
        if (!less(k, j)) break;
        pq[k] = pq[j];
        k = j;
    }
    pq[k] = tmp;
}
```



2.4.27<br>
用一个实例变量指向最小值，每次insert时更新


2.4.30 动态中位数查找<br>

思路：建一个最大堆和一个最小堆，满足以下条件：
- 两个堆的高度相当，或者最小堆的高度比最大堆的高度大1
- 最大堆堆顶值小于最小堆堆顶值

最后返回最小堆的堆顶或者两个堆堆顶平均值。
