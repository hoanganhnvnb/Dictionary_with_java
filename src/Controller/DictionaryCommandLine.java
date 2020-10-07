package Controller;

import java.util.ArrayList;
import Model.*;

public class DictionaryCommandLine {
    public static void showAllWords() {
        System.out.println("No\t" + "|English\t" + "|Vietnamese");
        for (Word word : Dictionary.words) {
            System.out.print((Dictionary.words.indexOf(word) + 1) + "\t");
            System.out.println(word.getInfo());
        }
    }

    /**
     * Call các hàm insertFromCommandline.
     * Show all word.
     */
    public static void basicDictionary() {
        DictionaryManagement.insertFromCommandline();
        showAllWords();
    }

    /**
     * Call insertFromFile and dictionaryLookup of DictionaryManagement.
     * Show all word...
     */
    public static void dictionaryAvanced() {
        DictionaryManagement.insertFromFile();
        showAllWords();

        // call dictionaryLookup
        System.out.print("Enter the word you want to search: ");
        String wordTargetFind = DictionaryManagement.sc.nextLine();
        int index = DictionaryManagement.dictionaryLookup(wordTargetFind);
        if (index != -1) {
            System.out.println(Dictionary.words.get(index).getInfo());
        }
        dictionarySearcher();
        DictionaryManagement.dictionaryExportToFile();
    }

     /**
     * Tim tu bang commandline.
     * Nhập 1 từ tiếng anh hoặc một phần của từ tiếng anh.
     * @return trả về list các từ bằng hoặc chứa từ đó...
     */
    public static void dictionarySearcher() {
        System.out.print("Enter the word you want to search: ");
        String wordTargetFind = DictionaryManagement.sc.nextLine();
        ArrayList<Word> wordFindArr = new ArrayList<Word>();
        
        // add các từ tìm được phù hợp vào mảng mới.
        for (Word word : Dictionary.words) {
            if(word.getWordTarget().toLowerCase().indexOf(wordTargetFind.toLowerCase()) != -1) {
                Word wordFind = new Word(word);
                wordFindArr.add(wordFind);
            }
        }

        // nếu các mảng có 0 phần tử nghĩa là không tìm được gì.
        if(wordFindArr.size() == 0) {
            System.out.println("Word Not Found!!!!!!!");
        } else {
            for (Word word : wordFindArr) {
                System.out.println(word.getInfo());
            }
        }
    }
}


