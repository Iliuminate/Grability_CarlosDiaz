package com.grability.iliuminate.grabilityprueba.ControlClasses;

import android.util.Log;

import com.grability.iliuminate.grabilityprueba.ModelClasses.AuthorClass;
import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassCategory;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassId;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassImContentType;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassImImage;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassImPrice;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassImReleaseDate;
import com.grability.iliuminate.grabilityprueba.EntryClasses.EntryClassLink;
import com.grability.iliuminate.grabilityprueba.ModelClasses.FeedClass;
import com.grability.iliuminate.grabilityprueba.ModelClasses.LinkClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Iliuminate on 10/01/2016.
 */
public class ParseJson {

    public ParseJson() {
    }


    public static FeedClass parseJsonFeedClass(JSONObject jsonObject)
    {

        FeedClass feedObjeto=null;
        AuthorClass authoObjeto=null;
        ArrayList<EntryClass> entryList=null;
        ArrayList<LinkClass> linkList=null;

        try {
            //Desempaquetamos el encabezado feed
            jsonObject= jsonObject.getJSONObject(KeysFeed.KEY_FEED);


            //Parseamos el Objeto JSON y alimentamos objetos respectivos
            authoObjeto= parseJsonAuthor(jsonObject.getJSONObject(KeysFeed.KEY_AUTHOR));
            entryList= parseJsonEntry(jsonObject.getJSONArray(KeysFeed.KEY_ENTRY));
            linkList=parseJsonLink(jsonObject.getJSONArray(KeysFeed.KEY_LINK));

//            Log.d(KeysFeed.KEY_UPDATED, jsonObject.getJSONObject(KeysFeed.KEY_UPDATED).getString(KeysFeed.KEY_LABEL));
//            Log.d(KeysFeed.KEY_RIGHTS,jsonObject.getJSONObject(KeysFeed.KEY_RIGHTS).getString(KeysFeed.KEY_LABEL));
//            Log.d(KeysFeed.KEY_ID,jsonObject.getJSONObject(KeysFeed.KEY_ID).getString(KeysFeed.KEY_LABEL));
//            Log.d(KeysFeed.KEY_ICON,jsonObject.getJSONObject(KeysFeed.KEY_ICON).getString(KeysFeed.KEY_LABEL));
//            Log.d(KeysFeed.KEY_TITLE,jsonObject.getJSONObject(KeysFeed.KEY_TITLE).getString(KeysFeed.KEY_LABEL));


            feedObjeto=new FeedClass(
                    //AuthorClass autor
                    authoObjeto,
                    //String updated
                    jsonObject.getJSONObject(KeysFeed.KEY_UPDATED).getString(KeysFeed.KEY_LABEL),
                    //String rigths
                    jsonObject.getJSONObject(KeysFeed.KEY_RIGHTS).getString(KeysFeed.KEY_LABEL),
                    //String title
                    jsonObject.getJSONObject(KeysFeed.KEY_TITLE).getString(KeysFeed.KEY_LABEL),
                    //String icon
                    jsonObject.getJSONObject(KeysFeed.KEY_ICON).getString(KeysFeed.KEY_LABEL),
                    //String id
                    jsonObject.getJSONObject(KeysFeed.KEY_ID).getString(KeysFeed.KEY_LABEL),
                    //ArrayList<EntryClass> entryList
                    entryList,
                    //ArrayList<LinkClass> linkList
                    linkList
            );

        } catch (JSONException e) {
            Log.e("ParseJson", "Error de parsing: " + e.getMessage());
        }


        return feedObjeto;
    }


    //***Metodos para parsear las clases asociadas a FeedClass***//
    public static AuthorClass parseJsonAuthor(JSONObject jsonObject)
    {
        AuthorClass authorClass=null;
        try {
            authorClass=new AuthorClass(
                    jsonObject.getJSONObject(KeysFeed.KEY_AUTHOR_NAME).getString(KeysFeed.KEY_LABEL),
                    jsonObject.getJSONObject(KeysFeed.KEY_AUTHOR_URI).getString(KeysFeed.KEY_LABEL)
            );
        } catch (JSONException e) {
            Log.e("AuthorClass", "Error: " + e.getMessage());
        }

        return authorClass;
    }

    public static EntryClass parseEntryClass(JSONObject jsonObject)
    {
        EntryClass entryClass=null;
        try {
            entryClass=new EntryClass(
                    // im_name --> label
                    jsonObject.getJSONObject(KeysFeed.KEY_ENTRY_IM_NAME).getString(KeysFeed.KEY_LABEL),
                    // summary --> label
                    jsonObject.getJSONObject(KeysFeed.KEY_ENTRY_SUMMARY).getString(KeysFeed.KEY_LABEL),
                    // rights --> label
                    jsonObject.getJSONObject(KeysFeed.KEY_ENTRY_RIGHTS).getString(KeysFeed.KEY_LABEL),
                    // title --> label
                    jsonObject.getJSONObject(KeysFeed.KEY_ENTRY_TITLE).getString(KeysFeed.KEY_LABEL),
                    // EntryClassImPrice im_price
                    parseImPrice(jsonObject.getJSONObject(KeysFeed.KEY_ENTRY_IM_PRICE)),
                    // EntryClassImContentType im_contetType
                    parseImContectType(jsonObject.getJSONObject(KeysFeed.KEY_ENTRY_IM_CONTENT_TYPE)),
                    // EntryClassLink link
                    parseEntryClassLink(jsonObject.getJSONObject(KeysFeed.KEY_ENTRY_LINK)),
                    // EntryClassId id
                    parseId(jsonObject.getJSONObject(KeysFeed.KEY_ENTRY_ID)),
                    // EntryClassCategory category
                    parseCategory(jsonObject.getJSONObject(KeysFeed.KEY_ENTRY_CATEGORY)),
                    // EntryClassImReleaseDate im_relaseDate
                    parseImReleaseDate(jsonObject.getJSONObject(KeysFeed.KEY_ENTRY_IM_RELEASE_DATE)),
                    // ArrayList<EntryClassImImage> im_image
                    parseJsonEntryImImage(jsonObject.getJSONArray(KeysFeed.KEY_ENTRY_IM_IMAGE))
            );
        } catch (JSONException e) {
            Log.e("EntryClass", "Error parseEntryClass: " + e.getMessage());
        }

        return entryClass;
    }


    public static LinkClass parseLink(JSONObject jsonObject)
    {
        LinkClass linkClass=null;

        try {
            linkClass=new LinkClass(
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_LINK_REL),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).optString(KeysFeed.KEY_LINK_TYPE),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_LINK_HREF)
            );
        } catch (JSONException e) {
            Log.e("LinkClass", "Error parseLink: " + e.getMessage());
        }

        return linkClass;
    }


    public static ArrayList<EntryClass> parseJsonEntry(JSONArray jsonArray)
    {
        ArrayList<EntryClass> entryClassList=new ArrayList<EntryClass>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject=(JSONObject)jsonArray.get(i);
//                Log.d("parseJsonEntry: "+i,jsonObject.toString());
                entryClassList.add(parseEntryClass(jsonObject));

            } catch (JSONException e) {
                Log.e("ArrayList<EntryClass>", "Error parseJsonEntry <Iter:"+i+">: " + e.getMessage());
            } catch (Exception e){
                Log.e("ArrayList<EntryClass>", "Error parseJsonEntry <Iter:"+i+">: " + e.getMessage());
            }
        }

        return entryClassList;
    }


    public static ArrayList<LinkClass> parseJsonLink(JSONArray jsonArray)
    {
        ArrayList<LinkClass> linkClasses=new ArrayList<LinkClass>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject=(JSONObject)jsonArray.get(i);
                linkClasses.add(parseLink(jsonObject));

            } catch (JSONException e) {
                Log.e("ArrayList<LinkClass>", "Error parseJsonLink <Iter:"+i+">: " + e.getMessage());
            }
        }

        return linkClasses;
    }



    //***Metodos para parsear las clases asociadas al EntryClass***//
    public static EntryClassImPrice parseImPrice(JSONObject jsonObject)
    {
        EntryClassImPrice entryClassImPrice=null;

        try {
            entryClassImPrice=new EntryClassImPrice(
                    jsonObject.getString(KeysFeed.KEY_LABEL),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_IM_PRICE_AMOUNT),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_IM_PRICE_CURRENCY)
            );
        } catch (JSONException e) {
            Log.e("EntryClassImPrice", "Error parseImPrice: " + e.getMessage());
        }

        return entryClassImPrice;
    }


    public static EntryClassImContentType parseImContectType(JSONObject jsonObject)
    {
        EntryClassImContentType entryClassImContentType=null;

        try {
            entryClassImContentType=new EntryClassImContentType(
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_IM_CONTENT_TYPE_TERM),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_IM_CONTENT_TYPE_LABEL)
            );
        } catch (JSONException e) {
            Log.e("EntryClassImContentType", "Error parseImContectType: " + e.getMessage());
        }

        return entryClassImContentType;
    }


    public static EntryClassLink parseEntryClassLink(JSONObject jsonObject)
    {
        EntryClassLink entryClassLink=null;

        try {
            entryClassLink =new EntryClassLink(
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_LINK_REL),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_LINK_TYPE),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_LINK_HREF)
            );
        } catch (JSONException e) {
            Log.e("EntryClassLink", "Error parseLink: " + e.getMessage());
        }

        return entryClassLink;
    }


    public static EntryClassId parseId(JSONObject jsonObject)
    {
        EntryClassId entryClassId=null;

        try {
            entryClassId= new EntryClassId(
                    jsonObject.getString(KeysFeed.KEY_LABEL),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_ID_IM_ID),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_ID_IM_BUNDLEID)
            );
        } catch (JSONException e) {
            Log.e("EntryClassId", "Error parseId: " + e.getMessage());
        }

        return entryClassId;
    }


    public static EntryClassCategory parseCategory(JSONObject jsonObject)
    {
        EntryClassCategory entryClassCategory=null;

        try {
            entryClassCategory=new EntryClassCategory(
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_CATEGORY_IM_ID),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_CATEGORY_TERM),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_CATEGORY_SCHEME),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_CATEGORY_LABEL)
            );
        } catch (JSONException e) {
            Log.e("EntryClassCategory", "Error parseCategory: " + e.getMessage());
        }

        return entryClassCategory;
    }


    public static EntryClassImReleaseDate parseImReleaseDate(JSONObject jsonObject)
    {
        EntryClassImReleaseDate entryClassImReleaseDate=null;

        try {
            entryClassImReleaseDate = new EntryClassImReleaseDate(
                    jsonObject.getString(KeysFeed.KEY_LABEL),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_IM_RELEASE_DATE_LABEL)
            );
        } catch (JSONException e) {
            Log.e("EntryClassImReleaseDate", "Error parseImReleaseDate: " + e.getMessage());
        }

        return entryClassImReleaseDate;
    }


    public static EntryClassImImage parseImImage(JSONObject jsonObject)
    {
        EntryClassImImage entryClassImImage=null;

        try {
            entryClassImImage=new EntryClassImImage(
                    jsonObject.getString(KeysFeed.KEY_LABEL),
                    jsonObject.getJSONObject(KeysFeed.KEY_ATTRIBUTES).getString(KeysFeed.KEY_ENTRY_IM_IMAGE_HEIGHT)
            );
        } catch (JSONException e) {
            Log.e("EntryClassImImage", "Error entryClassImImage: " + e.getMessage());
        }

        return entryClassImImage;
    }

    public static ArrayList<EntryClassImImage> parseJsonEntryImImage(JSONArray jsonArray)
    {
        ArrayList<EntryClassImImage> entryClassImImages=new ArrayList<EntryClassImImage>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject=(JSONObject)jsonArray.get(i);
                entryClassImImages.add(parseImImage(jsonObject));

            } catch (JSONException e) {
                Log.e("ArrayList<LinkClass>", "Error parseJsonLink <Iter:"+i+">: " + e.getMessage());
            }
        }

        return entryClassImImages;
    }
}
