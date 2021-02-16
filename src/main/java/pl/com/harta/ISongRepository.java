package pl.com.harta;

public interface ISongRepository {
    Song getSong(Song song);
    void addSong(Song song);
    void updateSong(Song song);
    void deleteSong(Song song);
}
