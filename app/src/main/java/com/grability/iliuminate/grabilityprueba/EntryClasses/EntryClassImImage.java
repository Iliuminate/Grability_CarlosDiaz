package com.grability.iliuminate.grabilityprueba.EntryClasses;

/**
 * Created by Iliuminate on 08/01/2016.
 */
@SuppressWarnings("serial")
public class EntryClassImImage {

    String  label,
            height;

    public EntryClassImImage() {
    }

    public EntryClassImImage(String label, String height) {
        this.label = label;
        this.height = height;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "EntryClassImImage{" +
                "label='" + label + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
