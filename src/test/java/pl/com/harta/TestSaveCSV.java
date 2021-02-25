package pl.com.harta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestSaveCSV {

    File file = new File("src/test/resources/saving.csv");

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
    public void testSaving() throws IOException {
        SaveCSV saveCSV = new SaveCSV(file, list);
        saveCSV.saveFile();
        Assertions.assertTrue(file.exists());
        Assertions.assertEquals(230.0, file.length());
        file.deleteOnExit();
    }
}
