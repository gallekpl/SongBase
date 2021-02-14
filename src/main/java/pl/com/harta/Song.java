package pl.com.harta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Song {

    String title;
    String author;
    String album;
    Category category;
    int votes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (!title.equals(song.title)) return false;
        if (!author.equals(song.author)) return false;
        if (!album.equals(song.album)) return false;
        return category == song.category;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + album.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }
}
