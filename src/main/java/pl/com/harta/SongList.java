package pl.com.harta;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongList {
    public static Map<Song, Song> songs = new HashMap<>();

    public static Map<Song, Song> getSongs() {
        return songs;
    }

    public static void setSongs(Map<Song, Song> songs) {
        SongList.songs = songs;
    }
}
