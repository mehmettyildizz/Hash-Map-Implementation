import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {


        File file = new File("story.txt");
        Scanner input = new Scanner(file);

        Hashmap<String,Integer> kelimeler=new Hashmap<String, Integer>();
        String str ="";

        //Reading story.txt
        //long a= System.currentTimeMillis();
        while (input.hasNext()) {
            str="";
            String story  = input.next();
            String story_lower=story.toLowerCase(Locale.ENGLISH);
            str+=story_lower;

            if(kelimeler.containsKey(str))
            {
                kelimeler.put(str,kelimeler.get(str)+1);
            }
            else {
                kelimeler.put(str, 1);
            }
        }
        //long b= System.currentTimeMillis();
        //System.out.println(b-a+" "+"miliseconds");

        //System.out.println(kelimeler.collision_count+" "+"collisions");



        //Reading search.txt
        /*
        File file2 = new File("search.txt");
        Scanner input2 = new Scanner(file2);

        while (input2.hasNext()) {
            str="";
            String story  = input2.next();
            String story_lower=story.toLowerCase(Locale.ENGLISH);
            str+=story_lower;


            if(kelimeler.containsKey(str))
            {
                kelimeler.put(str,kelimeler.get(str)+1);
            }
            else {
                kelimeler.put(str, 1);
            }
        }
         */



        //CALCULATING SEARCH TIMES FOR SERACH.TXT
        /*
        Scanner scanner=new Scanner(System.in);
        System.out.println("Please enter a word: ");
        String word=scanner.next().toLowerCase(Locale.ENGLISH);
        long a= System.currentTimeMillis();
        if(kelimeler.containsKey(word)){
            System.out.println("Search:"+ word+" "+"Key:"+kelimeler.hashcode(word)+" "+"Count:"+kelimeler.get(word)+" "+ "Index:"+kelimeler.hashFunction(word)+" ");
        }
        else {
            System.out.print("Word does not exist in Hashmap.");
        }
        long b= System.currentTimeMillis();
        System.out.println(b-a+" "+"miliseconds");
         */





        //System.out.println(System.currentTimeMillis());
        //System.out.println(kelimeler.collision_count);
        //kelimeler.write();


        kelimeler.write();

        Scanner scanner=new Scanner(System.in);
        System.out.println("Please enter a word: ");
        String word=scanner.next().toLowerCase(Locale.ENGLISH);
        if(kelimeler.containsKey(word)){
            System.out.println("Search:"+ word+" "+"Key:"+kelimeler.hashcode(word)+" "+"Count:"+kelimeler.get(word)+" "+ "Index:"+kelimeler.hashFunction(word)+" ");
        }
        else {
            System.out.print("Word does not exist in Hashmap.");
        }











    }
}
