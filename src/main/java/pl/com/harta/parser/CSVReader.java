package pl.com.harta.parser;

import javafx.scene.control.Alert;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import pl.com.harta.model.Category;
import pl.com.harta.model.Song;
import pl.com.harta.repository.SongRepositoryImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class CSVReader {
    private final File file;
    private SongRepositoryImpl songRepository;

    public CSVReader(File file) {
        this.file = file;
    }

    public void parseCSV() throws IOException {
        Reader reader = new FileReader(file);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .withIgnoreSurroundingSpaces() //ignore whitespaces in front of and behind text
                .withIgnoreEmptyLines()); //ignore empty lines
        for (CSVRecord record : csvParser) {
            try {
                //getting songs from csv
                String title = record.get("Title");
                String author = record.get("Author");
                String album = record.get("Album");
                String categoryValue = record.get("Category");
                Category category = Category.valueOfLabel(categoryValue); //changes string to Category
                int votes = Integer.parseInt(record.get("Votes"));
                Song song = new Song(title, author, album, category, votes);
                if (songRepository == null) {
                    songRepository = new SongRepositoryImpl();
                }
                songRepository.addSong(song);
            } catch (IllegalArgumentException e) {  //catches error and opens new JavaFx window
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error has occurred");
                alert.setContentText("Wrong line nr: " + csvParser.getCurrentLineNumber() + "\nor wrong header line\nin file: " + file.getName() + ".\nReading next line if any.");
                alert.showAndWait(); //waits until user reads the message and clicks OK.
            }
        }
    }
}
