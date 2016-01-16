package com.grability.iliuminate.grabilityprueba.EntryClasses;

/**
 * Created by Iliuminate on 09/01/2016.
 */
@SuppressWarnings("serial")
public class EntryClassId {

    String label,
            im_id,
            im_bundled;

    public EntryClassId() {
    }

    public EntryClassId(String label, String im_id, String im_bundled) {
        this.label = label;
        this.im_id = im_id;
        this.im_bundled = im_bundled;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIm_id() {
        return im_id;
    }

    public void setIm_id(String im_id) {
        this.im_id = im_id;
    }

    public String getIm_bundled() {
        return im_bundled;
    }

    public void setIm_bundled(String im_bundled) {
        this.im_bundled = im_bundled;
    }

    @Override
    public String toString() {
        return "EntryClassId{" +
                "label='" + label + '\'' +
                ", im_id='" + im_id + '\'' +
                ", im_bundled='" + im_bundled + '\'' +
                '}';
    }
}
