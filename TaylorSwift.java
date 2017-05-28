import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class TaylorSwift {

    //HashMap containing Data for Markov Generator
    public static HashMap<String,ArrayList<String>> wordsList = new HashMap();



    public static void generateLyric(String fileName){

    String content = "";
        try {
            content = new Scanner(new File(fileName)).useDelimiter("\\Z").next();

        } catch (IOException e){
            System.out.println("File Not Found");
        }

        content = content.replace("\n"," ");
        content = content.replace(")","");
        content = content.replace("(","");
        content = content.replace(",","");
        content = content.replace(".","");
        content = content.replace("\"","");
        //Array containing all words in the songs
        String[] words = content.split(" ");

        //Creating the HashMap of Data
        for(int i = 0; i<words.length; i++) {

            if (i == words.length - 1) {
                continue;
            } else {

                ArrayList<String> list = wordsList.get(words[i]);

                if (list == null) {
                    wordsList.put(words[i], new ArrayList<String>());
                    wordsList.get(words[i]).add(words[i + 1]);
                } else {
                    wordsList.get(words[i]).add(words[i + 1]);
                }
            }
        }

    }

    public static String markovGenerate(HashMap<String,ArrayList<String>> wordsList){

        Random random = new Random();
        ArrayList<String> keys = new ArrayList<>(wordsList.keySet());

        String word = keys.get(random.nextInt(wordsList.size()));

        String lyric = word + " ";

        int count = 0;
        int line = 0;

        int perLine = 0;
        ArrayList<String> l = new ArrayList<>();

        while (count < 90) {

             word = (wordsList.get(word)).get(new Random().nextInt(wordsList.get(word).size()));


            count = count + word.length();
            perLine ++;

            if (word.equals(word.toLowerCase()))
            lyric = lyric + word + " ";

            else if (perLine > 2 ){
                lyric = lyric + "\n" + word + " ";
                line++;
                perLine = 0;
            } else {
                word = word.toLowerCase();
                lyric = lyric + word + " ";
            }

            if (line == 2 && perLine>2) {
                return lyric;
            }
        }


        return lyric;
    }


    public static void main(String... args) throws TwitterException{
        generateLyric("/Users/csclass/Desktop/TaylorSwift/src/lyrics.txt");


        do {


            Twitter twitter = TwitterFactory.getSingleton();

            System.out.println(markovGenerate(wordsList));


            try {

                Thread.sleep(300);

            } catch (InterruptedException e) {

            }

        } while (true);
    }




    }

