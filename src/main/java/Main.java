import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {


    /**
     * Starts the application
     * @param args
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, URISyntaxException, InterruptedException {
        //DataDownloader dd = new DataDownloader();
        // WebScraper ws = new WebScraper();
        DataMapper dm = new DataMapper();
    }

}
