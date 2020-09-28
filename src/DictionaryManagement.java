import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class DictionaryManagement {

    public static Scanner sc = new Scanner(System.in);

    /**
     * Nhap so tu muon them vao sau do them tu
     */
    public static void insertFromCommandline() {
        System.out.print("Input numbers of word: ");
        int numWord = sc.nextInt();
        sc.nextLine();
        Word word;
        for(int i = 0; i < numWord; ++i) {
            System.out.print("Input word target: ");
            String wordTarget = sc.nextLine();
            System.out.print("Input word explain: ");
            String wordExplain = sc.nextLine();
            word = new Word(wordTarget, wordExplain);
            Dictionary.words.add(word);
        }
    }

    /**
     *  Insert cac tu trong file dictionaries.txt
     */
    public static void insertFromFile() {
        try {
            File file = new File("./data/dictionaries.txt");
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
     * Tim tu bang commandline
     * Nhập 1 từ tiếng anh hoặc một phần của từ tiếng anh
     * @return trả về list các từ bằng hoặc chứa từ đó
     */

    public static void dictionaryLookup() {
        System.out.print("Enter the word you want to find: ");
        String wordTargetFind = sc.nextLine();
        ArrayList<Word> wordFindArr = new ArrayList<Word>();

        for (Word word : Dictionary.words) {
            if(word.getWordTarget().toLowerCase().indexOf(wordTargetFind.toLowerCase()) != -1) {
                Word wordFind = new Word(word);
                wordFindArr.add(wordFind);
            }
        }

        if(wordFindArr.size() == 0) {
            System.out.println("Word Not Found!!!!!!!");
        } else {
            for (Word word : wordFindArr) {
                System.out.println(word.getInfo());
            }
        }
    }


}
