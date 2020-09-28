
public class DictionaryCommandLine {
    public static void showAllWords() {
        System.out.println("No\t" + "|English\t" + "|Vietnamese");
        for (Word word : Dictionary.words) {
            System.out.print((Dictionary.words.indexOf(word) + 1) + "\t");
            System.out.println(word.getInfo());
        }
    }

    public static void basicDictionary() {
        DictionaryManagement.insertFromCommandline();
        showAllWords();
    }

    public static void dictionaryAvanced() {
        DictionaryManagement.insertFromFile();
        showAllWords();
        DictionaryManagement.dictionaryLookup();
    }
}
