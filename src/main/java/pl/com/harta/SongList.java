package pl.com.harta;


import java.util.HashMap;
import java.util.Map;

public class SongList {
    private static final Map<Song, Song> songs = new HashMap<>();

    public static Map<Song, Song> getSongs() {
        return songs;
    }

}
