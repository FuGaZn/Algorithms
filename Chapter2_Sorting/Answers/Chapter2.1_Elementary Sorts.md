2.1.13<br>
每副纸牌中四个花色的牌数都是固定的，那么可以每次遍历数组，通过选择排序，把数组前13个都交换成黑桃，依次类推。<br>

2.1.14<br>
只允许交换最上面的两张或者把第一张移动到最下面。可以使用类似于冒泡排序的方法。<br>
先把最上面两张按大小交换，然后把第一张移动到最下面，持续做以上步骤。直到扑克牌排序完成。

2.1.15<br>
选择排序，只需要交换N次。

2.1.24<br>
插入排序的哨兵。在插入排序实现之前，找出最小的元素并将其置于数组的最左边。这样就能去掉内循环中的判断条件j>0<br>
实现：

```
public static void sort(Comparable[] a) {
    int n = a.length;
    
    // put smallest element in position to serve as sentinel
    int exchanges = 0;
    for (int i = n-1; i > 0; i--) {
        if (less(a[i], a[i-1])) {
            exch(a, i, i-1);
            exchanges++;
        }
    }
    if (exchanges == 0) return;

    // insertion sort with half-exchanges
    for (int i = 2; i < n; i++) {
        Comparable v = a[i];
        int j = i;
        while (less(v, a[j-1])) {
            a[j] = a[j-1];
            j--;
        }
        a[j] = v;
    }
}
```
2.1.25<br>

```
public static void sort(Comparable[] a){
    int N = a.length;
    for(int i = 1; i<N; i++){
        Comparable tmp = a[i];
        int j;
        for(j=i-1; j>=0 && less(tmp, a[j]); j--){
            a[j+1] = a[j];
        }
        a[j+1] = tmp;
    }
}
```
