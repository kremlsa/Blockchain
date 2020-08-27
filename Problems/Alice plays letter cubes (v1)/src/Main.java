import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine().replaceAll("\\s+","");
        int nums = Integer.parseInt(sc.nextLine());
        String[] chars = sc.nextLine().split(" ");
        for (int i = 0; i < nums; i++) {
            String rev = reverse(reverse(text )+chars[i]);
                System.out.print(text.length() + 1 - prefMax(prefixFunction(rev)));
                if (i < nums - 1) {
                    System.out.print(" ");
                }

        }

    }

    public static int prefMax(int[] prefixes) {
        int max = -1;
        for (int i = 0; i < prefixes.length; i++) {
            if (prefixes[i] > max) {
                max = prefixes[i];
            }
        }
        return max;
    }

    public static String reverse (String input) {
        StringBuilder input1 = new StringBuilder();
        input1.append(input);
        input1 = input1.reverse();
        return input1.toString();
    }

    public static int[] prefixFunction(String str) {
        /* 1 */
        int[] prefixFunc = new int[str.length()];

        /* 2 */
        for (int i = 1; i < str.length(); i++) {
            /* 3 */
            int j = prefixFunc[i - 1];

            while (j > 0 && str.charAt(i) != str.charAt(j)) {
                j = prefixFunc[j - 1];
            }

            /* 4 */
            if (str.charAt(i) == str.charAt(j)) {
                j += 1;
            }

            /* 5 */
            prefixFunc[i] = j;
        }

        /* 6 */
        return prefixFunc;
    }

    public static List<Integer> KMPSearch(String text, String pattern) {
        /* 1 */
        int[] prefixFunc = prefixFunction(pattern);
        ArrayList<Integer> occurrences = new ArrayList<Integer>();
        int j = 0;
        /* 2 */
        for (int i = 0; i < text.length(); i++) {
            /* 3 */
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefixFunc[j - 1];
            }
            /* 4 */
            if (text.charAt(i) == pattern.charAt(j)) {
                j += 1;
            }
            /* 5 */
            if (j == pattern.length()) {
                occurrences.add(i - j + 1);
                j = prefixFunc[j - 1];
            }
        }
        /* 6 */
        return occurrences;
    }
}