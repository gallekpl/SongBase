package pl.com.harta;

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

    public CSVReader(File file) {
        this.file = file;
    }

    public List<Song> parseCSV() throws IOException {
        List<Song> songs = new ArrayList<>();
        Reader reader = new FileReader(file);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreSurroundingSpaces());
        for (CSVRecord record : csvParser) {
            String title = record.get("Title");
            String author = record.get("Author");
            String album = record.get("Album");
            String categoryValue = record.get("Category");
            Category category = Category.valueOfLabel(categoryValue);
            int votes = Integer.parseInt(record.get("Votes"));
            Song song = new Song(title, author, album, category , votes);
            if (!songs.contains(song)) songs.add(song);

        }
        return songs;
    }
}
