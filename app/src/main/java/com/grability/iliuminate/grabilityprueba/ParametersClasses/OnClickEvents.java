package com.grability.iliuminate.grabilityprueba.ParametersClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.grability.iliuminate.grabilityprueba.DetailsActivity;
import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;

import java.util.ArrayList;

/**
 * Created by Iliuminate on 14/01/2016.
 */
public class OnClickEvents extends Activity{

    public static Context context;
    public static View.OnClickListener myOnClickListener;
    ArrayList<EntryClass> entryClasses;
    int position;



    public OnClickEvents(Context context, EntryClass entryClass) {
        this.context=context;
        myOnClickListener = new MyOnClickListener();
        startPreviewApp(entryClass);
    }


    public OnClickEvents(Context context, ArrayList<EntryClass> entryClasses, int position) {
        this.context=context;
        myOnClickListener = new MyOnClickListener();
        this.entryClasses = entryClasses;
        this.position=position;
    }


    private void startPreviewApp(EntryClass entryClass){
        Intent intent = new Intent(context, DetailsActivity.class);
        //intent.putExtra(KeysFeed.KEY_ENTRY,entryClasses);
        startActivity(intent);
    }


    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
           /* Log.d("ONCLICK_",entryClasses.get(position).toString());
            startPreviewApp(entryClasses.get(position));*/
        }
    }

    private void toastMessage(String msn){
        Toast.makeText(context, msn, Toast.LENGTH_SHORT).show();
    }
}
