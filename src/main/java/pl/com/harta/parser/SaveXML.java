package pl.com.harta.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.com.harta.model.Song;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class SaveXML {
    private final File file;
    private final List<Song> list;

    public SaveXML(File file, List<Song> list) {
        this.file = file;
        this.list = list;
    }

    public void saveFile() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        //creates xml structure and saves file
        Element songs = doc.createElement("songs");
        doc.appendChild(songs);
        for (Song s : list) {
            Element song = doc.createElement("song");
            songs.appendChild(song);
            Element title = doc.createElement("title");
            Element author = doc.createElement("author");
            Element album = doc.createElement("album");
            Element category = doc.createElement("category");
            Element votes = doc.createElement("votes");

            song.appendChild(title);
            song.appendChild(author);
            song.appendChild(album);
            song.appendChild(category);
            song.appendChild(votes);

            title.appendChild(doc.createTextNode(s.getTitle()));
            author.appendChild(doc.createTextNode(s.getAuthor()));
            album.appendChild(doc.createTextNode(s.getAlbum()));
            category.appendChild(doc.createTextNode(s.getCategory().toString()));
            votes.appendChild(doc.createTextNode(String.valueOf(s.getVotes())));
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //looks better :)
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); //no xml declaration at the beginning
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
    }
}
