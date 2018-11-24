import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class Reverse {
    public static void main(String[] args) {
        ArrayList<String[]> mmr = new ArrayList<String[]>();
        ScannerM in = new ScannerM();
        while (true) {
            String x=in.nextLine();
            if(x==null) break;
            mmr.add(x.split("\\p{javaWhitespace}"));
        }
        for (int i = mmr.size() - 1; i != -1; i--) {
            for (int j = mmr.get(i).length - 1; j != -1; j--) {
                System.out.print(mmr.get(i)[j]);
                System.out.print(" ");
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
