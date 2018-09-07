## 1.4
1.4.14
https://github.com/FuGaZn/LeetCode/blob/master/Algorithms/18_4Sum.java

1.4.15<br>
线性级别的算法实现2-sum<br>
以空间换时间

```
public List<List<Integer>> TwoSum(int[] nums){
    Arrays.sort(nums);
    List<List<Integer>> list = new ArrayList();
    int[] arr = new int[nums[nums.length-1]];
    for(int i=0; i<nums.length; i++){
        if(nums[i]>0){
            arr[nums[i]] = nums[i];
        }
    }
    for(int i=0; i<nums.length; i++){
        if(nums[i]<0){
            if(arr[-nums[i]] != 0){
                List<Integer> tmp = new ArrayList<>();
                tmp.add(nums[i]);
                tmp.add(-nums[i]);
                list.add(new ArrayList(tmp));
            }
        }else{
            break;
        }
    }
}
```
1.4.16<br>

```
public List<Integer> nearest(int[] nums){
    Arrays.sort(nums);
    int val1 = 0;
    int val2 = 0;
    int diff = Math.abs(nums[1]-nums[0]);
    for(int i=0;i<nums.length-2; i++){
        if(Math.abs(nums[i+1]-nums[i]) < diff){
            val1 = nums[i];
            val2 = nums[i+1];
        }
    }
    List<Integer> tmp = new ArrayList();
    tmp.add(val1);
    tmp.add(val2);
    return tmp;
}
```


1.4.17<br>
数组排序后首尾两个值<br>

1.4.18 局部最小元素<br>
对于任何一个数组，局部最小元素一定尽，因为最小值一定是局部最小值。使用二分法来查找，每次都向较小方向移动，一定会碰到局部最小元素。

```
public int localMin(int[] a, int N){
    if(a[0]<a[1])       
        return a[0];
        
    if(a[N-1]<a[N-2])   
        return a[N-1];
    
    int low = 0, high = N-1, mid = 0;
    
    while(low <= high){
        mid = (low + high) / 2;
        if(a[mid-1] < a[mid] && a[mid] < a[mid+1])
            return a[mid];
        else if(a[mid-1] < a[mid] && a[mid] < a[mid+1])
            high = mid-1;
        else if(a[mid-1] < a[mid] && a[mid] > a[mid+1])
            high = mid -1;
        else
            low = mid + 1;
    }
    return -1;
}
```
1.4.20 双调查找<br>
思路：先用二分法找到双调数组中的最大值，然后在最大值两边各二分查找。

```
public static int max(int[] a, int low, int high) {
    if (low == high) return low;
    int mid = low + (high - low) / 2;
    if (a[mid] < a[mid + 1]) return max(a, mid+1, high);
    if (a[mid] > a[mid + 1]) return max(a, low, mid);
    else return mid;
} 
public int binarySearch(int[] a){
    //
}
```

1.4.22 **仅用加减实现二分查找(斐波那契查找)**<br>
用斐波那契数代替2的幂进行查找

```
private static int maxsize = 20;
public static int[] fibonacci(){
    int f = new int[maxsize];
    f[0] = 1;
    f[1] = 1;
    for(int i=2; i<maxsize; i++){
        f[i] = f[i-1] + f[i-2];
    }
    return f;
}
public int fiboSearch(int[] a, int key){
    int low = 0, high = a.length-1;
    int k = 0; //斐波那契分割数值下标
    int mid = 0;
    int f[] = fibonacci();
    
    while(high>f[k]-1){
        k++;
    }
    
    int[] tmp = Arrays.copyOf(a, f[k]);
    for(int i=high+1; i<tmp.length; i++){
        tmp[i] = a[high];
    }
    
    while(low <= high){
        mid = low + f[k-1] - 1;
        if(key<tmp[mid){
            high = mid-1;
            k--;
        }else if(key > tmp[mid]){
            low = mid + 1;
            k-=2;
        }else{
            if(mid<high){
                return mid;
            }else{
                return high;
            }
        }
    }
    return -1;
}
```




## 1.5
1.5.1<br>

序列 | id数组内容 | 访问次数
---|---|---
9-0|9 1 2 3 4 5 6 7 8 9|13 
3-4|9 1 2 3 3 5 6 7 8 9|13
5-8|9 1 2 3 3 5 6 7 5 9|13
7-2|9 1 7 3 3 5 6 7 5 9|13
2-1|9 7 7 3 3 5 6 7 5 9|13
5-7|9 5 5 3 3 5 6 5 5 9|16
0-3|9 5 5 9 9 5 6 5 5 9|14
4-2|9 9 9 9 9 9 6 9 9 9|17

