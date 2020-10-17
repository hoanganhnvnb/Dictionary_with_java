package View;

import Model.Dictionary;
import Model.DictionaryManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.xml.soap.Text;


public class DictionaryApplication extends Application {

    public static Dialog<Pair<String, String>> dialogAdd;
    public static Dialog<Pair<String, String>> dialogSub;
    public static Dialog<Pair<String, String>> dialogEdit;

    private ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    private ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
    private ButtonType buttonTypeCancel = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);

    public static void main(String[] args) {
        DictionaryManagement.insertFromFile();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("DictionaryApplication.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("./DictionaryApplication.css").toExternalForm());
            primaryStage.setTitle("Dictionary");
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createDialogAdd() {
        dialogAdd = new Dialog<>();
        dialogAdd.setTitle("Adding Word");
        dialogAdd.setHeaderText("Add Word");

        dialogAdd.getDialogPane().getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

        // set layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField wordTarget = new TextField();
        wordTarget.setPromptText("Input Word target");
        TextField wordExplain = new TextField();
        wordExplain.setPromptText("Input Word Explain");

        grid.add(new Label("Word Target:"), 0, 0);
        grid.add(wordTarget, 1, 0);
        grid.add(new Label("Word Explain:"), 0, 1);
        grid.add(wordExplain, 1, 1);

        dialogAdd.getDialogPane().setContent(grid);

        Node buttonYes = dialogAdd.getDialogPane().lookupButton(buttonTypeYes);
        buttonYes.setDisable(true);

        wordTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            buttonYes.setDisable(newValue.trim().isEmpty());
        });

        wordExplain.textProperty().addListener((observable, oldValue, newValue) -> {
            buttonYes.setDisable(newValue.trim().isEmpty());
        });

        dialogAdd.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeYes) {
                return new Pair<>(wordTarget.getText(), wordExplain.getText());
            }
            return null;
        });
    }

    public void createDialogSub() {
        dialogSub = new Dialog<>();
        dialogSub.setTitle("Remove Word");
        dialogSub.setHeaderText("Remove Word");

        dialogSub.getDialogPane().getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

        // set layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField wordTarget = new TextField();
        wordTarget.setPromptText("Input Word target");

        grid.add(new Label("Word Target:"), 0, 0);
        grid.add(wordTarget, 1, 0);

        dialogSub.getDialogPane().setContent(grid);

        Node buttonYes = dialogSub.getDialogPane().lookupButton(buttonTypeYes);
        buttonYes.setDisable(true);

        wordTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            buttonYes.setDisable(newValue.trim().isEmpty());
        });

        dialogSub.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeYes) {
                return new Pair<>(wordTarget.getText(), wordTarget.getText());
            }
            return null;
        });
    }

    public void createDialogEdit() {
        dialogEdit = new Dialog<>();
        dialogEdit.setTitle("Edit Word");
        dialogEdit.setHeaderText("Edit Word");

        dialogEdit.getDialogPane().getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

        // set layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Button btnSearch = new Button("Search");
        btnSearch.setDisable(true);

        TextField newWordTarget = new TextField();
        newWordTarget.setPromptText("Word Target Edit");
        TextField newWordExplain = new TextField();
        newWordExplain.setPromptText("Word Explain Edit");

        grid.add(new Label("Word Target Edit:"), 0, 0);
        grid.add(newWordTarget, 0, 1);
        grid.add(btnSearch, 1, 1);
        grid.add(new Label("Word Explain Edit:"), 0, 2);
        grid.add(newWordExplain, 1, 2);

        dialogAdd.getDialogPane().setContent(grid);

        Node buttonYes = dialogAdd.getDialogPane().lookupButton(buttonTypeYes);
        buttonYes.setDisable(true);

        newWordTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            buttonYes.setDisable(newValue.trim().isEmpty());
        });

        btnSearch.setOnAction(event -> {
            int index = DictionaryManagement.dictionaryLookup(newWordTarget.getText());
            if (index > -1) {
                newWordExplain.setText(Dictionary.words.get(index).getWordExplain());
            } else {
                newWordExplain.setText("NOT FOUND!");
            }
        });

        dialogAdd.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeYes && !newWordTarget.getText().equals("NOT FOUND!")) {
                return new Pair<>(newWordTarget.getText(), newWordExplain.getText());
            }
            return null;
        });
    }
}
