package pl.com.harta;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    TextField titleTextField;
    @FXML
    TextField authorTextField;
    @FXML
    TextField albumTextField;
    @FXML
    TextField categoryTextField;
    SongRepositoryImpl songRepository;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void addSong() throws IOException {
        Song song = new Song(titleTextField.getText(), authorTextField.getText(),
                albumTextField.getText(), Category.valueOfLabel(categoryTextField.getText()), 0);
        if (songRepository==null) {
            songRepository = new SongRepositoryImpl();
        }
        songRepository.addSong(song);
        App.setRoot("primary");


    }
}