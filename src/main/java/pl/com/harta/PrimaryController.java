package pl.com.harta;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.collections.ListUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PrimaryController {

    SongRepositoryImpl songRepository;
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
    @FXML
    Button voteButton;
    ObservableList<Song> selectedSongs;
    List<Song> alreadyVoted = new ArrayList<>();


    //initializng table columns on window start
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        votesColumn.setCellValueFactory(new PropertyValueFactory<>("votes"));
        songTableView.getItems().setAll(SongList.getSongs().keySet());
        songTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        if (songRepository==null) songRepository = new SongRepositoryImpl();
        selectedSongs = songTableView.getSelectionModel().getSelectedItems();

        //if you already voted for a song in selection vote button will get disabled,
        //to allow many votes for one song comment out next 2 lines
        selectedSongs.addListener((ListChangeListener<Song>) change ->
                voteButton.setDisable(!ListUtils.intersection(selectedSongs, alreadyVoted).isEmpty()));
    }

    @FXML
    private void addSong() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void loadFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV and XML", "*.xml", "*.csv");

        files = fc.showOpenMultipleDialog(new Stage());
        try {
            for (File file:files) {
                CSVReader csvReader = new CSVReader(file);
                csvReader.parseCSV();
            }
        } catch (NullPointerException | IOException ignored) {};
        songTableView.getItems().setAll(songRepository.getSongs().keySet());
    }

    @FXML
    private void vote() {

        for (Song song:selectedSongs) {
            songRepository.addVoteToSong(song);
            alreadyVoted.add(song);
        }
        songTableView.getItems().setAll(songRepository.getSongs().keySet());
    }

    @FXML
    private void removeSong() {

        for (Song song:selectedSongs) {
            songRepository.deleteSong(song);
        }
        songTableView.getItems().setAll(songRepository.getSongs().keySet());
    }

    @FXML
    private void removeAllVotes() {
        for (Song song:songTableView.getItems()) {
            songRepository.resetVotesInSong(song);
        }
        songTableView.getItems().setAll(songRepository.getSongs().keySet());

    }

    @FXML
    private void resetVotes() {
        for (Song song:selectedSongs) {
            songRepository.resetVotesInSong(song);
        }
        songTableView.getItems().setAll(songRepository.getSongs().keySet());
    }


}
