import java.util.Scanner;

public class DictionaryManagement {

    public static Scanner sc = new Scanner(System.in);

    /**
     * Nhap so tu muon them vao sau do them tu
     */
    public static void insertFromCommandline() {
        System.out.print("Input numbers of word: ");
        int numWord = sc.nextInt();
        String buffer = sc.nextLine();
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
}
