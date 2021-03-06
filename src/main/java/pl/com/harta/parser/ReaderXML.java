package pl.com.harta.parser;

import javafx.scene.control.Alert;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.com.harta.model.Category;
import pl.com.harta.model.Song;
import pl.com.harta.repository.SongRepositoryImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ReaderXML {
    private final File file;
    private SongRepositoryImpl songRepository;

    public ReaderXML(File file) {
        this.file = file;

    }

    public void parseXML() throws ParserConfigurationException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();


        //checks if "&" (if exists) is escaped
        if (FileUtils.readFileToString(file, "UTF-8").contains("&") && !FileUtils.readFileToString(file, "UTF-8").contains("&amp;")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong XML");
            alert.setContentText("Instead of \"&\" use \"&amp;\".");
            alert.showAndWait();
        } else {

            try {
                //getting songs from xml
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("song");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String title = element.getElementsByTagName("title").item(0).getTextContent();
                        String author = element.getElementsByTagName("author").item(0).getTextContent();
                        String album = element.getElementsByTagName("album").item(0).getTextContent();
                        String categoryValue = element.getElementsByTagName("category").item(0).getTextContent();
                        Category category = Category.valueOfLabel(categoryValue);
                        int votes = Integer.parseInt(element.getElementsByTagName("votes").item(0).getTextContent());
                        Song song = new Song(title, author, album, category, votes);
                        if (songRepository == null) {
                            songRepository = new SongRepositoryImpl();
                        }
                        songRepository.addSong(song);
                    }
                }
            } catch (SAXException ignored) {
            }
        }
    }
}
