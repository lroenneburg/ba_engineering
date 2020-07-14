import model.Decision;
import model.DecisionSection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The DataMapper extracts the decision metadata from the downloaded decision-XML and maps it into a decision object
 */
public class DataMapper {

    // Regular Expression which finds all types of docketNumbers
    //private String _referenceNumberRegex = "(((VGS|RiZ\\s?s?\\(R\\)|KZR|VRG|RiZ|EnRB|StbSt\\s?\\(B\\)|AnwZ\\s?\\(Brfg\\)|RiSt|PatAnwSt\\s?\\(R\\)|AnwZ\\s?\\(B\\)|PatAnwZ|EnVZ|AnwSt\\s?\\(B\\)|NotSt\\s?\\(Brfg\\)|KVZ|KZB|AR\\s?\\(Ri\\)|NotZ\\s?\\(Brfg\\)|RiSt\\s?\\(B\\)|AnwZ\\s?\\(P\\)|EnZB|RiSt\\s?\\(R\\)|NotSt\\s?\\(B\\)|AnwSt|WpSt\\s?\\(R\\)|KVR|AR\\s?\\(Kart\\)|EnZR|StbSt\\s?\\(R\\)|WpSt\\s?\\(B\\)|KZA|AR\\s?\\(Enw\\)|AnwSt\\s?\\(R\\)|KRB|RiZ\\s?\\(B\\)|PatAnwSt\\s?\\(B\\)|EnVR|AnwZ|NotZ|EnZA|AR)\\s\\d+/\\d+)|" +
    //        "((GSZ|LwZB|WpSt\\s?\\(B\\)|AnwZ|LwZR|KVZ|EnRB|PatAnwSt\\s?\\(B\\)|ARP|VGS|WpSt\\s?\\(R\\)|RiSt\\s?\\(B\\)|EnZA|KRB|AnwSt\\s?\\(R\\)|NotSt\\s?\\(Brfg\\)|EnVR|LwZA|ZB|AR\\s?\\(Vollz\\)|StB|ZR|AR\\s?\\(VS\\)|BJs|BLw|NotZ\\s?\\(Brfg\\)|RiZ\\s?\\(B\\)|PatAnwSt\\s?\\(R\\)|AK|RiZ|PatAnwZ|ARs|StbSt\\s?\\(R\\)|VRG|NotSt\\s?\\(B\\)|AR\\s?\\(Enw\\)|AR\\s?\\(VZ\\)|StE|KVR|AR\\s?\\(Ri\\)|AR|AnwSt|NotZ|StbSt\\s?\\(B\\)|StR|ZA|AnwZ\\s?\\(B\\)|EnZR|AR\\s?\\(Kart\\)|GSSt|AnwZ\\s?\\(P\\)|ZR\\s?\\(Ü\\)|AnwZ\\s?\\(Brfg\\)|KZB|BGns|KZR|RiSt|KZA|BAusl|AnwSt\\s?\\(B\\)|BGs|RiZ\\s?\\(R\\)|EnZB|RiSt\\s?\\(R\\)|ARZ|EnVZ)\\s\\d+/\\d+)|" +
    //        "([I+|IV|V|VI|VII|VIII|IX|X|XI|XII|1-6]+[a-z]?\\s[A-Za-z\\(\\)]{2,20}\\s\\d+/\\d\\d))| BVerfGE\\s[0-9]+,\\s[0-9]+";

    private String _referenceNumberRegex = "BVerfGE\\s[0-9]+,\\s[0-9]+";


    /**
     *
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    public DataMapper() throws IOException, SAXException, ParserConfigurationException, URISyntaxException, InterruptedException {

        ArrayList<Decision> allDecisionsInDB = new ArrayList<>();

        File folder = new File("resources/Decisions");
        //TODO uncomment
        for (File dec_file : folder.listFiles()) {
            Decision dec_object = readDecisionXML(dec_file.getCanonicalPath());
            ////readDecisionXML("resources/Decisions/KVRE437202001.xml");
            allDecisionsInDB.add(dec_object);
        }

        //TODO uncomment for person network
        /*
        Decision dc = readDecisionXML("resources/Decisions/KVRE.xml");
        Decision dc2 = readDecisionXML("resources/Decisions/KVRE.xml");
        Decision dc3 = readDecisionXML("resources/Decisions/KVRE.xml");
        Decision dc4 = readDecisionXML("resources/Decisions/KVRE.xml");
        Decision dc5 = readDecisionXML("resources/Decisions/KVRE.xml");
        Decision dc6 = readDecisionXML("resources/Decisions/KVRE.xml");
        Decision dc7 = readDecisionXML("resources/Decisions/KVRE.xml");
        Decision dc8 = readDecisionXML("resources/Decisions/KVRE.xml");
        Decision dc9 = readDecisionXML("resources/Decisions/KVRE.xml");
        Decision dc10 = readDecisionXML("resources/Decisions/KVRE.xml");

        allDecisionsInDB.add(dc);
        allDecisionsInDB.add(dc2);
        allDecisionsInDB.add(dc3);
        allDecisionsInDB.add(dc4);
        allDecisionsInDB.add(dc5);
        allDecisionsInDB.add(dc6);
        allDecisionsInDB.add(dc7);
        allDecisionsInDB.add(dc8);
        allDecisionsInDB.add(dc9);
        allDecisionsInDB.add(dc10);
        */


        System.out.println("finished mapping");
        Network network = new Network(allDecisionsInDB);
    }

    private Decision readDecisionXML(String fileName) throws IOException, SAXException, ParserConfigurationException, InterruptedException {


        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());

        String decisionID = doc.select("doknr").text().trim();
        String ecli = doc.select("ecli").text().trim();
        String courtType = doc.select("gertyp").text().trim();
        String formation = doc.select("spruchkoerper").text().trim();

        String raw_decisionDate_string = doc.select("entsch-datum").text().trim();
        String year = raw_decisionDate_string.substring(0, 4);
        String month = raw_decisionDate_string.substring(4, 6);
        String day = raw_decisionDate_string.substring(6, 8);
        String decisionDate = day + "." + month + "." + year;

        String docketNumber = doc.select("aktenzeichen").text().trim();
        String decisionType = doc.select("doktyp").text().trim();

        ArrayList<String> norms = new ArrayList<>();
        String raw_norms_string = doc.select("norm").html().trim();
        String[] norm_parts = raw_norms_string.split(",");
        for (String part : norm_parts) {
            norms.add(part.trim());
        }

        ArrayList<String> lowerCourts = new ArrayList<>();
        for (Element e : doc.select("vorinstanz")) {
            String[] lc_parts = e.html().replace("\n", "").split("<br />");
            for (String part : lc_parts) {
                if (!part.equals("")) {
                    lowerCourts.add(part);
                }
            }
        }

        String decisionTitle = doc.select("titelzeile").text().trim();
        String guidingPrinciple = doc.select("leitsatz").text().trim();
        String sonstosatz = doc.select("sonstosatz").text().trim();

        ArrayList<String> tenor = new ArrayList<>();
        Elements raw_tenors = doc.select("tenor");
        Elements tenor_el = raw_tenors.select("p");
        for (Element e : tenor_el) {
            tenor.add(e.html().trim());
        }

        String fact = doc.select("tatbestand").text().trim();

        ArrayList<DecisionSection> reasonsOrDecReasons = new ArrayList<>();

        //TODO Entscheidungsgruende test urteil
        Elements raw_decision_reasons = doc.select("entscheidungsgruende");
        if (!raw_decision_reasons.text().equals("")) {
            reasonsOrDecReasons = new ArrayList<>();
        }


        Elements raw_reasons = doc.select("gruende");
        Elements reasons_el = raw_reasons.select("dl");
        if (!raw_reasons.text().equals("")) {
            for (Element e : reasons_el) {
                int rd_nr;
                String r_text;
                if (e.select("h2").isEmpty() && e.select("h1").isEmpty() && !e.select("a").isEmpty()) {
                    rd_nr = Integer.parseInt(e.select("a").text().trim());
                    r_text = e.select("p").text().trim();
                    reasonsOrDecReasons.add(new DecisionSection(rd_nr, r_text));
                } else if (e.select("h2").isEmpty() && e.select("a").isEmpty() && !e.select("h1").isEmpty()) {
                    rd_nr = 0;
                    r_text = e.select("h1").text().trim();
                    reasonsOrDecReasons.add(new DecisionSection(rd_nr, r_text));
                } else if (e.select("h1").isEmpty() && e.select("a").isEmpty() && !e.select("h2").isEmpty()) {
                    rd_nr = 0;
                    r_text = e.select("h2").text().trim();
                    reasonsOrDecReasons.add(new DecisionSection(rd_nr, r_text));

                }
                // If no element is given
                else {
                    rd_nr = 0;
                    r_text = e.select("p").text().trim();
                    reasonsOrDecReasons.add(new DecisionSection(rd_nr, r_text));
                }
            }
        }


        ArrayList<String> occuringDecisions = new ArrayList<>();
        for (String str : tenor) {
            occuringDecisions.addAll(findAllRegExMatches(str));
        }
        for (DecisionSection ds : reasonsOrDecReasons) {
            occuringDecisions.addAll(findAllRegExMatches(ds.getText()));
        }

        //TODO uncomment for person network
        ArrayList<String> occuringPersons = new ArrayList<>();
        ArrayList<String> occuringLocations = new ArrayList<>();
        ArrayList<String> occuringOrganisations = new ArrayList<>();
        PDFController pdfController = new PDFController(ecli, year, month);
        //ArrayList<String> occuringPersons = pdfController.getOccuringPersons();
        //ArrayList<String> occuringLocations = pdfController.getOccurringLocations();
        //ArrayList<String> occuringOrganisations = pdfController.getOccurringOrganisations();



        try {
            //File myObj = new File("resources/EntityRecognition/temp_document.txt");
            String pr_ecli = ecli.split(":")[4];
            pr_ecli = pr_ecli.replace(".", "_");

            File db_file = new File("resources/EntityRecognition/temp_db/" + pr_ecli + ".txt");
            Scanner myReader = new Scanner(db_file);
            while (myReader.hasNextLine()) {
                String str_part = myReader.nextLine();
                occuringPersons.add(str_part.trim());

            }
            myReader.close();


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



        String dissentingOpinion = doc.select("abwmeinung").html().trim();
        String raw_decisionUrl_string = doc.select("identifier").text().trim();
        String decisionURL = raw_decisionUrl_string.substring(0, raw_decisionUrl_string.lastIndexOf('&')) + "&doc.part=L" + raw_decisionUrl_string.substring(raw_decisionUrl_string.lastIndexOf('&'));

        Decision dec = new Decision(decisionID, ecli, courtType, formation, decisionDate, docketNumber, decisionType,
                norms, lowerCourts, decisionTitle, guidingPrinciple, sonstosatz, tenor, fact, reasonsOrDecReasons,
                dissentingOpinion, decisionURL, occuringDecisions, occuringPersons, occuringLocations, occuringOrganisations);
        return dec;

    }


    /**
     * @param text
     * @return
     */
    private ArrayList<String> findAllRegExMatches(String text) {
        ArrayList<String> allMatches = new ArrayList<>();
        Pattern pattern = Pattern.compile(_referenceNumberRegex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String match = matcher.group();
            allMatches.add(match);
        }

        return allMatches;
    }

}
