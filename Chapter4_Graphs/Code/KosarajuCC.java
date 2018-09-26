package Code;
/**
 * author: fujiaxing
 * date: 09/26/2018
 *
 * Kosaraju算法实现计算强连通分量
 */
public class KosarajuCC {
    private boolean[] marked;
    private int[] id;   //强连通分量的标识符
    private int count;

    public KosarajuCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        DepthFirstOrder order = new DepthFirstOrder(G.reverse());
        for (int s : order.reversePost()) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }
    }

    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }
}
