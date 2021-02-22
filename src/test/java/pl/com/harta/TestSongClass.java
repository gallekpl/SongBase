package pl.com.harta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TestSongClass {
    Song s1 = new Song("The Memory Remains", "Metallica", "Reload", Category.HEAVY_METAL, 0);
    Song s2 = new Song("The Memory Remains", "Metallica", "Reload", Category.HEAVY_METAL, 7);
    Song s3 = new Song("Unforgiven", "Metallica", "Reload", Category.HEAVY_METAL, 7);

    
    @Test
    @DisplayName("Same song with different votes should be equal")
    public void shouldBeEqual() {
        assertEquals(s1, s2);
    }
    @Test
    @DisplayName("Songs with different title should not be equal.")
    public void shouldNotBeEqual() {
        assertNotEquals(s1, s3);
    }

    @Test
    @DisplayName("Category should be correct")
    public void valueOfLabelTest() {
        assertEquals(Category.valueOfLabel("R&B"),Category.RB);
    }

}
