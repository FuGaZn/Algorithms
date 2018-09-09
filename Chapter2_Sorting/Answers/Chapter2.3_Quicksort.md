2.3.15<br>
先拿出一个螺丝，遍历所有螺帽，把较小的螺帽放在左边，较大的螺帽放在右边。最后能找到一个匹配的螺帽。<br>
再拿这个螺帽，遍历所有螺丝，把较小的螺丝放在左边，较大的螺丝放在右边。拿开匹配好的螺丝和螺帽。<br>
这时螺丝和螺帽都分成了两部分，以此递归。<br>

2.3.18 三取样切分<br>

```
private static int partition(Comparable[] a, int lo, int hi){
    int i = lo, j = hi+1;
    Comparable v = a[getMid(a, lo, hi)];
    while(true){
        while(less(a[++i], v))  if(i == hi) break;
        while(less(v, a[--j]))  if(j == lo) break;
        if(i >= j)  break;
        exch(a, i, j);
    }
    exch(a, lo, j);
    return j;
}

private static int getMid(Comparable[] a, int lo, int hi){
    int mid = lo + (hi-lo)/2;
    if(less(a[lo], a[hi])){
        if      (less(a[mid], a[lo]))   return lo ;
        else if (less(a[hi], a[mid]))   return hi ;
        else                            return mid;
    } else {
        if      (less(a[mid], a[hi]))   return hi ;
        else if (less(a[lo], a[mid]))   return lo ;
        else                            return mid;
    }
}
```


2.3.20
非递归的快速排序<br>

```
public static void sort(Comparable[] a, int lo, int hi){
    Stack<Comparable> s = new Stack();
    s.push(lo);
    s.push(hi);
    while(!s.isEmpty()){
        int right = s.pop();
        int left = s.pop();
        int j = partition(a, left, right);
        if(j-1>left){
            s.push(left);
            s.push(j-1);
        }
        if(j+1<right){
            s.push(j+1);
            s.push(right);
        }
    }
}
```
