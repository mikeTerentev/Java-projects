import java.math.*;
import java.util.Scanner;

public class SumBigInteger {
    public static void main(String[] args) {
        String s = "";
        BigInteger sum = new BigInteger("0");
        int begin = 0;
        boolean flag = false;
        for (int j = 0; j != args.length; ++j) {
            for (int i = 0; i != args[j].length(); ++i) {
                if (!Character.isWhitespace(args[j].charAt(i)) && flag == false) {
                    flag = true;
                    begin = i;
                }
                if (flag && ((Character.isWhitespace(args[j].charAt(i))) || i == args[j].length() - 1)) {
                    if (!Character.isWhitespace(args[j].charAt(i))) {
                        sum = sum.add(new BigInteger(args[j].substring(begin, i + 1)));
                    } else {
                        sum = sum.add(new BigInteger(args[j].substring(begin, i)));
                    }
                    s = "";
                    flag = false;
                }
            }
        }
        System.out.println(sum);
    }
}
