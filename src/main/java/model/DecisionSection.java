package model;

/**
 * This class Represents a section of a model.Decision. It consists of a recital and the section Text.
 */
public class DecisionSection {

    private int _recital;
    private String _text;

    /**
     * Creates a Decision Section
     * @param recital The recital ("Randnummer") of the section
     * @param text The text in the section
     */
    public DecisionSection(int recital, String text) {
        _recital = recital;
        _text = text;
    }

    /**
     * Gets the recital of this section
     * @return The recital
     */
    public int getRecital() {
        return _recital;
    }

    /**
     * Gets the Text of this section
     * @return The text
     */
    public String getText() {
        return _text;
    }
}
