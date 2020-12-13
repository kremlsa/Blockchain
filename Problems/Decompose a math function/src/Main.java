import java.util.Scanner;

class MultipleFunction {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double x = scanner.nextDouble();
        System.out.println(f(x));
    }

    public static double f(double x) {
        Double result;
        if (x <= 0.0) {
            result = f1(x);
        } else if (x > 0 && x < 1) {
            result = f2(x);
        } else {
            result = f3(x);
        }
        return result;
    }

    //implement your methods here
    public static double f1(Double x) {
        return Math.pow(x, 2.0) + 1.0;
    }

    public static double f2(Double x) {
        return 1 / Math.pow(x, 2.0);
    }

    public static double f3(Double x) {
        return Math.pow(x, 2.0) - 1.0;
    }
}