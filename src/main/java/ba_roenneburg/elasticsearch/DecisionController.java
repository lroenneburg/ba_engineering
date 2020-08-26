package ba_roenneburg.elasticsearch;

import ba_roenneburg.DataMapper;
import ba_roenneburg.elasticsearch.repository.DecisionRepository;
import ba_roenneburg.model.Decision;
import ba_roenneburg.model.DecisionSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class DecisionController {

    @Autowired
    ElasticsearchOperations operations;

    @Autowired
    DecisionRepository decisionRepository;


    @RequestMapping("/all")
    public ArrayList<Decision> getAllDecisions() {
        ArrayList<Decision> decisions = new ArrayList<>();
        decisionRepository.findAll().forEach(decisions::add);
        return decisions;
    }

    @RequestMapping(value = "/newTestDecision")
    public Decision addDecisions() {

        //new ElasticConfig().elasticsearchTemplate().indexOps(Decision.class).create();

        ArrayList<String> norms = new ArrayList<>();
        norms.add("Art 103 Abs 1 GG, Art 1 Abs 1 GG, Art 25 GG");
        norms.add("Art 2 Abs 1 GG, § 93c Abs 1 S 1 BVerfGG");
        norms.add("§ 32 IRG");
        norms.add("Art 302 StGB TUR");
        norms.add("Art 104 Verf TUR");
        ArrayList<String> lowerCourts = new ArrayList<>();
        lowerCourts.add("vorgehend OLG Hamm, 17. September 2009, Az: (2) 4 AuslA 22/08 (338/09), Beschluss");
        lowerCourts.add("vorgehend OLG Hamm, 24. August 2009, Az: (2) 4 AuslA 22/08 (297/09), Beschluss");
        lowerCourts.add("vorgehend OLG Hamm, 2. Juni 2009, Az: (2) 4 AuslA 22/08 (152/09), Beschluss");
        ArrayList<String> tenor = new ArrayList<>();
        tenor.add("Der Beschluss des Oberlandesgerichts Hamm vom 2. Juni 2009 - (2) 4 Ausl. A 22/08 (152 und 153/09) OLG Hamm - verletzt den Beschwerdeführer in seinem Grundrecht aus Artikel 2 Absatz 1 in Verbindung mit Artikel 1 Absatz 1 des Grundgesetzes, soweit in ihm die Auslieferung des Beschwerdeführers zur Strafverfolgung für zulässig erklärt wird. Der Beschluss wird insoweit aufgehoben und die Sache an das Oberlandesgericht Hamm zurückverwiesen");
        tenor.add("Der Beschluss des Oberlandesgerichts Hamm vom 17. September 2009 - (2) 4 Ausl. A 22/08 (338/09) OLG Hamm - verletzt den Beschwerdeführer in seinem Grundrecht aus Artikel 2 Absatz 1 in Verbindung mit Artikel 1 Absatz 1 des Grundgesetzes, soweit die Einwendungen des Beschwerdeführers gegen die Zulässigkeit der Auslieferung zurückgewiesen worden sind. Der Beschluss wird insoweit aufgehoben.");
        tenor.add("Im Übrigen wird die Verfassungsbeschwerde nicht zur Entscheidung angenommen.");
        tenor.add("...");
        ArrayList<DecisionSection> decReasons = new ArrayList<>();
        decReasons.add(new DecisionSection(1, "Die Verfassungsbeschwerde betrifft die Zulässigkeit der Auslieferung zum Zwecke der Strafverfolgung an die Republik Türkei wegen Staatsschutzdelikten bei drohender Verurteilung zu einer sogenannten erschwerten lebenslänglichen Freiheitsstrafe."));
        decReasons.add(new DecisionSection(2, "Der Beschwerdeführer besitzt die türkische Staatsangehörigkeit. Unter Bezugnahme auf einen Haftbefehl des Schwurgerichts zu D. vom 28. November 2007 ersuchte die türkische Regierung um seine Auslieferung. Ihm wird vorgeworfen, als Gebietsverantwortlicher der Arbeiterpartei Kurdistans (PKK) für die Region E. die Ausführung eines Bombenanschlags auf den Gouverneur von B. am 5. April 1999 durch ein Mitglied der PKK, den T..., beschlossen und angeordnet zu haben. Bei diesem Bombenattentat kamen T... und eine weitere Person ums Leben; weitere 14 Personen, darunter Polizeibeamte, wurden verletzt."));
        decReasons.add(new DecisionSection(3, "WEGEN TESTZWECKEN GEKÜRZT - UNVOLLSTAENDIG!"));
        ArrayList<String> occCit = new ArrayList<>();
        occCit.add("BVerfGE 63, 332 <337 f.>");
        occCit.add("BVerfGE 50, 205 <214 f.>");
        ArrayList<String> occJudge = new ArrayList<>();
        occJudge.add("Lennart Rönneburg");
        occJudge.add("Dirk Hartung");
        occJudge.add("Eugen Ruppert");

        ArrayList<String> gui_princ = new ArrayList<>();
        ArrayList<DecisionSection> fact = new ArrayList<>();

        Decision decision1 = new Decision("KVRE387011001", "ECLI:DE:BVerfG:2010:rk20100116.2bvr229909", "BVerfG", "2. Senat 2. Kammer", "16.01.2010",
                "2 BvR 2299/09", "Stattgebender Kammerbeschluss", norms, lowerCourts, "Teilweise stattgebender Kammerbeschluss: Auslieferung verletzt Grundrechte des Betroffenen aus Art 2 Abs 1 GG iVm Art 1 Abs 1 GG, wenn die Vollstreckung einer erschwerten lebenslangen Freiheitsstrafe droht - hier: Auslieferung in die Türkei zum Zweck der Strafverfolgung wegen Staatsschutzdelikten - Möglichkeit der Begnadigung nach türkischem Recht aufgrund tatbestandlicher Einschränkungen im Hinblick auf Verhältnismäßigkeitsgrundsatz des GG unzureichend", gui_princ,
                "", tenor, fact, decReasons, "", "http://www.rechtsprechung-im-internet.de/jportal/?quelle=jlink&docid=KVRE387011001&psml=bsjrsprod.psml&max=true", occCit,
                occJudge, new ArrayList<>(), new ArrayList<>());
        decisionRepository.save(decision1);
        return decision1;
    }


    @RequestMapping(value = "/addDB")
    public String addDB() throws URISyntaxException, SAXException, ParserConfigurationException, InterruptedException, IOException {
        DataMapper dm = new DataMapper();
        ArrayList<Decision> allDecisionsInDB = dm.getAllDecisionsInDB();
        int counter = 0;

        for (Decision d : allDecisionsInDB) {
            decisionRepository.save(d);
            counter++;
        }
        return counter + " Decisions added successfully.";
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/getDecision",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Iterable<Decision> getDecisionFromESDatabase(@RequestParam String decisionid) {
        Optional<Decision> byId = decisionRepository.findById(decisionid);
        System.out.println("okay");
        return null;
    }
}
