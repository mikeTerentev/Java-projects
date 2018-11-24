
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class SumAbcFile {
    public static void main(String[] args) {
        int sum = 0;
       /* if (args.length != 2) {
            throw new IllegalArgumentException("Invalid usage: only two arguments are allowed");
        }*/
        try {
            Scanner in = new Scanner(new File("input.txt"), "utf8");
            while (in.hasNextLine()) {
                String x = in.nextLine();
                String[] mem = x.split("[\\W&&[^-]]");
                for (String si : mem) {
                    if (!si.isEmpty()) {
                        si = si.toLowerCase();
                        int key = 1;
                        if (si.charAt(0) == '-') {
                            key = -1;
                        }
                        int num = 0;
                        for (int i = 0; i != si.length(); i++) {
                            if (key == -1 && i == 0) continue;
                            num = num * 10 + si.charAt(i) - 'a';
                        }
                        sum += key * num;

                    }
                }
            }
            PrintWriter out = new PrintWriter(new File("output.txt"));
            out.write(String.valueOf(sum));
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
