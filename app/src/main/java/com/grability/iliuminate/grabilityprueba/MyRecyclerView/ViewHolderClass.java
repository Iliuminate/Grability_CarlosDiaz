package com.grability.iliuminate.grabilityprueba.MyRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grability.iliuminate.grabilityprueba.R;

/**
 * Created by Iliuminate on 11/01/2016.
 */
public class ViewHolderClass extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView titulo, precio;

    public ViewHolderClass(View itemView) {
        super(itemView);

        //Instanciamos o declaramos el contenido del View (Item para este caso)
        imageView = (ImageView) itemView.findViewById(R.id.itemImage);
        titulo=(TextView)itemView.findViewById(R.id.itemTitle);
        precio=(TextView)itemView.findViewById(R.id.itemPrice);

        //Se puede agregar un evento OnClickListener
        //itemView.setOnClickListener(context.myOnClickListener);
    }
}
