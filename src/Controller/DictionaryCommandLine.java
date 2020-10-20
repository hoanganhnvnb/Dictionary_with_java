package Controller;

import java.util.ArrayList;
import java.util.List;

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
        int index = 0;
        if (index != -1) {
            System.out.println(Dictionary.words.get(index).getInfo());
        }
        DictionaryManagement.dictionaryExportToFile();
    }

     /**
     * Tim tu bang commandline.
     * Nhập 1 từ tiếng anh hoặc một phần của từ tiếng anh.
     * @return trả về list các từ bằng hoặc chứa từ đó...
     */
    public static List<Word> dictionarySearcher(String wordTargetFind) {
        List<Word> wordFindArr = new ArrayList<Word>();
        
        // add các từ tìm được phù hợp vào mảng mới.
        for (Word word : Dictionary.words) {
            if(word.getWordTarget().toLowerCase().startsWith(wordTargetFind.toLowerCase())) {
                Word wordFind = new Word(word);
                wordFindArr.add(wordFind);
            }
        }
        return wordFindArr;
    }
}


