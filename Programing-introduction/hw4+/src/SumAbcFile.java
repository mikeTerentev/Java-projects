
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;

import static java.lang.Math.*;

public class яSumAbcFile {
    public static void main(String[] args) throws FileNotFoundException {
        int sum = 0;
        try {
            Scanner in = new Scanner(new File(args[0]));
            while (in.hasNextLine()) {
                String x = in.nextLine();
                if (!x.isEmpty()) {
                    String[] mem = x.split("\\p{javaWhitespace}");
                    for (int i = 0; i != mem.length; ++i) {
                        mem[i] = mem[i].toLowerCase();
                        StringBuffer memCopy = new StringBuffer();
                        for (int j = 0; j != mem[i].length(); ++j) {
                            memCopy.append((char) (mem[i].charAt(i) - 'a'));
                        }
                        sum += Integer.parseInt(String.valueOf(memCopy));
                    }
                }
            }
            PrintWriter out = new PrintWriter(new File(args[1]));
            out.write(String.valueOf(sum));
            out.close();
            in.close();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
