package com.uhh.ba_roenneburg.model;

import com.uhh.ba_roenneburg.model.DecisionSection;

import java.util.ArrayList;

/**
 * This class maps a decision on a Java Object and stores all the information of the Decision-XML and additionally
 * the extracted Information like occurring citations and involved Judges
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
    private ArrayList<String> _occuringCitations;
    private ArrayList<String> _occuringJudges;

    private ArrayList<String> _occuringLocations;
    private ArrayList<String> _occuringOrganisations;


    /**
     * Creates a decision Object with all relevant Information related to the decision
     * @param decisionID The unique identifier of the decision
     * @param ecli The ECLI (European Case Law Identifier) of the decision
     * @param courtType The type of the court (e.g. BVerfG or BGH or ...)
     * @param formation The formation ("Spruchkoerper") of the court
     * @param decisionDate The date the Decision was taken
     * @param docketNumber The docketNumber of the decision
     * @param decisionType The type of the decision
     * @param norms All norms that were used for the decision
     * @param lowerCourts All previous courts of the decision
     * @param decisionTitle The decision title
     * @param guidingPrinciple The guiding principle for the decision
     * @param sonstosatz
     * @param tenor All tenor sentences of the decision
     * @param fact The fact ("Tatbestand") of the decision
     * @param decisionReasons The Reasons for the decision
     * @param dissentingOpinion
     * @param url The url to the decision on "https://rechtsprechung-im-internet.de"
     * @param occuringCitations The citations that are found in the reasons of the decision
     * @param occurringJudges The judges which are involved in the decision
     * @param occurringLocations
     * @param occurringOrganisations
     */
    public Decision(String decisionID, String ecli, String courtType, String formation, String decisionDate,
                    String docketNumber, String decisionType, ArrayList<String> norms, ArrayList<String> lowerCourts,
                    String decisionTitle, String guidingPrinciple, String sonstosatz, ArrayList<String> tenor,
                    String fact, ArrayList<DecisionSection> decisionReasons, String dissentingOpinion, String url,
                    ArrayList<String> occuringCitations, ArrayList<String> occurringJudges,
                    ArrayList<String> occurringLocations, ArrayList<String> occurringOrganisations) {
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
        this._occuringCitations = occuringCitations;
        this._occuringJudges = occurringJudges;
        this._occuringLocations = occurringLocations;
        this._occuringOrganisations = occurringOrganisations;
    }


    /**
     * Gets the unique identifier (id) of this decision
     * @return the identifier
     */
    public String getDecisionID() {
        return _decisionID;
    }

    /**
     * Gets the ECLI (European Case Law Identifier) of this decision
     * @return The ecli
     */
    public String getEcli() {
        return _ecli;
    }

    /**
     * Gets the court type of this decision
     * @return The court type
     */
    public String getCourtType() {
        return _courtType;
    }

    /**
     * Gets the formation of this decision
     * @return The formation
     */
    public String getFormation() {
        return _formation;
    }

    /**
     * Gets the date of of this decision
     * @return The decision date
     */
    public String getDecisionDate() {
        return _decisionDate;
    }

    /**
     * Gets the docket number of the decision
     * @return The docket number
     */
    public String getDocketNumber() {
        return _docketNumber;
    }

    /**
     * Gets the type of this decision
     * @return The decision type
     */
    public String getDecisionType() {
        return _decisionType;
    }

    /**
     * Gets all norms of this decision
     * @return The norms
     */
    public ArrayList<String> getNorms() {
        return _norms;
    }

    /**
     * Gets all previous courts of this decision
     * @return The previous courts
     */
    public ArrayList<String> getLowerCourts() {
        return _lowerCourts;
    }

    /**
     * Gets the title for this decision
     * @return The decision title
     */
    public String getDecisionTitle() {
        return _decisionTitle;
    }

    /**
     * Gets the guiding principle of this decision
     * @return The guiding principle
     */
    public String getGuidingPrinciple() {
        return _guidingPrinciple;
    }

    //TODO what is this? @dirk
    /**
     *
     * @return
     */
    public String getSonstosatz() {
        return _sonstosatz;
    }

    /**
     * Gets all Tenor sentences of this decision
     * @return The tenor sentences
     */
    public ArrayList<String> getTenor() {
        return _tenor;
    }

    /**
     * Gets the fact ("Tatbestand") of this decision
     * @return The fact
     */
    public String getFact() {
        return _fact;
    }

    /**
     * Gets all reasons for this decision
     * @return The decision reasons as sections
     */
    public ArrayList<DecisionSection> getDecisionReasons() {
        return _decisionReasons;
    }

    /**
     * Gets the dissenting opinion of this decision
     * @return The dissenting opinion
     */
    public String getDissentingOpinion() {
        return _dissentingOpinion;
    }

    /**
     * Gets the URL of this decision
     * @return The URL
     */
    public String getUrl() {
        return _url;
    }

    /**
     * Gets all citations occuring in this decision
     * @return The citations as reference Numbers ("Fundstelle")
     */
    public ArrayList<String> getOccurringCitations() {
        return _occuringCitations;
    }

    /**
     * Gets all judges involved in this decision
     * @return The judges names
     */
    public ArrayList<String> getOccurringJudges() {
        return _occuringJudges;
    }

    /**
     * Gets all locations occurring in this decision
     * @return The location names
     */
    public ArrayList<String> getOccurringLocations() {
        return _occuringLocations;
    }

    /**
     * Gets all organisations occurring in this decision
     * @return The organisation names
     */
    public ArrayList<String> getOccurringOrganisations() {
        return _occuringOrganisations;
    }
}
