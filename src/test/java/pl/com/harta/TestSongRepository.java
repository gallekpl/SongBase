package pl.com.harta;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestSongRepository {

    SongRepositoryImpl songRepository = new SongRepositoryImpl();
    Song s1 = new Song("The Memory Remains", "Metallica", "Reload", Category.HEAVY_METAL, 0);
    Song s2 = new Song("Vagabond's Life", "Mono Inc", "Melodies In Black", Category.ROCK, 7);
    Song s3 = new Song("Unforgiven", "Metallica", "Metallica", Category.HEAVY_METAL, 6);
    Song s4 = new Song("Fullmoon", "Sonata Arctica", "Ecliptica", Category.HEAVY_METAL, 12);
    Song s5 = new Song("Fullmoon", "Sonata Arctica", "Ecliptica", Category.HEAVY_METAL, 4);

    @BeforeEach
    public void setUp() {
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

    @Test
    public void testVoting() {
        songRepository.addVoteToSong(s1);
        Assertions.assertEquals(1, songRepository.getSong(s1).getVotes());
        songRepository.addVoteToSong(s1);
        Assertions.assertEquals(2, songRepository.getSong(s1).getVotes());
    }

    @Test
    public void testResetVotesInSong() {
        songRepository.resetVotesInSong(s2);
        Assertions.assertEquals(0, songRepository.getSong(s2).getVotes());
    }

    @Test
    public void testGetSongsByCategory() {
        List<Song> byCategory = songRepository.getSongsByCategory(Category.ROCK);
        Assertions.assertEquals(1, byCategory.size());
        Assertions.assertEquals(s2, byCategory.get(0));
    }

    @Test
    public void testDeleteSong() {
        songRepository.deleteSong(s3);
        Assertions.assertNull(songRepository.getSong(s3));
    }

    @AfterEach
    public void clear() {
        songRepository.clear();
    }

}
