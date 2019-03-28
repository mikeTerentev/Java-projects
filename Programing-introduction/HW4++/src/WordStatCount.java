
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

import static java.util.Collections.swap;

public class WordStatCount {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("error");
            return;
        }
        String x = "";
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Integer> num = new ArrayList<Integer>();
        try {
            Scanner in = new Scanner(new File(args[0]), "utf8");
            while (in.hasNextLine()) {
                x = in.nextLine();
                String[] mem = (x).split("[^\\p{L}\\p{Pd}']+");
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
            }
            boolean flag = true;
            int k = 0;
            for (int i = 0; i != words.size(); ++i) {
                if (!flag) break;
                flag = false;
                for (int j = words.size() - 1; j > i; --j) {
                    if (num.get(j) < num.get(j - 1)) {
                        swap(num, j, j - 1);
                        swap(words, j, j - 1);
                        flag = true;
                    }

                }
            }

            PrintWriter out = new PrintWriter(new File(args[1]));
            for (int i = 0; i != words.size(); ++i) {
                out.write(words.get(i) + " " + String.valueOf(num.get(i)) + "\n");
            }
            out.close();
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
