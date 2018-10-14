package Code;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KMP_DFA {
    private String pat;
    private int[][] dfa;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String txt = sc.nextLine();
        String pat = sc.nextLine();
        KMP_DFA kmp_dfa = new KMP_DFA(pat);
        List<Integer> list = kmp_dfa.search(txt);
        for (Integer i : list)
            System.out.println(i);
    }

    public KMP_DFA(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];    // 复制匹配失败情况下的值
            dfa[pat.charAt(j)][j] = j + 1;    // 设置匹配成功情况下的值
            X = dfa[pat.charAt(j)][X];    // 更新重启状态
        }
    }

    public List<Integer> search(String txt) {
        List<Integer> list = new ArrayList<>();
        int i, j, N = txt.length(), M = pat.length();
        for (i = 0, j = 0; i < N; i++) {
            j = dfa[txt.charAt(i)][j];
            if (j == M) {
                list.add(i - M + 1);
                j = 0;
            }
        }
        return list;
    }
}