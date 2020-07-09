import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

    private ArrayList<String> _testDataSet = new ArrayList<>();


    public DataDownloader() throws IOException, ParserConfigurationException, SAXException, URISyntaxException, InterruptedException {
        String[] things_1 = {"KVRE412581601", "KVRE410221501", "KVRE409841501", "KVRE409611501", "KVRE409201501", "KVRE409391501", "KVRE408521501", "KVRE408491501", "KVRE408451501", "KVRE408401501"};
        String[] things_2 = {"KVRE407641401", "KVRE407911501", "KVRE407651401", "KVRE407141401", "KVRE394271101", "KVRE407411401", "KVRE407131401", "KVRE407001401", "KVRE407661401", "KVRE407731401"};
        String[] things_3 = {"KVRE407821501", "KVRE394361101", "KVRE406771401", "KVRE406291401", "KVRE406371401", "KVRE406381401", "KVRE406431401", "KVRE406031401", "KVRE393401101", "KVRE393971101"};
        String[] things_4 = {"KVRE405121401", "KVRE404441301", "KVRE404091301", "KVRE404201301", "KVRE404021301", "KVRE403531301", "KVRE403371301", "KVRE403381301", "KVRE403401301", "KVRE403541301"};
        String[] things_5 = {"KVRE400121201", "KVRE400071201", "KVRE399821201", "KVRE399961201", "KVRE399741201", "KVRE399661201", "KVRE400021201", "KVRE399951201", "KVRE399971201", "KVRE399791201"};
        _testDataSet.addAll(Arrays.asList(things_1));
        _testDataSet.addAll(Arrays.asList(things_2));
        _testDataSet.addAll(Arrays.asList(things_3));
        _testDataSet.addAll(Arrays.asList(things_4));
        _testDataSet.addAll(Arrays.asList(things_5));

        //ArrayList <String> bverfg_decision_ids = readRSSFeed(_bverfgURL);
        //for (String entry : bverfg_decision_ids) {
        //    downloadDecisionXML(entry, "BVerfG");
        //}
        for (String entry : _testDataSet) {
            downloadDecisionXML(entry, "BVerfG");
        }

        DataMapper dm = new DataMapper();
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
