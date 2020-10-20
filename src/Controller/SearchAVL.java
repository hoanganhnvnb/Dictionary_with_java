package Controller;

import Model.AVLNode;
import Model.Dictionary;
import Model.Word;

import java.util.ArrayList;
import java.util.List;

public class SearchAVL {
    public static List<Word> listFind = new ArrayList<>();

    public static List<Word> dictionarySearcher(String target) {
        listFind.clear();
        search(nodeSearch(Dictionary.wordTree.getRoot(), target), target);
        return listFind;
    }

    public static void search(AVLNode rootSearch, String target) {
        if (rootSearch != null) {
            search(rootSearch.getLeft(), target);
            if (rootSearch.getWord().getWordTarget().toLowerCase()
                    .startsWith(target.toLowerCase()))
                listFind.add(rootSearch.getWord());
            search(rootSearch.getRight(), target);
        }
    }

    private static AVLNode nodeSearch(AVLNode node, String target) {
        if (node == null) {
            return null;
        }
        if (node.getWord().getWordTarget().toLowerCase().startsWith(target.toLowerCase())) {
            return node;
        } else if (node.getWord().getWordTarget().toLowerCase()
                .compareTo(target.toLowerCase()) > 0) {
            return nodeSearch(node.getLeft(), target);
        } else if (node.getWord().getWordTarget().toLowerCase()
                .compareTo(target.toLowerCase()) < 0){
            return nodeSearch(node.getRight(), target);
        }
        return null;
    }
}
