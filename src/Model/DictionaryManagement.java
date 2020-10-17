package Model;

import java.util.Collections;
import java.util.Comparator;
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

        Dictionary.words.sort((o1, o2) -> {
            return o1.getWordTarget().toLowerCase().compareTo(o2.getWordTarget().toLowerCase());
        });
    }


    /**
     *  Insert các từ trong file dictionaries.txt...
     */
    public static void insertFromFile() {
        try {
            File file = new File("./data/dictionaries.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String line;
            String[] str = new String[2];

            while ((line = buffReader.readLine()) != null) {
                str[0] = line.substring(0, line.indexOf(":"));
                str[1] = line.substring(line.indexOf(":") + 1);
                Dictionary.words.add(new Word(str[0].trim(), str[1].trim()));
            }
            fileReader.close();
            buffReader.close();
        } catch (Exception exception) {
            System.out.println("Error: " + exception);
        }

        Dictionary.words.sort((o1, o2) -> {
            return o1.getWordTarget().toLowerCase().compareTo(o2.getWordTarget().toLowerCase());
        });
    }


    /**
     * Suất ra file dictionaries.txt.
     */
    public static void dictionaryExportToFile() {
        Dictionary.words.sort((o1, o2) -> {
            return o1.getWordTarget().toLowerCase().compareTo(o2.getWordTarget().toLowerCase());
        });
        try {
            FileWriter file = new FileWriter("./data/dictionaries.txt");
            for(Word word : Dictionary.words) {
                file.write((word.getWordTarget() + ":\t" + word.getWordExplain()));
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
        Comparator<Word> cmp = new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return o1.getWordTarget().toLowerCase().compareTo(o2.getWordTarget().toLowerCase());
            }
        };

        return Collections.binarySearch(Dictionary.words, new Word(wordTargetFind, ""), cmp);
    }


    /**
     * ADD Word.
     */
    public static void addWord(String wordTarget, String wordExplain) {
        Dictionary.words.add(new Word(wordTarget, wordExplain));
        dictionaryExportToFile();
    }


    /**
     * Remove Word.
     */
    public static boolean removeWord(String wordTarget) {
        int index = dictionaryLookup(wordTarget);
        if (index > -1) {
            Dictionary.words.remove(Dictionary.words.get(index));
            dictionaryExportToFile();
            return true;
        }
        return false;
    }

    /**
     * Fixed word.
     */
    public static void editWord(String oldTarget, String newTarget, String newExplain) {
        int index = DictionaryManagement.dictionaryLookup(oldTarget);
        if (index > -1) {
            Dictionary.words.get(index).setWordTarget(newTarget);
            Dictionary.words.get(index).setWordExplain(newExplain);
            dictionaryExportToFile();
        }
    }
}
