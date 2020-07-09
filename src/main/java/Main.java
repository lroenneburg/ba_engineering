import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import WebScraper.WebScraper;

public class Main {


    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, URISyntaxException, InterruptedException {
        //DataDownloader dd = new DataDownloader();
        // WebScraper ws = new WebScraper();
        DataMapper dm = new DataMapper();
        //PDFController pdfController = new PDFController("ECLI:DE:BVerfG:2020:qk20200627.1bvq007420", "2020", "06");
        //Validator validator = new Validator();
    }

}
