package pl.com.harta;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SaveCSV {

    private final File file;
    private final List<Song> list;

    public SaveCSV(File file, List<Song> list) {
        this.file = file;
        this.list = list;
    }

    public void saveFile() throws IOException {
        // just saves given songs into csv file
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.getPath()));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("Title", "Author", "Album", "Category", "Votes")); //adds header
        for (Song song : list) {
            csvPrinter.printRecord(song.getTitle(), song.getAuthor(), song.getAlbum(), song.getCategory(), song.getVotes());
        }
        csvPrinter.flush();
    }
}
