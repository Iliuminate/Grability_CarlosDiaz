package com.grability.iliuminate.grabilityprueba.ModelClasses;

/**
 * Created by Iliuminate on 09/01/2016.
 */
public class LinkClass {

    String  rel,
            type,
            href;

    public LinkClass() {
    }

    public LinkClass(String rel, String type, String href) {
        this.rel = rel;
        this.type = type;
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }


    @Override
    public String toString() {
        return "LinkClass{" +
                "rel='" + rel + '\'' +
                ", type='" + type + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
