package Model;

import java.util.Scanner;
import java.io.*;

public class DictionaryManagement {

    public static Scanner sc = new Scanner(System.in);


    /**
     * Hàm nhập numWord muốn thêm sau đó thêm.
     */
    public static void insertFromCommandline() {
        System.out.print("Input numbers of word: ");
        int numWord = sc.nextInt();
        sc.nextLine();
        Word word;
        for (int i = 0; i < numWord; ++i) {
            System.out.print("Input word target: ");
            String wordTarget = sc.nextLine();
            System.out.print("Input word explain: ");
            String wordExplain = sc.nextLine();
            word = new Word(wordTarget, wordExplain);
            Dictionary.words.add(word);
        }
    }


    /**
     *  Insert các từ trong file dictionaries.txt...
     */
    public static void insertFromFile() {
        try {
            File file = new File("../data/dictionaries.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String line;
            String[] str = new String[2];

            while ((line = buffReader.readLine()) != null) {
                str = line.split("    ");
                Word word = new Word(str[0], str[1]);
                Dictionary.words.add(word);
            }
            fileReader.close();
            buffReader.close();
        } catch (Exception exception) {
            System.out.println("Error: " + exception);
        }
    }


    /**
     * Suất ra file dictionaries.txt.
     */
    public static void dictionaryExportToFile() {
        try {
            FileWriter file = new FileWriter("./data/dictionaries.txt");
            for(Word word : Dictionary.words) {
                file.write((word.getWordTarget() + "    " + word.getWordExplain()));
                file.write("\n");
            }
            file.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    /**
     * Tim từ đã nhập vào theo commandline.
     */
    public static int dictionaryLookup(String wordTargetFind) {
        for (Word word : Dictionary.words) {
            if (wordTargetFind.toLowerCase().equals(word.getWordTarget().toLowerCase())) {
               return Dictionary.words.indexOf(word);
            }
        }
        System.out.println("Not Found!");
        return -1;
    }


    /**
     * ADD Word.
     */
    public static void addWord() {
        System.out.print("Input word target want to add : ");
        String wordTarget = sc.nextLine();
        System.out.print("Input word explain want to add : ");
        String wordExplain = sc.nextLine();
        Dictionary.words.add(new Word(wordTarget, wordExplain));
    }


    /**
     * Remove Word.
     */
    public static void removeWord() {
        System.out.print("Input word target want to remove : ");
        String wordTarget = sc.nextLine();
        int index = dictionaryLookup(wordTarget);
        if (index != -1) {
            Dictionary.words.remove(Dictionary.words.get(index));
        }
    }

    /**
     * Fixed word.
     */
    public static void fixWord() {
        System.out.print("Input word want to fix : ");
        String wordTargetFix = sc.nextLine();
        int index = dictionaryLookup(wordTargetFix);
        if (index != -1) {
            System.out.print("Input word target: ");
            String wordTarget = sc.nextLine();
            System.out.print("Input word explain: ");
            String wordExplain = sc.nextLine();
            Dictionary.words.get(index).setWordTarget(wordTarget);
            Dictionary.words.get(index).setWordExplain(wordExplain);
        }
    }
}
