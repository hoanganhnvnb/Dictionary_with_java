
public class DictionaryCommandLine {
    public static void showAllWords() {
        System.out.println("No\t" + "|English\t" + "|Vietnamese");
        for (Word word : Dictionary.words) {
            System.out.print((Dictionary.words.indexOf(word) + 1) + "\t");
            System.out.println("|" + word.getWordTarget() + "\t\t|" + word.getWordExplain());
        }
    }

    public static void basicDictionary() {
        DictionaryManagement.insertFromCommandline();
        showAllWords();
    }
}
