package model;

import java.util.Objects;

/**
 * This class Represents a section of a model.Decision. It consists of a recital and the section Text.
 */
public class DecisionSection {

    private int _recital;
    private String _text;

    public DecisionSection(int recital, String text) {
        _recital = recital;
        _text = text;
    }

    public int getRecital() {
        return _recital;
    }

    public void setRecital(int _recital) {
        this._recital = _recital;
    }

    public String getText() {
        return _text;
    }

    public void setText(String _text) {
        this._text = _text;
    }
}
