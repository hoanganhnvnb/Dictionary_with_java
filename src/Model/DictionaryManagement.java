package Model;

import java.util.Scanner;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
            Dictionary.wordTree.add(word);
        }
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
                Dictionary.wordTree.add(new Word(str[0].trim(), str[1].trim()));
            }
            fileReader.close();
            buffReader.close();
        } catch (Exception exception) {
            System.out.println("Error: " + exception);
        }
        Dictionary.words.clear();
        treeToList(Dictionary.wordTree.getRoot());
    }


    /**
     * Suất ra file dictionaries.txt.
     */
    public static void dictionaryExportToFile() {
        Dictionary.words.clear();
        treeToList(Dictionary.wordTree.getRoot());
        try {
            FileWriter file = new FileWriter("./data/dictionaries.txt");
            for(Word word : Dictionary.words) {
                file.write((word.getWordTarget() + ":\t" + word.getWordExplain()));
                file.write("\n");
            }
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tim từ đã nhập vào theo commandline.
     */
    public static AVLNode dictionaryLookup(String wordTargetFind) {
        return Dictionary.wordTree.search(new Word(wordTargetFind, ""));
    }


    /**
     * ADD Word.
     */
    public static boolean addWord(String wordTarget, String wordExplain) {
        boolean isAdd = Dictionary.wordTree.add(new Word(wordTarget, wordExplain));
        dictionaryExportToFile();
        return isAdd;
    }


    /**
     * Remove Word.
     */
    public static boolean removeWord(String wordTarget) {
        boolean isRemove = Dictionary.wordTree.delete(new Word(wordTarget, ""));
        dictionaryExportToFile();
        return isRemove;
    }

    /**
     * Fixed word.
     */
    public static void editWord(String oldTarget, String newTarget, String newExplain) {
        AVLNode node = dictionaryLookup(oldTarget);
        if (node != null) {
            node.setWord(new Word(newTarget, newExplain));
            dictionaryExportToFile();
        }
    }

    public static void treeToList(AVLNode node) {
        if (node != null) {
            treeToList(node.getLeft());
            Dictionary.words.add(node.getWord());
            treeToList(node.getRight());
        }
    }

    private static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbzz8A2jsZN4709_fAP-fUawFVRx4esbdkTFUd742c5TOJaUJEE/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
}
