package com.grability.iliuminate.grabilityprueba.ModelClasses;

import java.util.ArrayList;

/**
 * Created by Iliuminate on 09/01/2016.
 */
public class FeedClassList {

    ArrayList<FeedClass> feedClassItem;

    public FeedClassList() {
    }

    public FeedClassList(ArrayList<FeedClass> feedClassItem) {
        this.feedClassItem = feedClassItem;
    }

    public ArrayList<FeedClass> getFeedClassItem() {
        return feedClassItem;
    }

    public void setFeedClassItem(ArrayList<FeedClass> feedClassItem) {
        this.feedClassItem = feedClassItem;
    }
}
