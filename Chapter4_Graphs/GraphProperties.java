package Code;

import edu.princeton.cs.algs4.*;

public class GraphProperties {
    private Graph graph;
    private boolean[] marked;
    private int[] pathlen;
    private int[] circle;
    private int diameter = 0;
    private int radius = 0;
    private int center = 0;
    private int girth = 0;


    GraphProperties(Graph G) {
        pathlen = new int[G.V()];
        marked = new boolean[G.V()];
        circle = new int[G.V()];
        graph = G;
    }

    public int eccentricity(int v) {
        int res = 0;
        bfs(graph, v);
        for (int i = 0; i < pathlen.length; i++) {
            if (i != v) {
                res = Math.max(res, pathlen[i]);
            }
        }
        return res;
    }

    public int diameter() {
        if (diameter == 0) {
            for (int v = 0; v < graph.V(); v++) {
                diameter = Math.max(diameter, eccentricity(v));
            }
        }
        return diameter;
    }

    public int radius() {
        if (radius == 0) {
            radius = eccentricity(0);
            for (int v = 0; v < graph.V(); v++) {
                radius = Math.min(radius, eccentricity(v));
            }
        }
        return radius;
    }

    public int center() {
        if (center == 0) {
            for (int v = 0; v < graph.V(); v++) {
                if (eccentricity(v) == radius()) {
                    center = v;
                    break;
                }
            }
        }
        return center;
    }

    public int girth() {
        if (girth == 0) {
            for (int i = 0; i < circle.length; i++) {
                if (circle[i] != 0) {
                    if (girth == 0)
                        girth = circle[i];
                    girth = Math.min(girth, circle[i]);
                }
            }
        }
        return girth == 0 ? Integer.MAX_VALUE : girth;
    }


    private void bfs(Graph g, int v) {
        for (int i = 0; i < pathlen.length; i++) {
            pathlen[i] = 0;
            marked[i] = false;
        }
        Queue<Integer> queue = new Queue<>();
        marked[v] = true;
        queue.enqueue(v);
        while (!queue.isEmpty()) {
            int i = queue.dequeue();
            for (int p : graph.adj(i)) {
                if (!marked[p]) {
                    marked[p] = true;
                    pathlen[p] = pathlen[i] + 1;
                    queue.enqueue(p);
                } else {
                    for (int q : graph.adj(p)) {
                        if (marked[q] && q != i) {
                            circle[q] = (pathlen[p] - pathlen[q]) + 2;
                        }
                    }
                }
            }
        }
    }
}
