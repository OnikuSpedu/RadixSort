public class Radix {
    public static int nth(int n, int col) {
        return (n - (n % (int) Math.pow(10, col))) / (int) Math.pow(10, col) % 10;
    }
}