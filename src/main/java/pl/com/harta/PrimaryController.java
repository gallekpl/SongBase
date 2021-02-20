package pl.com.harta;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @FXML
    ChoiceBox<String> categoryChoiceBox;
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
        songTableView.sort();

        //if you already voted for a song in selection, vote button will get disabled,
        //to allow many votes for one song comment out next 2 lines
        selectedSongs.addListener((ListChangeListener<Song>) change ->
                voteButton.setDisable(!ListUtils.intersection(selectedSongs, alreadyVoted).isEmpty()));

        for (int i = 0; i < Category.values().length; i++) {
            categoryChoiceBox.getItems().add(Category.values()[i].toString());
        }
        categoryChoiceBox.getItems().add("All");
        categoryChoiceBox.getSelectionModel().select("All");
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> getSongsByCategory());
    }

    @FXML
    private void addSong() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void loadFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV and XML", "*.xml", "*.csv");
        fc.getExtensionFilters().add(extFilter);
        files = fc.showOpenMultipleDialog(new Stage());
        try {
            for (File file:files) {
                if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("csv")) {
                    CSVReader csvReader = new CSVReader(file);
                    csvReader.parseCSV();
                } else {
                    ReaderXML readerXML = new ReaderXML(file);
                    readerXML.parseXML();
                }
            }
        } catch (NullPointerException | IOException ignored) {} catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        ;
        songTableView.getItems().setAll(songRepository.getSongs().keySet());
    }

    @FXML
    private void saveFile() throws IOException, TransformerException, ParserConfigurationException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV and XML", "*.xml", "*.csv");
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showSaveDialog(new Stage());
        if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xml")) {
            SaveXML saveXML = new SaveXML(file, songTableView.getItems());
            saveXML.saveFile();
        } else {
            SaveCSV saveCSV = new SaveCSV(file, songTableView.getItems());
            saveCSV.saveFile();
        }

    }

    @FXML
    private void vote() {

        for (Song song:selectedSongs) {
            songRepository.addVoteToSong(song);
            alreadyVoted.add(song);
        }
        songTableView.getItems().setAll(songRepository.getSongs().keySet());
        songTableView.sort();
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
        alreadyVoted.clear();
        songTableView.getItems().setAll(songRepository.getSongs().keySet());


    }

    @FXML
    private void resetVotes() {
        for (Song song:selectedSongs) {
            songRepository.resetVotesInSong(song);
            alreadyVoted.remove(song);
        }
        songTableView.getItems().setAll(songRepository.getSongs().keySet());

    }

    @FXML
    private void getSongsByCategory() {
        if (categoryChoiceBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("all")) {
            songTableView.getItems().setAll(songRepository.getSongs().keySet());
        } else {
            songTableView.getItems().setAll(songRepository.getSongsByCategory(Category.valueOfLabel(categoryChoiceBox.getSelectionModel().getSelectedItem())));
        }
    }


}
