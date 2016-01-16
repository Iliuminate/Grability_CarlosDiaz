package com.grability.iliuminate.grabilityprueba.ModelClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grability.iliuminate.grabilityprueba.R;

/**
 * Created by Iliuminate on 15/01/2016.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView titulo, precio;

    public MyViewHolder(View itemView) {
        super(itemView);

        //Instanciamos o declaramos el contenido del View (Item para este caso)
        imageView = (ImageView) itemView.findViewById(R.id.itemImage);
        titulo=(TextView)itemView.findViewById(R.id.itemTitle);
        precio=(TextView)itemView.findViewById(R.id.itemPrice);

    }
}
