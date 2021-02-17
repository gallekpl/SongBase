package pl.com.harta;


import java.util.HashMap;
import java.util.Map;

public class SongList {
    public static Map<Song, Song> songs = new HashMap<>();

    public static Map<Song, Song> getSongs() {
        return songs;
    }

}
