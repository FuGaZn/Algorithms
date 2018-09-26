package Code;

import edu.princeton.cs.algs4.*;

/**
 * author: fujiaxing
 * date: 09/26/2018
 *
 * 基于深度优先搜索的顶点排序
 */
public class DepthFirstOrder {
    private boolean[] marked;
    private Queue<Integer> pre;         //前序
    private Queue<Integer> post;        //后序
    private Stack<Integer> reversePost; //逆后序

    public DepthFirstOrder(Digraph G) {
        marked = new boolean[G.V()];
        pre = new Queue<>();
        post = new Queue<>();
        reversePost = new Stack<>();

        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(Digraph G, int v) {
        pre.enqueue(v); //前序：在递归调用前将顶点加入队列
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);

        post.enqueue(v);    //后序，在递归调用之后将顶点加入队列
        reversePost.push(v);    //逆后序，在递归调用之后将顶点加入栈
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
