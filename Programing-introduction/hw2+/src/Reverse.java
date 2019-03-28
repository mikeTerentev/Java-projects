import java.util.ArrayList;
import java.util.Scanner;
public class Reverse {
    public static void main(String [] args){
        ArrayList<String[]> mmr= new ArrayList<String[]>();
        Scanner in= new Scanner(System.in);
        while(in.hasNextLine()){
            mmr.add(in.nextLine().split("\\p{javaWhitespace}"));
        }
        for(int i=mmr.size()-1;i!=-1;i--){
            for(int j=mmr.get(i).length-1;j!=-1;j--){
                System.out.print(mmr.get(i)[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
