

import java.io.*;
import java.util.*;

public class WordStatLineIndex {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("error");
            return;
        }
        ScannerM input = null;
        PrintWriter output = null;
        try {
            TreeMap<String, ArrayList<Pair>> ans = new TreeMap<>();
            int nline = 0;
            int ncolumn;
            input = new ScannerM(args[0]);
            while (true) {
                String line = input.nextLine();
                if (line == null) break;
                String[] words = line.split("[^\\p{L}\\p{Pd}']+");

                ++nline;
                ncolumn = 0;
                for (String word : words) {
                    word = word.toLowerCase();
                    if (word.isEmpty()) {
                        continue;
                    }
                    if (ans.get(word) == null) {
                        ArrayList<Pair> mem = new ArrayList<>();
                        mem.add(new Pair(nline, ++ncolumn));
                        ans.put(word, mem);
                    } else {
                        ans.get(word).add(new Pair(nline, ++ncolumn));
                    }
                }
            }

            output = new PrintWriter(new File(args[1]), "UTF-8");
            for (Map.Entry<String, ArrayList<Pair>> word : ans.entrySet()) {
                output.print(word.getKey() + " " + word.getValue().size());
                for (Pair i : word.getValue()) {
                    output.print(" " + i.x + ":" + i.y);
                }
                output.println();
            }
        } catch (IOException e) {
            System.out.println("Wrong with input/output files");
        } catch (Exception err) {
            System.out.println("Unexpected error");

        } finally {
            input.close();
            output.close();
        }
    }

    static class Pair {
        Integer x;
        Integer y;

        public Pair() {
            this.x = 0;
            this.y = 0;
        }

        public Pair(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }
    }

    static class ScannerM {
        String filex;
        BufferedReader input;

        public ScannerM(String x) {
            filex = x;
            try {
                input = new BufferedReader(new InputStreamReader(new FileInputStream(filex)));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Wrong with input/output files");
            }
        }

        String nextLine() {
            try {
                return input.readLine();
            } catch (IOException e) {
                System.out.println("Wrong with input/output files");
                return null;
            }
        }

        public void close() {
            try {
                input.close();
            } catch (IOException e) {
                System.out.println("Wrong with input/output files");
            }
        }
    }
}
