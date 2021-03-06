package pl.com.harta.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import pl.com.harta.*;
import pl.com.harta.model.Category;
import pl.com.harta.model.Song;
import pl.com.harta.model.SongList;
import pl.com.harta.parser.CSVReader;
import pl.com.harta.parser.ReaderXML;
import pl.com.harta.parser.SaveCSV;
import pl.com.harta.parser.SaveXML;
import pl.com.harta.repository.SongRepositoryImpl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrimaryController {

    private SongRepositoryImpl songRepository;
    @FXML
    private TableView<Song> songTableView;
    @FXML
    private TableColumn<Song, String> titleColumn;
    @FXML
    private TableColumn<Song, String> authorColumn;
    @FXML
    private TableColumn<Song, String> albumColumn;
    @FXML
    private TableColumn<Song, Category> categoryColumn;
    @FXML
    private TableColumn<Song, Integer> votesColumn;
    @FXML
    private Button voteButton;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    private ObservableList<Song> selectedSongs;
    // uncomment bellow line if using listener for voting only once (in initialize())
    // private final List<Song> alreadyVoted = new ArrayList<>();
    @FXML
    private ChoiceBox<String> showChoiceBox;


    //initializing table columns, listeners, etc. on window start
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        votesColumn.setCellValueFactory(new PropertyValueFactory<>("votes"));
        songTableView.getItems().setAll(SongList.getSongs().keySet());
        songTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        if (songRepository == null) songRepository = new SongRepositoryImpl();
        selectedSongs = songTableView.getSelectionModel().getSelectedItems();
        votesColumn.setSortType(TableColumn.SortType.DESCENDING);
        songTableView.getSortOrder().add(votesColumn);
        songTableView.sort();

        //if you already voted for a song in selection, vote button will get disabled,
        //to allow many votes for one song comment out next line

        //selectedSongs.addListener((ListChangeListener<Song>) change -> voteButton.setDisable(!ListUtils.intersection(selectedSongs, alreadyVoted).isEmpty()));


        for (int i = 0; i < Category.values().length; i++) {
            categoryChoiceBox.getItems().add(Category.values()[i].toString());
        }
        categoryChoiceBox.getItems().add("All");
        categoryChoiceBox.getSelectionModel().select("All");
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> getSongsByCategory());

        showChoiceBox.getItems().add("Top 3");
        showChoiceBox.getItems().add("Top 10");
        showChoiceBox.getItems().add("All");
        showChoiceBox.getSelectionModel().select("All");
        showChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> showTop());
    }

    @FXML
    private void addSong() throws IOException {

        Stage stage = (Stage) voteButton.getScene().getWindow();
        stage.setHeight(450);
        stage.setWidth(350);
        stage.setTitle("Add a song");
        App.setRoot("secondary");
    }

    @FXML
    private void loadFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV and XML", "*.XML", "*.CSV", "*.csv", "*.xml");
        fc.getExtensionFilters().add(extFilter);
        List<File> files = fc.showOpenMultipleDialog(new Stage());
        try {
            for (File file : files) {
                //checks if file is csv or xml then uses appropriate method
                if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("csv")) {
                    CSVReader csvReader = new CSVReader(file);
                    csvReader.parseCSV();
                } else {
                    ReaderXML readerXML = new ReaderXML(file);
                    readerXML.parseXML();
                }
            }
        } catch (NullPointerException | IOException ignored) {
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        showChoiceBox.getSelectionModel().select("All");
        getSongsByCategory();
    }

    @FXML
    private void saveFile() throws IOException, TransformerException, ParserConfigurationException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV and XML", "*.xml", "*.csv");
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showSaveDialog(new Stage());
        if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xml")) {
            /*  if extension is xml (any case) then save as xml file,
                otherwise save as csv (even if extension
                is something different)  */
            SaveXML saveXML = new SaveXML(file, songTableView.getItems());
            saveXML.saveFile();
        } else {
            SaveCSV saveCSV = new SaveCSV(file, songTableView.getItems());
            saveCSV.saveFile();
        }

    }

    @FXML
    private void vote() {

        for (Song song : selectedSongs) {
            songRepository.addVoteToSong(song);
            //uncomment for voting only once
            //alreadyVoted.add(song);
        }
        showTop();
    }

    @FXML
    private void removeSong() {

        for (Song song : selectedSongs) {
            songRepository.deleteSong(song);
        }
        showTop();
    }

    @FXML
    private void removeAllVotes() {

        //removes all votes from all songs in visible list (ex. Top 10)
        //can be changed to remove votes from all songs in repository
        for (Song song : songTableView.getItems()) {
            songRepository.resetVotesInSong(song);
        }
        //uncomment for voting only once
        //alreadyVoted.clear();
        showTop();


    }

    @FXML
    private void resetVotes() {
        for (Song song : selectedSongs) {
            songRepository.resetVotesInSong(song);
            //uncomment for voting only once
            //alreadyVoted.remove(song);
        }
        showTop();

    }

    @FXML
    private void getSongsByCategory() {
        if (categoryChoiceBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("all")) {
            songTableView.getItems().setAll(songRepository.getSongs().keySet());
        } else {
            songTableView.getItems().setAll(songRepository.getSongsByCategory(Category.valueOfLabel(categoryChoiceBox.getSelectionModel().getSelectedItem())));
        }
        songTableView.getSortOrder().add(votesColumn);
        songTableView.sort();

    }

    @FXML
    private void showTop() {
        List<Song> top = new ArrayList<>();  //list of songs that has to be shown

        switch (showChoiceBox.getSelectionModel().getSelectedItem()) {
            case "Top 3" -> {
                songTableView.getSortOrder().clear();
                getSongsByCategory();
                try {
                    top.add(songTableView.getItems().get(0));
                    top.add(songTableView.getItems().get(1));
                    top.add(songTableView.getItems().get(2));
                    songTableView.getItems().setAll(top);
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            case "Top 10" -> {
                songTableView.getSortOrder().clear();
                getSongsByCategory();
                for (int i = 0; i < 10; i++) {
                    try {
                        top.add(songTableView.getItems().get(i));
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
                songTableView.getItems().setAll(top);
            }
            case "All" -> {
                songTableView.getSortOrder().clear();
                getSongsByCategory();
            }
        }
    }

    @FXML
    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("About");
        alert.setHeaderText("SongBase");
        alert.setContentText("Program for song management.\nWritten by Ariel Gał as a recruitment task\nfor Core Services Java Bootcamp 2021.");
        alert.showAndWait();
    }




}
