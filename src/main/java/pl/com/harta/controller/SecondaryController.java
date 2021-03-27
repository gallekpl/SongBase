package pl.com.harta.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import pl.com.harta.App;
import pl.com.harta.model.Category;
import pl.com.harta.model.Song;
import pl.com.harta.repository.SongRepositoryImpl;

import java.io.IOException;

//new controller for "creating new song" window
public class SecondaryController {

    @FXML
    private TextField titleTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField albumTextField;
    @FXML
    private ChoiceBox<Category> categoryChoiceBox;
    @FXML
    private TextField votesTextField;
    @FXML
    private Button addButton;
    private SongRepositoryImpl songRepository;



    public void initialize() {
        categoryChoiceBox.getItems().addAll(Category.values());
        categoryChoiceBox.getSelectionModel().select(Category.OTHER);
        addButton.setDisable(true);


    }

    @FXML
    private void switchToPrimary() throws IOException {
        Stage stage = (Stage) albumTextField.getScene().getWindow();
        stage.setHeight(600);
        stage.setWidth(935);
        stage.setTitle("SongBase");
        App.setRoot("primary");
    }

    @FXML
    private void addSong() throws IOException {
        //gets data from fields and creates new song
        Song song = new Song(titleTextField.getText(), authorTextField.getText(),
                albumTextField.getText(), categoryChoiceBox.getSelectionModel().getSelectedItem(), Integer.parseInt(votesTextField.getText()));
        if (songRepository == null) {
            songRepository = new SongRepositoryImpl();
        }
        songRepository.addSong(song);
        switchToPrimary();

    }

    @FXML
    private void onKeyReleased() {
        //if any fields are empty or votes is not a number - Add button is disabled
        String title;
        String author;
        String album;

        title = titleTextField.getText().trim();
        author = authorTextField.getText().trim();
        album = albumTextField.getText().trim();

        addButton.setDisable(!StringUtils.isNumeric(votesTextField.getText()) | title.isEmpty() | author.isEmpty() | album.isEmpty());


    }
}