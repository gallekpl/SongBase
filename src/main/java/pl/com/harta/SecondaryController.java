package pl.com.harta;

import java.io.IOException;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    TextField titleTextField;
    @FXML
    TextField authorTextField;
    @FXML
    TextField albumTextField;
    @FXML
    ChoiceBox<Category> categoryChoiceBox;
    SongRepositoryImpl songRepository;


    public void initialize()  {
    categoryChoiceBox.getItems().addAll(Category.values());
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void addSong() throws IOException {

        Song song = new Song(titleTextField.getText(), authorTextField.getText(),
                albumTextField.getText(), categoryChoiceBox.getSelectionModel().getSelectedItem(), 0);
        if (songRepository==null) {
            songRepository = new SongRepositoryImpl();
        }
        songRepository.addSong(song);
        App.setRoot("primary");


    }
}