package jasper.servicezuul;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author liuyuanju1
 * @date 2020/9/6
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        String[] strings = str.split(" ");
        String logStr = strings[0];
        String key = strings[1];
        System.out.println(findShortSubString(logStr, key));
    }

    private static String findShortSubString(String s, String t) {
        String sLow = s.toLowerCase();
        String tLow = t.toLowerCase();
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : tLow.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        int begin = 0, end = 0;
        int count = 0;
        int minBegin = 0;
        int minLength = Integer.MAX_VALUE;
        while (end < sLow.length()) {
            char c = sLow.charAt(end);
            if (map.containsKey(c)) {
                if (map.get(c) > 0) {
                    count++;
                }
                map.put(c, map.get(c) - 1);
            }
            end++;
            while (count == tLow.length()) {
                if (end - begin < minLength) {
                    minBegin = begin;
                    minLength = end - begin;
                }
                char m = sLow.charAt(begin);
                if (map.containsKey(m)) {
                    if (map.get(m) == 0) {
                        count--;
                    }
                    map.put(m, map.get(m) + 1);
                }
                begin++;

            }

        }
        if (minLength == Integer.MAX_VALUE) {
            return "not found";
        } else {
            return s.substring(minBegin, minBegin + minLength);
        }
    }
}
