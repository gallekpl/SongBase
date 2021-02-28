package pl.com.harta;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private TextField titleTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField albumTextField;
    @FXML
    private ChoiceBox<Category> categoryChoiceBox;
    private SongRepositoryImpl songRepository;


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