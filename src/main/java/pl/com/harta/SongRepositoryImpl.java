package pl.com.harta;

public class SongRepositoryImpl implements ISongRepository {

    @Override
    public Song getSong(Song song) {
        return SongList.getSongs().get(song);

    }

    @Override
    public void addSong(Song song) {
        if (!SongList.getSongs().containsKey(song)) {
            SongList.getSongs().put(song, song);
        } else {
            getSong(song).setVotes(getSong(song).getVotes()+ song.getVotes());
        }

    }

    @Override
    public void updateSong(Song song) {

    }

    @Override
    public void deleteSong(Song song) {

    }
}
