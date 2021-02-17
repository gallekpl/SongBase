package pl.com.harta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongRepositoryImpl implements ISongRepository {

    @Override
    public Song getSong(Song song) {
        return SongList.getSongs().get(song);

    }
    @Override
    public Map<Song, Song> getSongs() {
        return SongList.getSongs();
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
    public void addVoteToSong(Song song) {  //for updating votes
        Song songToBeUpdated = SongList.getSongs().get(song);
        songToBeUpdated.setVotes(songToBeUpdated.getVotes()+1);
        SongList.getSongs().remove(song, song);
        SongList.getSongs().put(songToBeUpdated, songToBeUpdated);

    }

    @Override
    public void deleteSong(Song song) {
        SongList.getSongs().remove(song);

    }

    @Override
    public void resetVotesInSong(Song song) {
        Song songToBeUpdated = SongList.getSongs().get(song);
        songToBeUpdated.setVotes(0);
        SongList.getSongs().remove(song, song);
        SongList.getSongs().put(songToBeUpdated, songToBeUpdated);
    }
}
