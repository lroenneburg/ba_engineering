import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This class fetches the data from the rechtsprechung-im-internet.de Site and calls the DataMapper to get the
 * Information into Java Objects
 */
public class DataDownloader {


    private String _bverfgURL = "https://www.rechtsprechung-im-internet.de/jportal/docs/feed/bsjrs-bverfg.xml";
    private String _bghURL = "https://www.rechtsprechung-im-internet.de/jportal/docs/feed/bsjrs-bgh.xml";
    private String _bverwgURL = "https://www.rechtsprechung-im-internet.de/jportal/docs/feed/bsjrs-bverwg.xml";
    private String _bfhURL = "https://www.rechtsprechung-im-internet.de/jportal/docs/feed/bsjrs-bfh.xml";
    private String _bagURL = "https://www.rechtsprechung-im-internet.de/jportal/docs/feed/bsjrs-bag.xml";
    private String _bsgURL = "https://www.rechtsprechung-im-internet.de/jportal/docs/feed/bsjrs-bsg.xml";
    private String _bpatgURL = "https://www.rechtsprechung-im-internet.de/jportal/docs/feed/bsjrs-bpatg.xml";


    public DataDownloader() throws IOException, ParserConfigurationException, SAXException, URISyntaxException {
        ArrayList <String> bverfg_decision_ids = readRSSFeed(_bverfgURL);
        for (String entry : bverfg_decision_ids) {
            downloadDecisionXML(entry, "BVerfG");
        }
        //DataMapper dm = new DataMapper();
        //downloadDecisionXML(bverfg_decision_ids.get(4), "BVerfG");


    }



    private ArrayList<String> readRSSFeed(String url) throws IOException {
        ArrayList<String> all_decIDs = new ArrayList<>();

        URL rssURL = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(rssURL.openStream()));
        String line;

        while((line = in.readLine()) != null) {

            if (line.contains("</guid>")) {
                String decID = line.replace("<guid>", "");
                decID = decID.replaceFirst("<guid\\sisPermaLink=\".*\">", "");
                decID = decID.replace("</guid>", "").trim();
                all_decIDs.add(decID);
            }
        }

        // First URL is just the overview-url for the rss feed
        all_decIDs.remove(0);
        return all_decIDs;

    }


    private void downloadDecisionXML(String decisionID, String court) throws IOException {
        String url = "https://www.rechtsprechung-im-internet.de/jportal/docs/bsjrs/" + decisionID + ".zip";
        Files.copy(new URL(url).openStream(), Paths.get("resources/Decisions/" + decisionID + ".zip"));

        String fileZip = "resources/Decisions/" + decisionID + ".zip";
        File destDir = new File("resources/Decisions");
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File destFile = new File(destDir, zipEntry.getName());
            String destDirPath = destDir.getCanonicalPath();
            String destFilePath = destFile.getCanonicalPath();

            if (!destFilePath.startsWith(destDirPath + File.separator)) {
                throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
            }
            File newFile = destFile;
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

        File f = new File(fileZip);
        f.delete();
    }

}
