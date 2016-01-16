package com.grability.iliuminate.grabilityprueba.EntryClasses;

/**
 * Created by Iliuminate on 09/01/2016.
 */
@SuppressWarnings("serial")
public class EntryClassImArtist {

    String label,
            href;

    public EntryClassImArtist() {
    }

    public EntryClassImArtist(String label, String href) {
        this.label = label;
        this.href = href;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "EntryClassImArtist{" +
                "label='" + label + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
