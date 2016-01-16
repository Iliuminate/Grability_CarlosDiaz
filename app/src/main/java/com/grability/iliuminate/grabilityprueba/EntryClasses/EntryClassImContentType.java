package com.grability.iliuminate.grabilityprueba.EntryClasses;

/**
 * Created by Iliuminate on 09/01/2016.
 */
@SuppressWarnings("serial")
public class EntryClassImContentType {

    String term, label;


    public EntryClassImContentType() {
    }

    public EntryClassImContentType(String term, String label) {
        this.term = term;
        this.label = label;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "EntryClassImContentType{" +
                "term='" + term + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
