import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine().replaceAll("\\s+","");
        int result = 2;
        for (int i = 1; i < text.length(); i++) {
            String s = text.substring(0, i);
            char c = text.toCharArray()[i];

            String t = new StringBuilder(s).append(c).reverse().toString();
            //int prefixMax = prefMax(prefixFunction(t));
            int prefixMax = Arrays.stream(prefixFunction(t)).max().orElse(0);
            result += s.length() + 1 - prefixMax;
        }

        System.out.println(result);


    }

    public static int prefMax(int[] prefixes) {
        int max = 0;
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