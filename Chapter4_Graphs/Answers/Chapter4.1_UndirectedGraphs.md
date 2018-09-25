4.1.1

至多2^(V-2)+V条边

连通图，至少V-1条边

<br>

4.1.3

```java
public Graph(Graph G){
  this.V = G.V();
  this.E = G.E();
  adj = (Bag<Integer>[]) new Bag[G.V()];
  for(int v= 0;v<V;v++)
    adj[v] = new Bag(G.adj[v]);
}
```



4.1.4

```java
public boolean hasEdge(int v, int w){
  for(int i: this.adj[v]){
    if(i == w)
      return true;
  }
  return false;
}
```



4.1.5

```java
public void addEdge(int v, int w){
  if(!this.hasEdge(v, w) && v!=w){
    this.adj[v].add(w);
    this.adj[w].add(v);
    E++;
  }
}
```

