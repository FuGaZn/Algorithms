## 字符串排序

低位优先（LSD）：从右向左检查键中的字符。如果把一个字符串看做是一个256进制的数字，那么从右向左检查就等价于先检查数字的地位。<br> LSD适合用于键的**长度都相同**的字符串的排序应用。

高位优先（MSD）：从左向右检查字符。

### 键索引计数法

根据键索引的值进行排序，适用情景如：通过班级号对学生排序。

键索引计数法适用于小整数键的排序。键索引计数法排序有个特点：**原来有序的元素将保持相对有序**。

代码分析：

- 首先要计算每个键出现的频率，count[r+1]代表r索引出现的频率。

  为什么是r+1？保证count[0]恒为0，这样在第二步将频率转化为索引的时候，表示首组的同学前没有其他组。

- 第二步，将频率转化为索引。`count[r+1]+=count[r]`。比如第一组有3人，第二组有5人，那么第三组同学在排序中的起始位置就是8

- 第三步，数据分类，将排序结果写入辅助数组。

  `aux[count[a[i].key()]++] = a[i];` 在迁移数据之后，要将count值加一，以保证count[r]总是第r个元素在aux数组中的索引位置。

- 第四步，回写。

<br>

```java
int N = a.length;
int R = 256; // 字符所属字符集的字符数量，ASCII的数量为256
String[] aux = new String[N]; // 辅助排序数组
int[] count = new int[R+1]; //频率统计数组

// 计算出现频率
for (int i = 0; i < N; i++)
  count[a[i].key()+1]++;
// 将频率转化为索引
for (int r = 0; r < R; r++)
  count[r+1] += count[r];
// 将元素分类
for (int i = 0; i < N; i++)
  aux[count[a[i].key()]++] = a[i];
// 回写
for (int i = 0; i < N; i++)
  a[i] = aux[i];
```

<br>

### 低位优先字符串排序

适用于定长字符串数组的排序。

```java
public class LSD {
  // 通过前W个字符将数组a[]排序
  public static void sort(String[] a, int W) {
    int N = a.length;
    int R = 256;
    String[] aux = new String[N];
    
    // 根据第d个字符用键索引计数法对数组排序
    for (int d = W-1; d >= 0; d--){
      int[] count = new int[R+1];
      for(int i = 0; i<N; i++)
        count[a[i].charAt(d)+1]++；
      for(int r = 0; r<R; r++)
        count[r+1] += count[r];
      for(int i = 0; i<N; i++)
        aux[count[a[i].charAt(d)+1]++] = a[i];
      for(int i = 0; i<N; i++)
        a[i] = aux[i];
    }
  }
}
```



### 高位优先字符串排序

