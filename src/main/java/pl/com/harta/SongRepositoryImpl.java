package pl.com.harta;

import java.util.*;


//implementation of interface, operations on songs
//instead of SongList (Map) access to database can be implemented here
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
    public List<Song> getSongsByCategory(Category category) {
        List<Song> songsByCategory = new ArrayList<>();


        for (Song song : getSongs().values()) {
            if (song.getCategory() == category) {
                songsByCategory.add(song);
            }
        }

        return songsByCategory;
    }



    @Override
    //adds song if it doesn't exist
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
    public void resetVotesInSong(Song song) { //deletes votes in selected song
        Song songToBeUpdated = SongList.getSongs().get(song);
        songToBeUpdated.setVotes(0);
        SongList.getSongs().remove(song, song);
        SongList.getSongs().put(songToBeUpdated, songToBeUpdated);
    }

    @Override
    //deletes everything
    public void clear() {
        SongList.getSongs().clear();
    }


}
