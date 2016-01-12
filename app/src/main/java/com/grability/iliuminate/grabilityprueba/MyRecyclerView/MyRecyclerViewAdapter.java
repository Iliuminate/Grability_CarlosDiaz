package com.grability.iliuminate.grabilityprueba.MyRecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.grability.iliuminate.grabilityprueba.ControlClasses.ParameterImageView;
import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;
import com.grability.iliuminate.grabilityprueba.OfflineManager.GetInternalImage;
import com.grability.iliuminate.grabilityprueba.OfflineManager.SaveImage;
import com.grability.iliuminate.grabilityprueba.R;

import java.security.Policy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Iliuminate on 11/01/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    List<EntryClass> data = Collections.emptyList();
    List<Integer> displayParameter;
    SaveImage saveImage;
    GetInternalImage getInternalImage;
    private LayoutInflater inflater;
    final int tamanoImage=definirHeightImagen();
    Context context;

    //Identifica si se realizará desde de internet o de manera local
    // 0-Internet, 1-Local
    int tipoCarga;
    //El separador se requiere para armar el nombre al momento de almacenar la imagen
    final String[] separador=new String[3];

    private final String TAG="MyRecyclerAdapter";


    //Constructor del ReyclerView.Adapter
    public MyRecyclerViewAdapter(Context context, List<EntryClass> data, int tipoCarga, List<Integer> displayParameter) {

        inflater = LayoutInflater.from(context);
        this.context=context;
        this.data = data;
        this.displayParameter=displayParameter;
        this.tipoCarga=tipoCarga;
        separador[0]="LOW";
        separador[1]="MEDIUM";
        separador[2]="HIGH";
    }

    //
    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titulo, precio;

        public MyViewHolder(View itemView) {
            super(itemView);

            //Instanciamos o declaramos el contenido del View (Item para este caso)
            imageView = (ImageView) itemView.findViewById(R.id.itemImage);
            titulo=(TextView)itemView.findViewById(R.id.itemTitle);
            precio=(TextView)itemView.findViewById(R.id.itemPrice);

            //Se puede agregar un evento OnClickListener
            //itemView.setOnClickListener(context.myOnClickListener);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        //View itemView = inflater.inflate(R.layout.item, viewGroup, false);
        View itemView = inflater.inflate(R.layout.item_card, viewGroup, false);
        //Instanciamos la clase Holder(para esta caso myViewHolder) y la retornamos
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        //Por medio de esta se pueden pasar los parametros al view
        EntryClass currentData = data.get(i);

        //Recuperamos el contexto del view
        Context context = myViewHolder.imageView.getContext();
        int heightImg=Integer.parseInt(currentData.getIm_image().get(tamanoImage).getHeight());
        if(GetInternalImage.existBitmap(context,currentData.getId().getIm_id() + separador[tamanoImage] + heightImg))
        {
            cargarImagenLocal(currentData,myViewHolder.imageView);
        }
        else {
            switch (tipoCarga) {
                case 0: {
                    cargarImagenJson(currentData, myViewHolder.imageView);
                    break;
                }
                case 1: {
                    cargarImagenLocal(currentData, myViewHolder.imageView);
                    break;
                }
            }
        }

        // Actualizar los Views
        myViewHolder.titulo.setText(currentData.getTitle());
        myViewHolder.precio.setText("$ " + currentData.getIm_price().getAmount() + " " + currentData.getIm_price().getCurrency());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }



    //***********************************************************************************************
    //****Metodos para tratar las Imagenes****
    //***********************************************************************************************
    private void cargarImagenJson(final EntryClass item, final ImageView imageView){

        //Preparamos la cola de peticiones
        RequestQueue queu = Volley.newRequestQueue(context);


        //Escojemos el tamaño de la imagen de acuerdo a la pantalla
        final String uriImagen=item.getIm_image().get(tamanoImage).getLabel();
        final int heightImage=Integer.parseInt(item.getIm_image().get(tamanoImage).getHeight());


        // Petición para obtener la imagen
        ImageRequest request = new ImageRequest(
                uriImagen,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {

                        //Cargamos la Imagen en el ImageView
                        parametrizarImageView(heightImage,imageView);
                        Log.d(TAG, "Precargar: " + item.getId().getIm_id() + separador[tamanoImage] + heightImage);
                        imageView.setImageBitmap(bitmap);


                        //Guardamos la Imagen en almacenamiento Interno
                        if(!GetInternalImage.existBitmap(context,item.getId().getIm_id() + separador[tamanoImage] + heightImage)) {
                            saveImage = new SaveImage(
                                    //String FileName (im:id) --Nombre del archivo como se almacena en la memoria
                                    item.getId().getIm_id() + separador[tamanoImage] + heightImage,
                                    //Bitmap,
                                    bitmap,
                                    //Contexto
                                    context
                            );
                            //Log.d(TAG,"PostGuardar: "+item.getId().getIm_id() + separador[tamanoImage] + heightImage);
                        }

                    }
                }, 0, 0, null,null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.mipmap.ic_launcher);
                        Log.e("FeedApadter", "Error en respuesta Bitmap: " + error.getMessage());
                        //Al no poder cargar los datos desde la red, se cargan de manera local
                        cargarImagenLocal(item,imageView);
                    }
                });

        // Añadir petición a la cola
        queu.add(request);
    }



    private void cargarImagenLocal(EntryClass item, final ImageView imageView){

        final int heightImage=Integer.parseInt(item.getIm_image().get(tamanoImage).getHeight());
        parametrizarImageView(heightImage,imageView);

        /*//Menaje en el Lod como apoyo para verificar la carga Local*/
        //Log.d(TAG, "CargarImagenLocal: " + item.getId().getIm_id() + separador[tamanoImage] + heightImage);

        //Se busca y se carga la Imagen en el ImageView
        getInternalImage=new GetInternalImage(item.getId().getIm_id()+separador[tamanoImage]+heightImage,imageView, context);
    }


    /**
     * Retorna un Int que indica el tamaño de la imagen 0-LOW, 1-MEDIUM, 2-HIGH
     * @return
     */
    private int definirHeightImagen() {

        int tamano = 2;
         //List<Integer> [0-3]: Width, Height, Density, Orientation
         //displayParameter.get(int);
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

    private void parametrizarImageView(int heightImage, ImageView imageView){
        int marginImage=(int)heightImage/6;
        int lado=(int)(heightImage*1.8);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(lado,lado);
        layoutParams.setMargins(marginImage, marginImage, marginImage, marginImage);
        imageView.setLayoutParams(layoutParams);
    }


    public void reloadData(ArrayList<EntryClass> datas){
        Log.d(TAG,"Reload Data0 Size: "+datas.size());
        data.clear();
        data.addAll(datas);
        notifyDataSetChanged();
        Log.d(TAG, "Reload Data1 Size: " + data.size());
        Log.d(TAG,"Reload Data2 Size: "+datas.size());
    }

/*
    public void add(MyViewHolder item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(MyViewHolder item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }
*/


}


