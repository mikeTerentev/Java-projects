
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class WordStatInput {
    public static void main(String[] args) throws IOException {
        String x = "";
        ArrayList<String> mem = new ArrayList<String>();
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Integer> num = new ArrayList<Integer>();
        Scanner in = new Scanner(new File(args[0]));
        int begin=0;
        while (in.hasNextLine()) {
            x = in.nextLine();
            boolean flag = false;
            for (int i = 0; i != x.length(); ++i) {
                if (!Character.isWhitespace(x.charAt(i))Character.getType(x.charAt(i)) == Character.DASH_PUNCTUATION && !flag) {
                    flag = true;
                     begin = i;
                }
                if (flag && ((Character.isWhitespace(x.charAt(i))) || i == args[j].length() - 1)) {
                    if (!Character.isWhitespace(x.charAt(i))) {
                        mem.add(x.substring(begin, i + 1));
                    } else {
                        mem.add(x.substring(begin, i));
                    }
                    flag = false;
                }
            }
        }
        for (String si : mem) {
            if (!si.isEmpty()) {
                si = si.toLowerCase();
                boolean flag = false;
                for (int i = 0; i != words.size(); ++i) {
                    if (words.get(i).equals(si)) {
                        num.set(i, num.get(i) + 1);
                        flag = true;
                    }
                }
                if (!flag) {
                    words.add(si);
                    num.add(1);
                }
            }
        }
        PrintWriter out = new PrintWriter(new File(args[1]));
        for (int i = 0; i != words.size(); ++i) {
            out.write(words.get(i) + " " + String.valueOf(num.get(i)) + "\n");
        }
        out.close();
        in.close();

    }
}
