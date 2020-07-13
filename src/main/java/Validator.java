import com.opencsv.CSVReader;
import model.Decision;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 */
public class Validator {

    private String _edgesDocumentPath = "resources/Coupette/bverfge-edges.csv";
    private String _nodeDocumentPath = "resources/Coupette/bverfge-nodes.csv";

    /**
     *
     *
     */
    public Validator(Graph<String, DefaultEdge> graph, ArrayList<Decision> decisions) {
        HashMap<String, String> dictionary = getDictData(_nodeDocumentPath);

        //HashMap<String, String> backwards_dictionary = new HashMap<>();
        //for(Map.Entry<String, String> entry : dictionary.entrySet()){
        //    backwards_dictionary.put(entry.getValue(), entry.getKey());
        //}

        int test = graph.edgeSet().size();
        ArrayList<String> DecisionsFromTestSet = new ArrayList<>();
        for (Decision d : decisions) {
            String dn = d.getDocketNumber();
            if (dn.contains(",")) {
                String[] parts = dn.split(",");
                    String p = parts[0].trim();
                    p = dictionary.get(p);
                    DecisionsFromTestSet.add(p);

            }
            else {
                DecisionsFromTestSet.add(dictionary.get(dn));
            }

        }
        ArrayList<String[]> references = getReferenceData(_edgesDocumentPath, DecisionsFromTestSet);


        ArrayList<String[]> my_references = new ArrayList<>();

        for (Decision de : decisions) {
            ArrayList<String> refs = de.getOccuringDecisions();
            Set<String> set = new HashSet<>(refs);
            refs.clear();
            refs.addAll(set);

            String source = de.getDocketNumber();
            if (source.contains(",")) {
                source = source.split(",")[0].trim();
            }
            String source_fs = dictionary.get(source);
            for (String target_fs : refs) {
                my_references.add(new String[]{source_fs, target_fs});
            }
        }

        ArrayList<String> temp_ref_strings = new ArrayList<>();
        for (String[] reference : references) {
            String str = reference[0] + "__" + reference[1];
            temp_ref_strings.add(str);
        }
        Set<String> set = new HashSet<>(temp_ref_strings);
        temp_ref_strings.clear();
        temp_ref_strings.addAll(set);

        references.clear();
        for (String string : temp_ref_strings) {
            String[] ps = string.split("__");
            references.add(new String[]{ps[0].trim(), ps[1].trim()});
        }
        /*
        Set<DefaultEdge> defaultEdges = graph.edgeSet();
        for (DefaultEdge de : defaultEdges) {
            String source = graph.getEdgeSource(de);
            String target = graph.getEdgeTarget(de);
            if (source.contains(",") && !source.contains("BVerfGE")) {
                source = source.split(",")[0].trim();
            }
            if (target.contains(",") && !target.contains("BVerfGE")) {
                target = target.split(",")[0].trim();
            }
            if (!source.contains("BVerfGE")) {
                source = dictionary.get(source);
            }
            if (!target.contains("BVerfGE")) {
                target = dictionary.get(target);
            }
            //String source_fs = dictionary.get(source);
            //String target_fs = dictionary.get(target);
            if (source != null && target != null) {
                my_references.add(new String[]{source, target});
            }
            else {
                System.out.println("Problem with: " + source + " or" + target);
            }

        }
        */

        compareResults(references, my_references);

        //compareAndEvaluate(dictionary, references, graph);

        System.out.println("test");
    }

    private void compareResults(ArrayList<String[]> references, ArrayList<String[]> my_references) {

        int match_counter = 0;
        ArrayList<String[]> matches = new ArrayList<>();

        for (String[] cou_ref : references) {
            String cou_source = cou_ref[0];
            String cou_target = cou_ref[1];
            //System.out.println("[ " + cou_source + " ] -> [ " + cou_target + " ]");

            for (String[] my_ref : my_references) {
                String my_source = my_ref[0];
                String my_target = my_ref[1];

                if (cou_source.equals(my_source) && cou_target.equals(my_target)) {
                    match_counter++;
                    matches.add(my_ref);
                    break;

                }
            }
        }
        double accuracy = (double) match_counter / (double) references.size();
        double precision = (double) match_counter/ ;
        System.out.println("Coupette found " + references.size() + " Decision-references for the test dataset.");
        System.out.println("I found " + my_references.size() + " Decision-references for the test dataset.");
        System.out.println("Found " + match_counter + " matching References for the test dataset.");
        System.out.println("Accuracy: " +  accuracy);
        System.out.println("Precision: " + precision);

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
    private ArrayList<String[]> getReferenceData(String filepath, ArrayList<String> decisionsfromTS) {


        ArrayList<String[]> references = new ArrayList<>();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filepath));
            String[] line = reader.readNext();
            while ((line = reader.readNext()) != null) {
                String src = line[0];
                String target = line[1];
                if (decisionsfromTS.contains(src)) {
                    references.add(new String[]{src, target});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return references;
    }




















    private void compareAndEvaluate(HashMap<String, String> dictionary, ArrayList<String[]> references, Graph<String, DefaultEdge> graph) {

        int counter = 0;

        for (String[] ref : references) {
            String source = ref[0];
            String target = ref[1];

            Set<DefaultEdge> defaultEdges = graph.edgeSet();
            for (DefaultEdge de : defaultEdges) {
                String mySource = graph.getEdgeSource(de).trim();
                if (mySource.contains(",")) {
                    mySource = mySource.split(",")[0].trim();
                }
                String myTarget = graph.getEdgeTarget(de).trim();
                if (myTarget.contains(",")) {
                    myTarget = myTarget.split(",")[0].trim();
                }

                if (!mySource.contains("BVerfGE")) {
                    mySource = dictionary.get(mySource);
                }
                if (!myTarget.contains("BVerfGE")) {
                    myTarget = dictionary.get(myTarget);
                }
                if (source.equals(mySource) && target.equals(myTarget)) {
                    counter++;
                    System.out.println("Matched: " + mySource + " to " + myTarget);
                }
            }
        }
        System.out.println("Coupette found " + references.size() + " Decision-references for the test dataset.");
        System.out.println("I found " + graph.edgeSet().size() + " Decision-references for the test dataset.");
        System.out.println("Found " + counter + " matching References for the test dataset.");
    }
}
