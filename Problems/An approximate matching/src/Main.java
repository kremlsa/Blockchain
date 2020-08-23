import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner(System.in);
        String source = sc.nextLine();
        String target = sc.nextLine();

        Alignment result = Alignment.editDistanceAlignment(source, target);

        System.out.println(result.editDistance);
        System.out.println(result.source);
        System.out.println(result.target);
    }
}

class Alignment {
    public int editDistance;
    public String source;
    public String target;

    public Alignment(int editDist, String source, String target) {
        this.editDistance = editDist;
        this.source = source;
        this.target = target;
    }

    public static int match(char a, char b) {
        return (a == b) ? 0 : 1;
    }

    public static Alignment editDistanceAlignment(String s, String t) {
        int weight1 = 3;
        int weight2 = 5;

        if (s.isEmpty()) {
            return new Alignment(t.length(), t, t);
        }

        if (s.length() > t.length()) {
            return new Alignment(s.length(), s, s);
        }

        int[][] d = new int[s.length() + 1][t.length() + 1];

        for (int i = 0; i < s.length() + 1; i++) {
            d[i][0] = i * weight1;
        }

        for (int j = 0; j < t.length() + 1; j++) {
            d[0][j] = 0;
        }

        for (int i = 1; i < s.length() + 1; i++) {
            for (int j = 1; j < t.length() + 1; j++) {
                int insCost = d[i][j - 1] + weight1;
                int delCost = d[i - 1][j] + weight1;
                int subCost = d[i - 1][j - 1] + match(s.charAt(i - 1), t.charAt(j - 1)) * weight2;
                d[i][j] = Math.min(Math.min(insCost, delCost), subCost);
            }
        }

        int min = Integer.MAX_VALUE;
        int minPos = t.length();
        for (int i = 0; i < t.length() + 1; i++) {
            int cost = d[s.length()][i];
            if (cost < min) {
                min = cost;
                minPos = i;
            }
        }

        StringBuilder ssBuilder = new StringBuilder();
        StringBuilder ttBuilder = new StringBuilder();
        int i = s.length();
        int j = minPos;

        while (i > 0) {
            if (d[i][j] == d[i][j - 1] + weight1) {
                ssBuilder.append("-");
                ttBuilder.append(t.charAt(j - 1));
                j -= 1;
            } else if (d[i][j] == d[i - 1][j] + weight1) {
                ssBuilder.append(s.charAt(i - 1));
                ttBuilder.append("-");
                i -= 1;
            } else if (d[i][j] == (d[i - 1][j - 1] + match(s.charAt(i - 1), t.charAt(j - 1)) * weight2)) {
                ssBuilder.append(s.charAt(i - 1));
                ttBuilder.append(t.charAt(j - 1));
                i -= 1;
                j -= 1;
            }
        }

        String ss = ssBuilder.reverse().toString();
        String tt = ttBuilder.reverse().toString();

        return new Alignment(min, ss, tt);
    }
}