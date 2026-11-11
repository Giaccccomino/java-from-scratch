import java.util.Arrays;
public class ForEach {
    public static void main(String[] args) {
        int[] prices = {950, 700, 600};
        for (int p : prices) {
            p = p + 100;                 // changes only the copy
        }
        System.out.println(Arrays.toString(prices));
        for (int i = 0; i < prices.length; i++) {
            prices[i] += 100;
        }
        System.out.println(Arrays.toString(prices));
        String[] names = new String[3];
        names[0] = "Anna";
        int lengths = 0;
        for (String n : names) lengths += n.length();   // NullPointerException at names[1]
    }
}
