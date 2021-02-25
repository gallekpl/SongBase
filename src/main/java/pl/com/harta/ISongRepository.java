package pl.com.harta;

import java.util.List;
import java.util.Map;

public interface ISongRepository {
    Song getSong(Song song);
    void addSong(Song song);
    void addVoteToSong(Song song);
    void resetVotesInSong(Song song);
    void deleteSong(Song song);
    Map<Song, Song> getSongs();
    List<Song> getSongsByCategory(Category category);
    void clear();

}
