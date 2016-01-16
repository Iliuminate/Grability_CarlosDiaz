package com.grability.iliuminate.grabilityprueba.AdaptersParsing;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;
import com.grability.iliuminate.grabilityprueba.OfflineManager.GetInternalImage;
import com.grability.iliuminate.grabilityprueba.OfflineManager.SaveImage;
import com.grability.iliuminate.grabilityprueba.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iliuminate on 10/01/2016.
 */
public class EntryAdapter extends ArrayAdapter {

    ArrayList<EntryClass> items;
    List<Integer> displayParameter;
    Context context;
    SaveImage saveImage;
    GetInternalImage getInternalImage;
    int heightImage;
    String filename;

    //Identifica si se realizará desde de internet o de manera local
    int tipoCarga;

    //El separador se requiere para armar el nombre al momento de almacenar la imagen
    final String[] separador=new String[3];

    private final String TAG="EntryAdapter";


    /**
     * TipoCarga (0-Por medio de la Red)(1-Medio Local)
     * @param context
     * @param feedClasses
     * @param tipoCarga
     * @param displayParameter
     */
    public EntryAdapter(Context context, ArrayList<EntryClass> feedClasses, int tipoCarga, List<Integer> displayParameter) {
        super(context, 0);
        this.context=context;
        this.tipoCarga=tipoCarga;
        this.displayParameter=displayParameter;
        items=feedClasses;
        notifyDataSetChanged();

        separador[0]="LOW";
        separador[1]="MEDIUM";
        separador[2]="HIGH";
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
                    R.layout.item_card,
                    parent,
                    false):convertView;
        }

        // Obtener item actual
        EntryClass item = items.get(position);


        // Obtener Views
        TextView itemTitle = (TextView) listItemView.
                findViewById(R.id.itemTitle);
        TextView itemPrice = (TextView) listItemView.
                findViewById(R.id.itemPrice);
        final ImageView imagenItem = (ImageView) listItemView.
                findViewById(R.id.itemImage);

        switch (tipoCarga)
        {
            case 0:{cargarImagenJson(item,imagenItem);break;}
            case 1:{cargarImagenLocal(item,imagenItem);break;}
        }

        // Actualizar los Views
        itemTitle.setText(item.getTitle());
        itemPrice.setText("$ "+item.getIm_price().getAmount());

        //Cargamos la Imagen desde el Json

        return listItemView;
    }


    private void cargarImagenJson(final EntryClass item, final ImageView imageView){

        RequestQueue queu = Volley.newRequestQueue(context);


        //Verificamos el tamaño de la pantalla y densidad de pixeles para escoger el tamaño de la imagen
        final int tamanoImage=definirHeightImagen();
        final String uriImagen=item.getIm_image().get(tamanoImage).getLabel();

        heightImage=Integer.parseInt(item.getIm_image().get(tamanoImage).getHeight());
        filename=item.getId().getIm_id()+separador[tamanoImage]+heightImage;

        // Petición para obtener la imagen
        ImageRequest request = new ImageRequest(
                uriImagen,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {

                        //Cargamos la Imagen en el ImageView
                        imageView.setMinimumHeight(heightImage);
                        imageView.setImageBitmap(bitmap);

                        //Guardamos la Imagen en almacenamiento Interno
                        if(!GetInternalImage.existBitmap(context,filename))
                        {
                            saveImage = new SaveImage(
                                    //String FileName (im:id) --Nombre del archivo como se almacena en la memoria
                                    filename,
                                    //Bitmap,
                                    bitmap,
                                    //Contexto
                                    context
                            );
                        }

                    }
                }, 0, 0, null,null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.mipmap.ic_launcher);
                        Log.e("FeedApadter", "Error en respuesta Bitmap: " + error.getMessage());
                    }
                });

        // Añadir petición a la cola
        queu.add(request);
    }



    private void cargarImagenLocal(EntryClass item, final ImageView imageView){

        int tamanoImage=definirHeightImagen();
        int heightImageLocal=Integer.parseInt(item.getIm_image().get(tamanoImage).getHeight());
        String filenameLocal=item.getId().getIm_id()+separador[tamanoImage]+heightImage;

        Log.d(TAG,"CargarImagenLocal: "+filenameLocal);

        imageView.setMinimumHeight(heightImageLocal);
        //Se busca y se carga la Imagen en el ImageView
        getInternalImage=new GetInternalImage(filenameLocal,imageView, context);
    }


    /**
     * Retorna un Int que indica el tamaño de la imagen 0-LOW, 1-MEDIUM, 2-HIGH
     * @return
     */
    private int definirHeightImagen() {

        int tamano = 2;
        /*List<Integer> [0-3]: Width, Height, Density, Orientation
         * displayParameter.get(int);
         */

        if (displayParameter != null) {
            if (displayParameter.get(2) > 480) {
                tamano = 2;
            } else {
                if (displayParameter.get(2) > 320) {
                    tamano = 1;
                } else {
                    tamano = 0;
                }
            }
        }
        return tamano;
    }


}
