package Model;

public class AVLNode {
    private Word word;
    private int height;
    private AVLNode left;
    private AVLNode right;

    public AVLNode(Word word) {
        this.word = new Word(word);
        this.height = 1;
        this.left = null;
        this.right = null;
    }

    public boolean greaterThan(Word word) {
        return this.word.getWordTarget()
                .toLowerCase()
                .compareTo(word.getWordTarget().toLowerCase()) > 0;
    }

    public boolean lessThan(Word word) {
        return this.word.getWordTarget()
                .toLowerCase()
                .compareTo(word.getWordTarget().toLowerCase()) < 0;
    }

    public boolean equal(Word word) {
        return this.word.getWordTarget()
                .toLowerCase()
                .compareTo(word.getWordTarget().toLowerCase()) == 0;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int getHeight() {
        return this == null ? 0 : height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public AVLNode getLeft() {
        return left;
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public AVLNode getRight() {
        return right;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }
}
