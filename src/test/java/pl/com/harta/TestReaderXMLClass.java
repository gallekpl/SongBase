package pl.com.harta;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestReaderXMLClass {

    static SongRepositoryImpl songRepository = new SongRepositoryImpl();

    @Test
    public void loadFileTest() throws ParserConfigurationException, IOException {
        File file = new File("src/test/resources/testfile.xml");
        ReaderXML readerXML = new ReaderXML(file);
        readerXML.parseXML();
        assertFalse(songRepository.getSongs().values().isEmpty());
        assertEquals(4, songRepository.getSongs().size());
    }

    @Test
    public void shouldThrowFileNotFoundException() {
        File file = new File("src/test/resources/nonexisting.xml");
        ReaderXML readerXML = new ReaderXML(file);

        assertThrows(FileNotFoundException.class, readerXML::parseXML);
    }

    @AfterAll
    static void clear() {
        songRepository.clear();
    }


}
