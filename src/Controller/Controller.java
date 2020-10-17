package Controller;

import Model.Dictionary;
import Model.Word;
import View.DictionaryApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.net.URL;
import java.util.*;

import static Model.DictionaryManagement.*;


public class Controller implements Initializable {

    private List<Word> wordsFind = Dictionary.words;

    private String textSpeech = "";

    private Word wordEdit;

    @FXML
    private TextField Search;

    @FXML
    private ListView<String> WordTarget;

    @FXML
    private TextArea WordExplain;

    @FXML
    private Label numOfWord;

    @FXML
    private Button editButton;

    @FXML
    private Button Speak;

    private String[] textExplain = new String[3];

    ObservableList<String> listWordTarget = FXCollections.observableArrayList();
    ObservableList<String> listWordExplain = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        editButton.setDisable(true);
        Speak.setDisable(true);
        WordExplain.setEditable(false);

        // add listener cho thanh search
        try {
            Search.textProperty().addListener((observable, oldValue, newValue) -> {
                wordsFind = DictionaryCommandLine.dictionarySearcher(newValue.trim());
                loadData();
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Speak.setOnMouseClicked(event -> {
            textToSpeech(textSpeech);
        });

        // Them cach hien thi khi click vao cac Wordtarget
        WordTarget.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        try {
            WordTarget.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Speak.setDisable(false);
                editButton.setDisable(false);
                String s = listWordExplain.get(listWordTarget.indexOf(newValue));
                if (!newValue.equals("NOT FOUND!")) {
                    if (s.lastIndexOf(".") > -1) {
                        textExplain[0] = s.substring(0, s.lastIndexOf(".") + 1);
                    } else {
                        textExplain[0] = "UNKNOWN";
                    }
                    if (s.indexOf("/") > - 1 && s.lastIndexOf("/") > -1) {
                        textExplain[1] = s.substring(s.indexOf("/"), s.lastIndexOf("/") + 1);
                        textExplain[2] = s.substring(s.lastIndexOf("/") + 1);
                    } else {
                        textExplain[1] = "/UNKNOWN/";
                        textExplain[2] = s;
                    }
                    WordExplain.setText(newValue + ":\n" + textExplain[1] + "\n"
                            + "( " + textExplain[0] + ") " + textExplain[2]);
                } else {
                    WordExplain.setText(newValue);
                }
                textSpeech = newValue;
                wordEdit = new Word(newValue, s);

            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        loadData();
    }

    private void loadData() {
        WordTarget.getItems().clear();
        listWordTarget.removeAll(listWordTarget);
        listWordExplain.removeAll(listWordExplain);
        for (Word word : wordsFind) {
            listWordTarget.add(word.getWordTarget());
            listWordExplain.add(word.getWordExplain());
        }
        if (wordsFind.size() == 0) {
            listWordTarget.add("NOT FOUND!");
        }
        WordTarget.getItems().addAll(listWordTarget);
        numOfWord.setText(wordsFind.size() + " / " + Dictionary.words.size() + " words");
    }

    public void clickEdit(ActionEvent event) {
        DictionaryApplication.createDialogEdit(wordEdit);
        Optional<Pair<String, String>> result = DictionaryApplication.dialogEdit.showAndWait();

        result.ifPresent(word -> {
            editWord(wordEdit.getWordTarget(), word.getKey(), word.getValue());
        });
        wordsFind = DictionaryCommandLine.dictionarySearcher(Search.getText().trim());
        loadData();
    }

    public void clickAdd(ActionEvent event) {
        DictionaryApplication.createDialogAdd();
        Optional<Pair<String, String>> result = DictionaryApplication.dialogAdd.showAndWait();

        result.ifPresent(word -> addWord(word.getKey(), word.getValue()));
        wordsFind = DictionaryCommandLine.dictionarySearcher(Search.getText().trim());
        loadData();
    }

    /**
     * click vào remove sẽ gọi hàm tạo dialog.
     * Sau khi nhập sữ liệu, nếu tìm tháy từ cần xóa thì sẽ xóa, còn không thì thông báo.
     * @param event sự kiện
     */
    public void clickRemove(ActionEvent event) {
        DictionaryApplication.createDialogSub();
        Optional<Pair<String, String>> result = DictionaryApplication.dialogSub.showAndWait();

        result.ifPresent(word -> {
            boolean isRemove = removeWord(word.getKey());
            if (!isRemove) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("NOT FOUND WORD!!!");
                alert.setContentText("Not found word");
                alert.setTitle("Not Remove");
                alert.show();
            }
        });
        wordsFind = DictionaryCommandLine.dictionarySearcher(Search.getText().trim());
        loadData();
    }


    /**
     * Đầu vào là từ cần nói.
     * @param text từ cần nói.
     * @return 1. sau khi nói trả về 1 để tránh không thoát được luồng input...
     */
    public static int textToSpeech(String text) {
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");
            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");
            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            // Speaks the given text
            // until the queue is empty.
            synthesizer.speakPlainText(
                    text, null);
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}