package com.grability.iliuminate.grabilityprueba.ModelClasses;

import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassCategory;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassId;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassImContentType;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassImImage;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassImPrice;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassImReleaseDate;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassLink;

import java.util.ArrayList;

/**
 * Created by Iliuminate on 09/01/2016.
 */
public class EntryClass {

    String  im_name,
            summary,
            rights,
            title;

    EntryClassImPrice im_price;
    EntryClassImContentType im_contetType;
    EntryClassLink link;
    EntryClassId id;
    EntryClassCategory category;
    EntryClassImReleaseDate im_relaseDate;
    ArrayList<EntryClassImImage> im_image;


    public EntryClass() {
    }

    public EntryClass(String im_name, String summary, String rights, String title,
                      EntryClassImPrice im_price, EntryClassImContentType im_contetType, EntryClassLink link,
                      EntryClassId id, EntryClassCategory category, EntryClassImReleaseDate im_relaseDate,
                      ArrayList<EntryClassImImage> im_image) {
        this.im_name = im_name;
        this.summary = summary;
        this.rights = rights;
        this.title = title;
        this.im_price = im_price;
        this.im_contetType = im_contetType;
        this.link = link;
        this.id = id;
        this.category = category;
        this.im_relaseDate = im_relaseDate;
        this.im_image = im_image;
    }

    public String getIm_name() {
        return im_name;
    }

    public void setIm_name(String im_name) {
        this.im_name = im_name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EntryClassImPrice getIm_price() {
        return im_price;
    }

    public void setIm_price(EntryClassImPrice im_price) {
        this.im_price = im_price;
    }

    public EntryClassImContentType getIm_contetType() {
        return im_contetType;
    }

    public void setIm_contetType(EntryClassImContentType im_contetType) {
        this.im_contetType = im_contetType;
    }

    public EntryClassLink getLink() {
        return link;
    }

    public void setLink(EntryClassLink link) {
        this.link = link;
    }

    public EntryClassId getId() {
        return id;
    }

    public void setId(EntryClassId id) {
        this.id = id;
    }

    public EntryClassCategory getCategory() {
        return category;
    }

    public void setCategory(EntryClassCategory category) {
        this.category = category;
    }

    public EntryClassImReleaseDate getIm_relaseDate() {
        return im_relaseDate;
    }

    public void setIm_relaseDate(EntryClassImReleaseDate im_relaseDate) {
        this.im_relaseDate = im_relaseDate;
    }

    public ArrayList<EntryClassImImage> getIm_image() {
        return im_image;
    }

    public void setIm_image(ArrayList<EntryClassImImage> im_image) {
        this.im_image = im_image;
    }

    @Override
    public String toString() {
        return "EntryClass{" +
                "im_name='" + im_name + '\'' +
                ", summary='" + summary + '\'' +
                ", rights='" + rights + '\'' +
                ", title='" + title + '\'' +
                ", im_price=" + im_price +
                ", im_contetType=" + im_contetType +
                ", link=" + link +
                ", id=" + id +
                ", category=" + category +
                ", im_relaseDate=" + im_relaseDate +
                ", im_image=" + im_image +
                '}';
    }
}
