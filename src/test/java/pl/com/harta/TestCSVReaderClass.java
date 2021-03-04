package pl.com.harta;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestCSVReaderClass {

    static SongRepositoryImpl songRepository = new SongRepositoryImpl();

    @Test
    public void loadFileTest() throws IOException {
        File file = new File("src/test/resources/testfile.csv");
        CSVReader csvReader = new CSVReader(file);
        csvReader.parseCSV();
        assertFalse(songRepository.getSongs().values().isEmpty());
        assertEquals(15, songRepository.getSongs().size());
    }

    @Test
    public void shouldThrowFileNotFoundException() {
        File file = new File("src/test/resources/nonexisting.csv");
        CSVReader csvReader = new CSVReader(file);

        assertThrows(FileNotFoundException.class, csvReader::parseCSV);
    }


    @Test
    public void shouldCatchIllegalArgumentException() {
        File file = new File("src/test/resources/illegal.csv");
        CSVReader csvReader = new CSVReader(file);
        assertThrows(ExceptionInInitializerError.class, csvReader::parseCSV);
        //should throw that, because graphic UI is not initialized and Alert window can't be rendered. For that I should use TestFX library

        assertFalse(songRepository.getSongs().values().isEmpty());
    }

    @AfterAll
    static void clear() {
        songRepository.clear();
    }
}
