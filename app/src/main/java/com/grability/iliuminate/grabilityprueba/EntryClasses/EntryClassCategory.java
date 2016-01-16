package com.grability.iliuminate.grabilityprueba.EntryClasses;

/**
 * Created by Iliuminate on 09/01/2016.
 */
@SuppressWarnings("serial")
public class EntryClassCategory {

    String  im_id,
            term,
            scheme,
            label;

    public EntryClassCategory() {
    }

    public EntryClassCategory(String im_id, String term, String scheme, String label) {
        this.im_id = im_id;
        this.term = term;
        this.scheme = scheme;
        this.label = label;
    }


    public String getIm_id() {
        return im_id;
    }

    public void setIm_id(String im_id) {
        this.im_id = im_id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "EntryClassCategory{" +
                "im_id='" + im_id + '\'' +
                ", term='" + term + '\'' +
                ", scheme='" + scheme + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
