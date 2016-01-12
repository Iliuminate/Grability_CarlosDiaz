package com.grability.iliuminate.grabilityprueba.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.gson.JsonObject;
import com.grability.iliuminate.grabilityprueba.ModelClasses.FeedClass;
import com.grability.iliuminate.grabilityprueba.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iliuminate on 10/01/2016.
 */
public class FeedAdapter extends ArrayAdapter {

    ArrayList<FeedClass> items;

    public FeedAdapter(Context context, ArrayList<FeedClass> feedClasses) {
        super(context, 0);
        items=feedClasses;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        //Conservamos la referencia del View row
        View listItemView=convertView;

        //Verificamos si el View existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo
            listItemView = null == convertView ? layoutInflater.inflate(
                    R.layout.item,
                    parent,
                    false):convertView;
        }

        // Obtener item actual
        FeedClass item = items.get(position);


        // Obtener Views
        TextView itemTitle = (TextView) listItemView.
                findViewById(R.id.itemTitle);
        TextView itemPrice = (TextView) listItemView.
                findViewById(R.id.itemPrice);
        final ImageView imagenPost = (ImageView) listItemView.
                findViewById(R.id.itemImage);

        // Actualizar los Views
        itemTitle.setText(item.getTitle());
        itemPrice.setText(item.getUpdated());

        //Cargamos la Imagen desde el Json

        return listItemView;
    }

    private void cargarImagenJson(){
//        // Petición para obtener la imagen
//        ImageRequest request = new ImageRequest(
//                URL_BASE + item.getImagen(),
//                new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap bitmap) {
//                        imagenPost.setImageBitmap(bitmap);
//                    }
//                }, 0, 0, null,null,
//                new Response.ErrorListener() {
//                    public void onErrorResponse(VolleyError error) {
//                        imagenPost.setImageResource(R.drawable.error);
//                        Log.d("FeedApadter", "Error en respuesta Bitmap: " + error.getMessage());
//                    }
//                });
//
//        // Añadir petición a la cola
//        requestQueue.add(request);
    }
}
