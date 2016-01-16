package com.grability.iliuminate.grabilityprueba.EntryClasses;

/**
 * Created by Iliuminate on 09/01/2016.
 */
@SuppressWarnings("serial")
public class EntryClassImReleaseDate {

    String label, labelAttributes;

    public EntryClassImReleaseDate() {
    }

    public EntryClassImReleaseDate(String label, String labelAttributes) {
        this.label = label;
        this.labelAttributes = labelAttributes;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelAttributes() {
        return labelAttributes;
    }

    public void setLabelAttributes(String labelAttributes) {
        this.labelAttributes = labelAttributes;
    }

    @Override
    public String toString() {
        return "EntryClassImReleaseDate{" +
                "label='" + label + '\'' +
                ", labelAttributes='" + labelAttributes + '\'' +
                '}';
    }
}
