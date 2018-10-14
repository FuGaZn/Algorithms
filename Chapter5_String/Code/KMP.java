package Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KMP {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String txt = sc.nextLine();
        String pat = sc.nextLine();
        sc.close();
        int[] next = new int[pat.length()];
        getNext(next, pat);
        List<Integer> pos = new ArrayList<>();
        ifMatch(txt, pat, next, pos);
        for (Integer i : pos)
            System.out.println(i);
    }

    public static void ifMatch(String txt, String pat, int[] next, List<Integer> pos) {
        int j = 0, s = 0;
        while (j < txt.length()) {
            if (s == -1 || txt.charAt(j) == pat.charAt(s)) {
                j++;
                s++;
                if (s >= pat.length()) {
                    pos.add(j - pat.length());
                    s = 0;
                    j--;
                }
            } else
                s = next[s];
        }

    }

    private static void getNext(int[] next, String str) {
        next[0] = -1;
        int k = -1;
        int j = 0;
        while (j < str.length() - 1) {
            if (k == -1 || str.charAt(j) == str.charAt(k)) {
                j++;
                k++;
                next[j] = k;
            } else
                k = next[k];
        }
    }
}