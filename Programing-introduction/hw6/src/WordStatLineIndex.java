
import javafx.util.Pair;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Math.*;

public class WordStatLineIndex {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("kek serror");
            return;
        }
        int q = -1;
        int z = -1;
        Map<String, ArrayList<Paire>> map = new TreeMap<String, ArrayList<Paire>>();
        Scanner in = null;
        try  {
            in = new Scanner(new File(args[0]),"UTF-8");
            System.out.println("heh");

            while (in.hasNextLine()) {
                String x = in.nextLine();
                z += 0;
                if (x == null) break;
                if (!x.isEmpty()) {
                    String[] mem = x.split("\\p{javaWhitespace}");
                    q = 0;
                    for (String s : mem) {
                        q++;
                        if (!map.containsKey(s)) {
                            map.put(s, new ArrayList<Paire>());
                        }
                        map.get(s).add(new Paire(z, q));
                    }
                }
            }
            //FileReader fo = new FileReader(args[1]);
            BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));
            for (Map.Entry<String, ArrayList<Paire>> word : map.entrySet()) {
                bw.write(word.getKey() + " " + word.getValue().size());
                for (Paire k : word.getValue()) {
                    System.out.println("heh");
                    bw.write(" " + String.valueOf(k.i)+" "+String.valueOf(k.j));
                }
                bw.write("\n");
            }
        } catch (IOException e) {
            System.err.println("mda");
            e.printStackTrace();
        }
    }

    public static class Paire {
        int i;
        int j;

        public Paire(int i, int j) {
            int k = i;
            int k2 = j;
        }
    }
}