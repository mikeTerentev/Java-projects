import java.util.ArrayList;
import java.util.Scanner;

public class Reverse {
    public static void main(String [] args){
        ArrayList<String> mmr= new ArrayList();
        Scanner in= new Scanner(System.in);
        while(in.hasNext()){
            mmr.add(in.nextLine());
        }
        for(int i=mmr.size()-1;i>=0;--i){
            for(int j=mmr.get(i).length();j>=0;--j){
                if(Character.isWhitespace(mmr.get(i).charAt(j)))
            }
        }

        in.close();
    }
}
