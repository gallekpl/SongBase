package pl.com.harta;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public class CSVReader {
    File file;
    SongRepositoryImpl songRepository;

    public CSVReader(File file) {
        this.file = file;
    }

    public void parseCSV() throws IOException {
        Reader reader = new FileReader(file);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .withIgnoreSurroundingSpaces()
                .withIgnoreEmptyLines());
        for (CSVRecord record : csvParser) {
            try {
                String title = record.get("Title");
                String author = record.get("Author");
                String album = record.get("Album");
                String categoryValue = record.get("Category");
                Category category = Category.valueOfLabel(categoryValue);
                int votes = Integer.parseInt(record.get("Votes"));
                Song song = new Song(title, author, album, category, votes);
                if (songRepository == null) {
                    songRepository = new SongRepositoryImpl();
                }
                songRepository.addSong(song);
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error has occurred");
                alert.setContentText("Wrong line nr: " + csvParser.getCurrentLineNumber() + "\nor wrong header line\nin file: " + file.getName() + ".\nReading next line if any.");
                alert.showAndWait();
            }
        }
    }
}
