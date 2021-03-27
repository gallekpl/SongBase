package pl.com.harta.model;


import java.util.HashMap;
import java.util.Map;


//all songs are saved here
//can put database here instead
public class SongList {
    private static final Map<Song, Song> songs = new HashMap<>();

    public static Map<Song, Song> getSongs() {
        return songs;
    }

}
