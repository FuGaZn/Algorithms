package Question;

import java.util.Stack;

/**
 * 假设以某个点为圆心，它四周的8个点在同一个圆上，从圆心出发，遍历这个圆，把这个圆上的能到达的点存到栈里，
 * 接下来再依次从栈里取出一个点，把这个点当成圆心，再遍历以他为圆心的那个圆。直到我们以A点为圆心，并且终点在它的圆周上且能够到达
 */
public class Labyrinth {

    public static void main(String[] args) {
        // 迷宫定义（最外面全是1，代表一堵墙，这样迷宫里的任意一点都能有8个方向
        int[][] maze = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 1},
                {1, 1, 0, 1, 0, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 1},
                {1, 1, 0, 0, 1, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 0, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

        // move是一个坐标周围的八个点的方向
        int[][] move = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};
        Stack<Step> s = new Stack();
        int a = path(maze, move, s);
        while (!s.isEmpty()) {
            Step step = s.pop();
            System.out.println(step.x + ":" + step.y);
        }
    }

    public static int path(int[][] maze, int[][] move, Stack<Step> s) {
        Step temp = new Step(1, 1, -1); //起点，1,1可以改成start_x,start_y
        s.push(temp);
        while (!s.isEmpty()) {  // 栈里存储圆心
            temp = s.pop(); //出栈一个圆心
            int x = temp.x;
            int y = temp.y;
            int d = temp.d + 1; //d是方向的索引
            while (d < 8) { //遍历八个方向
                int i = x + move[d][0];
                int j = y + move[d][1]; // i和j就是圆周上某个点的索引

                if (maze[i][j] == 0) {    //该点可达
                    temp = new Step(i, j, d); //到达新点
                    s.push(temp);   //该点可达，则把该点存入栈中
                    x = i;
                    y = j;
                    maze[x][y] = -1;  //到达新点，标志已经到达（防止回到老路上）
                    if (x == 6 && y == 8) { //这里判断是否到达终点（6,8可以改成end_x,end_y)
                        return 1;  //到达出口，迷宫有路，返回1(Yes)
                    } else {
                        d = 0;  //重新初始化方向
                    }
                } else {
                    d++; //改变方向
                }
            }
        }
        return 0;// No
    }
}

class Step {
    int x, y, d;

    public Step(int x, int y, int d) {
        this.x = x;//横坐标
        this.y = y;//纵坐标
        this.d = d;//方向
    }
}