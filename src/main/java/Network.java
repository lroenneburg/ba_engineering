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
    }

    /**
     *
     */
    private void createDocketNumberNetwork(ArrayList<Decision> decs) throws URISyntaxException {

        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        for (Decision dec : decs) {
            String dn = dec.getDocketNumber();
            graph.addVertex(dn);
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
        System.out.println("test_fin");
    }
}
