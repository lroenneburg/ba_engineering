import com.opencsv.CSVReader;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Validator {

    private String _edgesDocumentPath = "resources/Coupette/bverfge-edges.csv";
    private String _nodeDocumentPath = "resources/Coupette/bverfge-nodes.csv";

    /**
     *
     * @param defaultEdges
     */
    public Validator(Graph<String, DefaultEdge> graph) {
        HashMap<String, String> dictionary = getDictData(_nodeDocumentPath);
        HashMap<String, String> backwards_dictionary = new HashMap<>();

        for(Map.Entry<String, String> entry : dictionary.entrySet()){
            backwards_dictionary.put(entry.getValue(), entry.getKey());
        }

        ArrayList<String[]> references = getReferenceData(_edgesDocumentPath);
        compareAndEvaluate(dictionary, backwards_dictionary, references, graph);

        System.out.println("test");
    }


    /**
     *
     */
    private HashMap<String, String> getDictData(String filepath) {
        HashMap<String, String> docketNumberDict = new HashMap<>();


        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filepath));
            String[] line;
            while ((line = reader.readNext()) != null) {
                String az = line[2].replace("BVerfG", "").trim();
                String fs = line[3];
                docketNumberDict.put(az, fs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return docketNumberDict;

    }

    /**
     *
     */
    private ArrayList<String[]> getReferenceData(String filepath) {
        //HashMap<String, String> references = new HashMap<>();
        ArrayList<String[]> references = new ArrayList<>();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filepath));
            String[] line = reader.readNext();
            while ((line = reader.readNext()) != null) {
                String src = line[0];
                String target = line[1];
                references.add(new String[]{src, target});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return references;
    }


    /**
     * @param dictionary
     * @param backwards_dictionary
     * @param references
     * @param defaultEdges
     */
    private void compareAndEvaluate(HashMap<String, String> dictionary, HashMap<String, String> backwards_dictionary, ArrayList<String[]> references, Graph<String, DefaultEdge> graph) {

        for (String[] ref : references) {
            String source = ref[0];
            String target = ref[1];
            //...
        }
    }
}
