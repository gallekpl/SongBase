package pl.com.harta;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PrimaryController {

    @FXML
    MenuItem loadFile;
    @FXML
    MenuItem saveFile;
    List<File> files;
    @FXML
    TableView<Song> songTableView;
    @FXML
    TableColumn<Song, String> titleColumn;
    @FXML
    TableColumn<Song, String> authorColumn;
    @FXML
    TableColumn<Song, String> albumColumn;
    @FXML
    TableColumn<Song, Category> categoryColumn;
    @FXML
    TableColumn<Song, Integer> votesColumn;


    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        votesColumn.setCellValueFactory(new PropertyValueFactory<>("votes"));
        songTableView.getItems().setAll(SongList.getSongs().keySet());
    }

    @FXML
    private void addSong() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void loadFile() throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV and XML", "*.xml", "*.csv");
        fc.getExtensionFilters().add(extFilter);
        files = fc.showOpenMultipleDialog(new Stage());
        for (File file:files) {
            CSVReader csvReader = new CSVReader(file);
            csvReader.parseCSV();
        }
        songTableView.getItems().setAll(SongList.getSongs().keySet());
    }


}
