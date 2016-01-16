package com.grability.iliuminate.grabilityprueba.EntryClasses;

import java.io.Serializable;

/**
 * Created by Iliuminate on 08/01/2016.
 */
@SuppressWarnings("serial")
public class EntryClassImImage implements Serializable {

    String  label,
            height,
            id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EntryClassImImage{" +
                "label='" + label + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
