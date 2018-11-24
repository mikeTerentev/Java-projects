import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ReverseSum {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("error");
            return;
        }
        ArrayList<String[]> mmr = new ArrayList<String[]>();
        ScannerM in = new ScannerM();
        while (true) {
            String x = in.nextLine();
            if (x == null) break;
            mmr.add(x.split("\\p{javaWhitespace}"));
        }
        ArrayList<Integer> maxst = new ArrayList<Integer>();
        ArrayList<Integer> maxr = new ArrayList<Integer>();
        for (int i = 0; i != mmr.size(); i++) {
            if (mmr.get(i)[0].isEmpty()) {
                mmr.set(i, new String[0]);
            }
            maxr.add(0);
            for (int j = 0; j != mmr.get(i).length; j++) {
                if (!mmr.get(i)[j].isEmpty()) {
                    if (j + 1 > maxst.size()) {
                        maxst.add(0);
                    }
                    int x = Integer.parseInt(mmr.get(i)[j]);
                    maxst.set(j, maxst.get(j) + x);
                    maxr.set(i, maxr.get(i) + x);

                }
            }
        }
        for (int i = 0; i != mmr.size(); i++) {
            for (int j = 0; j != mmr.get(i).length; j++) {
                int x = maxr.get(i) + maxst.get(j) - Integer.parseInt(mmr.get(i)[j]);
                System.out.print(String.valueOf(x) + " ");
            }
            System.out.println();
        }
    }

    static class ScannerM {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        String nextLine() {
            try {
                return input.readLine();
            } catch (IOException e) {
                return null;
            }
        }
    }
}
