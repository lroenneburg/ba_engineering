package ba_roenneburg.elasticsearch;

import ba_roenneburg.elasticsearch.repository.DecisionRepository;
import ba_roenneburg.model.Decision;
import ba_roenneburg.model.DecisionSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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

    @RequestMapping(value = "/new")
    public Decision addDecisions() {

        new ElasticConfig().elasticsearchTemplate().indexOps(Decision.class).create();

        Decision decision1 = new Decision("testID55", "testECLI55", "TestCourtType55", "testformation55", "testdecdate55",
                "testdocketnumber55", "testdectype55", new ArrayList<String>(), new ArrayList<String>(), "testtitle55", "testgprinc",
                "testsonstosatz", new ArrayList<String>(), "testfact", new ArrayList<DecisionSection>(), "testdissopinion", "testurl.de", new ArrayList<String>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        decisionRepository.save(decision1);
        return decision1;
    }
}
