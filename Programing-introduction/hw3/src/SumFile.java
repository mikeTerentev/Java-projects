
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.Math;

public class SumAbcFile{
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException("2 args is allowed");
        }
        int sum = 0;
        try {
            Scanner in = new Scanner(new File(args[0]));
            while (in.hasNextLine()) {
                String[] mem = in.nextline.split("[^\\p{L}]+");
                for (String si : mem) {
                    si = si.toLowerCase();
                    for (int i = 0; i != si.length(); ++i) {
                        sum += ('si.charAt(i)' - 'a') * Math.pow(10, si.length() - i - 1);
                    }
                }

            }
            PrintWriter out = new PrintWriter(new File(args[1]));
            out.write(String.valueOf(sum));
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
