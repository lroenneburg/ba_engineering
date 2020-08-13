package com.uhh.ba_roenneburg.WebScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * The Webscraper is used to crawl the decision files from the www.rechtsprechung-im-internet.de Website, to
 * store them at the filesystem to do the analysis on them later
 */
public class WebScraper {


    /**
     * Creates a Crawler, which downloads all decision files by counting up the identifier number
     *
     * @throws IOException
     */
    public WebScraper() throws IOException, InterruptedException {

        HashMap<String, ArrayList<Element>> allDecisionsFromDatabase = getAllDecisionsFromDatabase();

        //downloadDecisions("BVerfG", allDecisionsFromDatabase.get("BVerfG"));
        downloadDecisions(allDecisionsFromDatabase.get("BGH"));

    }

    /**
     *
     * @throws IOException
     */
    private HashMap<String, ArrayList<Element>> getAllDecisionsFromDatabase() throws IOException {

        //String url = "https://www.rechtsprechung-im-internet.de/jportal/docs/bsjrs";

        //URL URLObj = new URL(url);
        //BufferedReader in = new BufferedReader(new InputStreamReader(URLObj.openStream()));

        //Files.copy(new URL(url).openStream(), Paths.get("resources/all_decision_information.xml"));


        File file = new File("resources/all_decision_information.xml");
        FileInputStream fis = new FileInputStream(file);
        Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());

        Elements decisions = doc.select("item");

        ArrayList<Element> bverfg_decisions = new ArrayList<>();
        ArrayList<Element> bgh_decisions = new ArrayList<>();
        ArrayList<Element> bverwg_decisions = new ArrayList<>();
        ArrayList<Element> bfh_decisions = new ArrayList<>();
        ArrayList<Element> bag_decisions = new ArrayList<>();
        ArrayList<Element> bsg_decisions = new ArrayList<>();
        ArrayList<Element> bpatg_decisions = new ArrayList<>();
        ArrayList<Element> gSdoGdB_decisions = new ArrayList<>();


        for (Element dec : decisions) {
            if (dec.select("gericht").text().contains("BVerfG")) {
                bverfg_decisions.add(dec);
            }
            else if (dec.select("gericht").text().contains("BGH")) {
                bgh_decisions.add(dec);
            }
            else if (dec.select("gericht").text().contains("BPatG")) {
                bpatg_decisions.add(dec);
            }
            else if (dec.select("gericht").text().contains("BAG")) {
                bag_decisions.add(dec);
            }
            else if (dec.select("gericht").text().contains("BSG")) {
                bsg_decisions.add(dec);
            }
            else if (dec.select("gericht").text().contains("BFH")) {
                bfh_decisions.add(dec);
            }
            else if (dec.select("gericht").text().contains("BVerwG")) {
                bverwg_decisions.add(dec);
            }
            else {
                gSdoGdB_decisions.add(dec);
            }

        }

        HashMap<String, ArrayList<Element>> data = new HashMap<>();
        data.put("BVerfG", bverfg_decisions);
        data.put("BGH", bgh_decisions);
        data.put("BPatG", bpatg_decisions);
        data.put("BAG", bag_decisions);
        data.put("BSG", bsg_decisions);
        data.put("BFH", bfh_decisions);
        data.put("BVerwG", bverwg_decisions);
        data.put("GSdoGdB", gSdoGdB_decisions);

        return data;
    }

    /**
     * Downloads all Decision Files that are found with the given parameters.
     * @param court_decisions
     */
    private void downloadDecisions(ArrayList<Element> court_decisions) throws IOException, InterruptedException {

        ArrayList<String> urls = new ArrayList<>();


        for (Element decision : court_decisions) {
            String link = decision.select("link").text().trim();
            urls.add(link);
        }

        for (int i = 0; i < urls.size(); i++) {

            if (i % 10 == 0) {
                //System.out.println("Now waiting 5 seconds");
                TimeUnit.SECONDS.sleep(1);
            }

            String url = urls.get(i);

            String id = url.substring(60);
            id = id.substring(0, id.indexOf(".zip"));

            Files.copy(new URL(url).openStream(), Paths.get("resources/DecisionFiles/" + id + ".zip"));

            // Destination Path for the zip
            String zipDestinationPath = "resources/DecisionFiles/" + id + ".zip";
            File destinationDirectory = new File("resources/DecisionFiles");

            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipDestinationPath));
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                File destFile = new File(destinationDirectory, zipEntry.getName());
                String destDirPath = destinationDirectory.getCanonicalPath();
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

            // We don't need the zipfile anymore, so we delete it
            File f = new File(zipDestinationPath);
            f.delete();

            System.out.println("Decision " + id + " stored.");
        }

        System.out.println("hello");
    }
}
