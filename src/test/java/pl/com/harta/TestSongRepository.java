package pl.com.harta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestSongRepository {

    static SongRepositoryImpl songRepository = new SongRepositoryImpl();
    static Song s1 = new Song("The Memory Remains", "Metallica", "Reload", Category.HEAVY_METAL, 0);
    static Song s2 = new Song("Vagabond's Life", "Mono Inc", "Melodies In Black", Category.ROCK, 7);
    static Song s3 = new Song("Unforgiven", "Metallica", "Reload", Category.HEAVY_METAL, 6);
    static Song s4 = new Song("Fullmoon", "Sonata Arctica", "Ecliptica", Category.HEAVY_METAL, 12);
    static Song s5 = new Song("Fullmoon", "Sonata Arctica", "Ecliptica", Category.HEAVY_METAL, 4);

    @BeforeAll
    public static void setUp() {
        songRepository.addSong(s1);
        songRepository.addSong(s2);
        songRepository.addSong(s3);
        songRepository.addSong(s4);
        songRepository.addSong(s5);
    }

    @Test
    public void testSameSongShouldUpdateOnlyVotes() {
        Assertions.assertEquals(4, songRepository.getSongs().size()); //s4 and s5 should be as one, only votes updated
        Assertions.assertEquals(16, songRepository.getSong(s4).getVotes());
        Assertions.assertEquals(16, songRepository.getSong(s5).getVotes());
        Assertions.assertNotSame(s4, s5);
        Assertions.assertSame(songRepository.getSong(s4), songRepository.getSong(s5));
    }

}
