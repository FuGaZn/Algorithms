## 无向图

> 图是由一组顶点和一组能够将两个顶点相连的变组成的。

```
用0到v-1的整数来表示含V个顶点的图中的各个顶点
v-w的记法来表示v和w之间的边。在无向图中，v-w和w-v是相同的。
```

两种特殊的图：

- 自环：一条连接一个顶点和其自身的边
- 平行边：连接同一对顶点的两条边


当两个顶点通过一条边相连时，我们称这两个顶点是***相邻的***，并称这条边依附于这两个顶点。

**路径**是由边顺序连接的一系列顶点。

简单路径是一条没有重复顶点的路径。

环是一条至少含有一条边且起点和终点相同的路径。

> 如果从任意一个顶点都存在一条路径到达另一个任意顶点，我们称这幅图是**连通图**。
>
> 一幅非连通的图由若干连通的部分组成，它们都是其极大连通子图。



> 树是一幅无环连通图
>
> 互不相连的树组成的集合称为森林。



当且仅当一副含有V个结点的图G满足以下5个条件之一时，它就是一棵树：

- G有V-1条边且不含有环
- G有V-1条边且是连通的
- G是连通的，但删除任意一条边都会使它不连通
- G是无环的，但添加任意一条边都会产生一条环
- G中的任意一对顶点之间仅存在一条简单路径


二分图：一种能够将所有结点都分为两部分的图。其中图的每条边所连接的两个顶点都分别属于不同的部分。![](../img/4.1.7.jpg)

常用的图处理代码

```java
public int V();	//顶点数
public int E();	//边数
public addEdge(int v, int w);	//添加一条边v-w
public Interable<Integer> adj(int v);	// 和v相邻的所有顶点
public String toString(){
  String s = V + " vertices, " + E + " edges\n";
  for(int v = 0; v < V; v++){
    s+=v+": ";
    for(int w: this.adj(v))
      s+=w+" ";
    s+"\n";
  }
  return s;
}

public static int degree(Graph G, int v){
  int degree = 0;
  for(int w : G.adj(v))	degree++;
  return degree;
}

public static int maxDegree(Graph G){
  int max = 0;
  for(int v = 0; v < G.V(); v++)
    if(degree(G, v) > max)
      max = degreee(G, v);
  return max;
}

public static double avgDegree(Graph G){
  return 2.0 * G.E() / G.V();
}

// 图中自环的数量
public static int numberOfSelfLoops(Graph G){
  int count = 0;
  for(int v = 0; v < G.V(); v++)
    for(int w : G.adj(v))
      if(v == w)	count++;
  return count / 2;
}
```



图的表示方法

