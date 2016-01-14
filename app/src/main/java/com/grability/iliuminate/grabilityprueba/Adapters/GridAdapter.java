package com.grability.iliuminate.grabilityprueba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.ViewHolderClass;
import com.grability.iliuminate.grabilityprueba.R;

import java.util.ArrayList;

/**
 * Created by Iliuminate on 13/01/2016.
 */
public class GridAdapter extends RecyclerView.Adapter<ViewHolderClass> {

    private final ArrayList<EntryClass> entryClasses;

    public GridAdapter(ArrayList<EntryClass> entryClasses) {
        this.entryClasses = entryClasses;
    }

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderClass holder, final int position) {
        final EntryClass entryClass = entryClasses.get(position);

        holder.titulo.setText(entryClass.getTitle());
        holder.precio.setText(entryClass.getIm_price().getAmount());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.imageView.getContext();
                //context.startActivity(new Intent(context, demo.activityClass));
            }
        });
    }

    @Override
    public int getItemCount() {
        return entryClasses.size();
    }
}