package com.grability.iliuminate.grabilityprueba.ModelClasses;

import java.util.ArrayList;

/**
 * Created by Iliuminate on 09/01/2016.
 */
public class FeedClass {


    String  updated,
            rigths,
            title,
            icon,
            id;

    AuthorClass autor;
    ArrayList<EntryClass> entryList;
    ArrayList<LinkClass> linkList;



    public FeedClass() {
    }

    public FeedClass(AuthorClass autor, String updated, String rigths, String title, String icon, String id, ArrayList<EntryClass> entryList, ArrayList<LinkClass> linkList) {
        this.autor = autor;
        this.updated = updated;
        this.rigths = rigths;
        this.title = title;
        this.icon = icon;
        this.id = id;
        this.entryList = entryList;
        this.linkList = linkList;
    }


    public AuthorClass getAutor() {
        return autor;
    }

    public void setAutor(AuthorClass autor) {
        this.autor = autor;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getRigths() {
        return rigths;
    }

    public void setRigths(String rigths) {
        this.rigths = rigths;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<EntryClass> getEntryList() {
        return entryList;
    }

    public void setEntryList(ArrayList<EntryClass> entryList) {
        this.entryList = entryList;
    }

    public ArrayList<LinkClass> getLinkList() {
        return linkList;
    }

    public void setLinkList(ArrayList<LinkClass> linkList) {
        this.linkList = linkList;
    }

    @Override
    public String toString() {
        return "FeedClass{" +
                "autor=" + autor +
                ", updated='" + updated + '\'' +
                ", rigths='" + rigths + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", id='" + id + '\'' +
                ", entryList=" + entryList +
                ", linkList=" + linkList +
                '}';
    }
}
