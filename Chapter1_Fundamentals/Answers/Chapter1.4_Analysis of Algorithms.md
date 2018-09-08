## 1.4
1.4.14<br>
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

1.4.24 扔鸡蛋<br>
二分法<br>

1.4.25 **扔两个鸡蛋**<br>
最多扔2sqrt(N)次鸡蛋就能判断F的值<br>
思路：第一个鸡蛋，在sqrt(N)、2sqrt(N)、3sqrt(N)..., sqrt(N)*sqrt(N)处扔下。如果在k * sqrt(N)处破碎，第二个鸡蛋就在(k-1)sqrt(N)到k * sqrt(N)之间扔下。<br>
最多扔2sqrt(N)次鸡蛋。<br>

改进算法：数学反推<br>
先假设最多扔x次就能找到F的位置。<br>
正确的方法是先假设最少判断次数为x，则第一个鸡蛋第一次从第x层扔（不管碎没碎，还有x-1次尝试机会）。如果碎了，则第二个鸡蛋在1～x-1层中线性搜索，最多x-1次；如果没碎，则第一个鸡蛋第二次从x+(x-1)层扔（现在还剩x-2次尝试机会）。如果这次碎了，则第二个鸡蛋在x+1～x+(x-1)-1层中线性搜索，最多x-2次；如果还没碎第一个鸡蛋再从x+(x-1)+(x-2)层扔，依此类推。x次尝试所能确定的最高楼层数为x+(x-1)+(x-2)+...+1=x(x+1)/2。<br>

比如100层楼，用第一种算法，最多扔2*10=20次；用第二种算法，最多扔14次<br>

推广：n层楼，扔m个鸡蛋<BR>
需要用到动态规划:假设f[n,m]表示n层楼、m个鸡蛋时找到摔鸡蛋不碎的最少判断次数。则一个鸡蛋从第i层扔下，如果碎了，还剩m-1个鸡蛋，为确定下面楼层中的安全位置，还需要f[i-1,m-1]次（子问题）；不碎的话，上面还有n-i层，还需要f[n-i,m]次（子问题，实体n层楼的上n-i层需要的最少判断次数和实体n-i层楼需要的最少判断次数其实是一样的）。
> 状态转移方程：f[n,m] = min{ 1+max(f[i-1,m-1], f[n-i,m]) | i＝1..n } 初始条件：f[i,0]=0（或f[i,1]=i），对所有i

[【算法】楼层扔鸡蛋问题](https://blog.csdn.net/mermaidz/article/details/11819585)

1.4.26 三点共线<br>
a+b+c=0是(a, a^3), (b, b^3), (c, c^3)三点共线的充要条件。

1.4.27 用两个栈实现队列<br>

```
public Queue<Item>{
    Stack<Item> A = new Stack();
    Stack<Item> B = new Stack();
    
    public int size(){
        return A.size()+B.size();
    }
    
    public boolean isEmpty(){
        return A.isEmpty() || B.isEmpty();
    }
    
    public void enqueue(Item item){
        A.push(item);
    }
    
    public Item dequeue(){
        if(B.isEmpty()){
            while(!A.isEmpty()){
                B.push(A.pop());
            }
            B.pop();
        }else{
            B.pop();
        }
    }
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

