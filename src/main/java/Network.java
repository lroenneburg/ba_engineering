import model.Decision;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Network {

    public Network(ArrayList<Decision> decisions) throws URISyntaxException {
        createDocketNumberNetwork(decisions);
        //TODO uncomment for person network
        //createEntityNetwork(decisions);
    }

    /**
     *
     */
    private void createEntityNetwork(ArrayList<Decision> decisions) {
        Graph<String, DefaultEdge> entity_graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        for (Decision dec : decisions) {
            String dn = dec.getDocketNumber();
            entity_graph.addVertex(dn);
            ArrayList<String> persons = dec.getOccuringPersons();
            for (String person : persons) {
                entity_graph.addVertex(person);
            }


        }

        for (Decision dec : decisions) {
            ArrayList<String> persons = dec.getOccuringPersons();
            String dn = dec.getDocketNumber();
            Set<String> vertexes = entity_graph.vertexSet();
            for (String per : persons) {
                if (vertexes.contains(per)) {
                    entity_graph.addEdge(per, dn);
                }
            }
        }
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(v -> {
            v = v.replace('/', '_');
            v = v.replace(" ", "_");
            v = v.replace(",", "_");
            v = v.replace("ä", "ae");
            v = v.replace("Ä", "Ae");
            v = v.replace("ö", "oe");
            v = v.replace("Ö", "Oe");
            v = v.replace("ü", "ue");
            v = v.replace("Ü", "Ue");
            v = v.replace("ß", "ss");
            v = "_" + v;
            return v;
        });
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v));
            return map;
        });
        Writer writer = new StringWriter();
        exporter.exportGraph(entity_graph, writer);
        System.out.println(writer.toString());
        System.out.println("fin_entities");
    }


    /**
     *
     */
    private void createDocketNumberNetwork(ArrayList<Decision> decs) {

        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        for (Decision dec : decs) {
            //System.out.println(dec.getDocketNumber() + ": Insgesamt " + dec.getOccuringDecisions().size() + " Aktenzeichen tauchen im Dokument auf");
            String dn = dec.getDocketNumber();
            graph.addVertex(dn);
            for (String occuringdn : dec.getOccuringDecisions()) {
                graph.addVertex(occuringdn);
            }
        }

        for (Decision dec : decs) {
            ArrayList<String> ods = dec.getOccuringDecisions();
            String dn = dec.getDocketNumber();
            Set<String> vertexes = graph.vertexSet();

            for (String od : ods) {
                if (vertexes.contains(od)) {
                    graph.addEdge(dn, od);
                }
            }
        }


        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(v -> {
            v = v.replace('/', '_');
            v = v.replace(" ", "_");
            v = v.replace(",", "_");
            v = v.replace("(", "_");
            v = v.replace(")", "_");
            v = "_" + v;
            return v;
        });
            exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v));
            return map;
        });
        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        System.out.println(writer.toString());

        Validator validator = new Validator(graph, decs);

        System.out.println("test_fin");
    }
}
