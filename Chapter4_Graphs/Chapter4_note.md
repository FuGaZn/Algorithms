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
public String toString();

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



### 图的表示方法：邻接表

将每个顶点的所有相邻结点都保存在该顶点对应的元素所指向的一张链表中。

这个链表可以用Bag来实现（链表中所有点与点之间并没有联系）

```java
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.Iterator;

public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Graph(int V) {
        this.V = V;	//读取V并将图初始化
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    public Graph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public String toString() {
        String s = V + " vertices, " + E + " edges\n";
        for (int v = 0; v < V; v++) {
            s += v + ": ";
            for (int w : this.adj(v))
                s += w + " ";
            s += "\n";
        }
        return s;
    }
}
```



### 深度优先搜索	DFS

```java
public class DepthFirstSearch {
  private boolean[] marked;
  private int count;
  
  public DepthFirstSearch(Graph G, int s){
    marked = new boolean[G.V()];
    dfs(G, s)
  }
  
  private void dfs(Graph G, int v){
  	marked[v] = true;
    count++;
    for(int w: G.adj(v))
      if(!marked[w])	dfs(G, w);
  }
}
```

使用深度优先搜索得到的从给定起点到任意标记顶点的路径所需时间与路径的长度成正比。



### 广度优先搜索	BFS

实现思路：使用一个队列来保存所有已经被标记过的但其领接表还未被检查的顶点。重复以下步骤:

- 取队列中的下一个顶点v并标记它。
- 将与v相邻的所有未被标记过的顶点加入到队列。

```java
private void bfs(Graph G, int s){
  Queue<Integer> queue = new Queue<Integer>();
  marked[s] = true;
  queue.enqueue(s);
  while(!queue.isEmpty()){
  	int v = queue.dequeue();
    for(int w : G.adj(v))
      if(!marked[w]){
        marked[w] = true;
        queue.enqueue(w);
      }
  }
}
```

对于从s可达的任意顶点v，广度优先搜索都能找到一条从s到v的最短路径。

广度优先搜索所需时间在最坏情况下和V+E成正比。



### 连通分量

