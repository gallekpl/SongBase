package pl.com.harta;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSaveXML {

    File file = new File("src/test/resources/saving.xml");

    static Song s1 = new Song("The Memory Remains", "Metallica", "Reload", Category.HEAVY_METAL, 0);
    static Song s2 = new Song("Vagabond's Life", "Mono Inc", "Melodies In Black", Category.ROCK, 7);
    static Song s3 = new Song("Unforgiven", "Metallica", "Reload", Category.HEAVY_METAL, 6);
    static Song s4 = new Song("Fullmoon", "Sonata Arctica", "Ecliptica", Category.HEAVY_METAL, 12);
    static List<Song> list = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
    }

    @Test
    public void testSaving() throws TransformerException, ParserConfigurationException {
        SaveXML saveXML = new SaveXML(file, list);
        saveXML.saveFile();
        assertTrue(file.exists());
        assertTrue(file.length()==792 | file.length()==832); //792 for linux and 832 for windows, cause of different line end, 1 byte on linux and 2 bytes on windows
        file.deleteOnExit();
    }
}
