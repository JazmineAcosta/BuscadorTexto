package co.edu.unbosque.app.model;

import java.util.ArrayList;
import java.util.List;

public class BoyerMooreAlgorithm {

    public static List<Integer> search(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();
        int[] badCharTable = preprocessBadChar(pattern);
        int m = pattern.length();
        int n = text.length();
        int s = 0;
        
        while (s <= (n - m)) {
            int j = m - 1;
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j))
                j--;
            if (j < 0) {
                positions.add(s);
                s += (s + m < n) ? m - badCharTable[text.charAt(s + m)] : 1;
            } else {
                s += Math.max(1, j - badCharTable[text.charAt(s + j)]);
            }
        }
        return positions;
    }

    private static int[] preprocessBadChar(String pattern) {
        int[] badCharTable = new int[256];
        for (int i = 0; i < 256; i++) {
            badCharTable[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++) {
            badCharTable[pattern.charAt(i)] = i;
        }
        return badCharTable;
    }
}
