package model;

import java.util.ArrayList;

/**
 * This class maps a decision on a Java Object with the attributes 'Entscheidungstitel', 'Gericht', 'Entscheidungsdatum', 'Aktenzeichen', 'Dokumenttyp', 'URL' and 'Veroeffentlichungsdatum'.
 */
public class Decision {

    private String _decisionID;
    private String _ecli;
    private String _courtType;
    private String _formation;
    private String _decisionDate;
    private String _docketNumber;
    private String _decisionType;
    private ArrayList<String> _norms;
    private ArrayList<String> _lowerCourts;
    private String _decisionTitle;
    private String _guidingPrinciple;
    private String _sonstosatz;
    private ArrayList<String> _tenor;
    private String _fact;
    private ArrayList<DecisionSection> _decisionReasons;
    private String _dissentingOpinion;
    private String _url;
    private ArrayList<String> _occuringDecisions;

    private ArrayList<String> _occuringPersons;
    private ArrayList<String> _occuringLocations;
    private ArrayList<String> _occuringOrganisations;


    /**
     * Creates a decision Object with all relevant Information of the decision
     * @param decisionID
     * @param ecli
     * @param courtType
     * @param formation
     * @param decisionDate
     * @param docketNumber
     * @param decisionType
     * @param norms
     * @param lowerCourts
     * @param decisionTitle
     * @param guidingPrinciple
     * @param sonstosatz
     * @param tenor
     * @param fact
     * @param decisionReasons
     * @param dissentingOpinion
     * @param url
     * @param occuringDecisions
     */
    public Decision(String decisionID, String ecli, String courtType, String formation, String decisionDate,
                    String docketNumber, String decisionType, ArrayList<String> norms, ArrayList<String> lowerCourts,
                    String decisionTitle, String guidingPrinciple, String sonstosatz, ArrayList<String> tenor,
                    String fact, ArrayList<DecisionSection> decisionReasons, String dissentingOpinion, String url,
                    ArrayList<String> occuringDecisions, ArrayList<String> occuringPersons,
                    ArrayList<String> occuringLocations, ArrayList<String> occuringOrganisations) {
        this._decisionID = decisionID;
        this._ecli = ecli;
        this._courtType = courtType;
        this._formation = formation;
        this._decisionDate = decisionDate;
        this._docketNumber = docketNumber;
        this._decisionType = decisionType;
        this._norms = norms;
        this._lowerCourts = lowerCourts;
        this._decisionTitle = decisionTitle;
        this._guidingPrinciple = guidingPrinciple;
        this._sonstosatz = sonstosatz;
        this._tenor = tenor;
        this._fact = fact;
        this._decisionReasons = decisionReasons;
        this._dissentingOpinion = dissentingOpinion;
        this._url = url;
        this._occuringDecisions = occuringDecisions;
        this._occuringPersons = occuringPersons;
        this._occuringLocations = occuringLocations;
        this._occuringOrganisations = occuringOrganisations;
    }


    public String getEcli() {
        return _ecli;
    }

    public String getDocketNumber() {
        return _docketNumber;
    }

    public ArrayList<String> getOccuringDecisions() {
        return _occuringDecisions;
    }

    public ArrayList<String> getOccuringPersons() {
        return _occuringPersons;
    }

    public ArrayList<String> getOccuringLocations() {
        return _occuringLocations;
    }

    public ArrayList<String> getOccuringOrganisations() {
        return _occuringOrganisations;
    }
}
