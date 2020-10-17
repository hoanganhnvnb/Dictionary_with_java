/**
 * Class Word co hai thuoc tinh la wordTarget va wordExplain
 * Mang cac doi tuong word duoc luu vao mang trong class Dictionary
 */
package Model;

public class Word {
    private String wordTarget;
    private String wordExplain;

    /**
     * Constructer 1
     */
    public Word() {
        this.wordTarget = "";
        this.wordExplain = "";
    }

    /**
     * Constructer 2
     * @param wordTarget = từ tiếng anh
     * giải nghĩa = empty string
     */
    public Word(String wordTarget) {
        this.wordTarget = wordTarget;
        this.wordExplain = "";
    }

    /**
     * Constructer 3
     * @param wordTarget từ mới
     * @param wordExplain giải nghĩa
     */
    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    /**
     * Copy word
     * @param w
     */
    public Word(Word w) {
        this.wordTarget = w.getWordTarget();
        this.wordExplain = w.getWordExplain();
    }

    public String getInfo() {
        return this.getWordTarget() + "\t\t" + this.getWordExplain();
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }
}
