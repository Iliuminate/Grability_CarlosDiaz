package com.grability.iliuminate.grabilityprueba;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grability.iliuminate.grabilityprueba.ControlClasses.ParseJson;
import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;
import com.grability.iliuminate.grabilityprueba.ModelClasses.FeedClass;
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.MarginDecoration;
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.MyRecyclerViewAdapter;
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.MyRecyclerViewAdapterTablet;
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.ViewHolderClass;
import com.grability.iliuminate.grabilityprueba.OfflineManager.JsonOffline;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iliuminate on 11/01/2016.
 */
public class MyRecyclerTablet extends AppCompatActivity {


    //Definicion de los Otros elementos principales
    JsonOffline jsonOffline;
    String jsonTexto="";
    FeedClass feedClass;
    Spinner spinnerCategory;
    private final String TAG="MyRecyclerTablet";
    Context contexto=this;
    int numeroBloques;

    //Definicion de elementos para el Recycler View
    private static MyRecyclerViewAdapterTablet recyclerViewAdapter;
    private static RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_tablet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        spinnerCategory=(Spinner)findViewById(R.id.spinnerCategory);


        loadItems();
        numeroBloques=determinarBloques();


        final JsonOffline jsonOffline=new JsonOffline(contexto);
        if(jsonOffline.writeLocalFile(jsonTexto))
        {Log.d(TAG,"JSON2: "+jsonTexto);}
        else{Log.d(TAG,"No se pudo escribir el fichero");}

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);

        /*recyclerView.setLayoutManager(new LinearLayoutManager(contexto));*/
        recyclerView.setLayoutManager(new GridLayoutManager(contexto, numeroBloques));
        /*recyclerView.setAdapter(new NumberedAdapter(30));*/



    }


    private static class GridAdapter extends RecyclerView.Adapter<ViewHolderClass> {

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
                    toastMessage(context,"Ocurrio un evento");
                }
            });
        }

        @Override
        public int getItemCount() {
            return entryClasses.size();
        }
    }



    /**
     * List<Integer> [0-3]: Width, Height, Density, Orientation
     * @return
     */
    public List<Integer> getDisplayParameters()
    {
        Display display = getWindowManager().getDefaultDisplay();
        List<Integer> displayParameters=new ArrayList<Integer>();

        // Tamaño en píxeles
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.i(TAG, "Ancho             = " + width);
        Log.i(TAG, "Alto              = " + height);

        // dpi
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        int densityDpi = metrics.densityDpi;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;
        Log.i(TAG, "Ancho en píxeles  = " + widthPixels);
        Log.i(TAG, "Alto en píxeles   = " + heightPixels);
        Log.i(TAG, "Densidad dpi      = " + densityDpi);
        Log.i(TAG, "x dpi             = " + xdpi);
        Log.i(TAG, "y dpi             = " + ydpi);

        Log.i(TAG, "Rel. Ancho        = " + (widthPixels/xdpi));
        Log.i(TAG, "Rel. Alto         = " + (heightPixels/ydpi));

        // Orientación (1 portrait, 2 Landscape)
        int orientation = getResources().getConfiguration().orientation;
        Log.i(TAG, "Orientación       = " + orientation);

        displayParameters.add(widthPixels);//Width
        displayParameters.add(heightPixels);//Height
        displayParameters.add(densityDpi);//Density
        displayParameters.add(orientation);

        return displayParameters;
    }

    private FeedClass loadItems()
    {
        FeedClass aux;
        /*Creamos la cola de peticiones (Representacion logica "RequestQueue")
         *INICIALIZAMOS para realizar las peticiones
         */
        RequestQueue queu = Volley.newRequestQueue(this);
        String url = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                // Capturamos la respuesta y la dejamos a disposición
                feedClass= ParseJson.parseJsonFeedClass(response);
                jsonTexto=response.toString();
                //Creamos el Adapter y cargamos en pantalla
                //recyclerViewAdapter = new MyRecyclerViewAdapter(contexto, feedClass.getEntryList(), 0,getDisplayParameters());
                recyclerViewAdapter = new MyRecyclerViewAdapterTablet(contexto, feedClass.getEntryList(), 0,getDisplayParameters(),numeroBloques);
                recyclerView.setAdapter(recyclerViewAdapter);

                feedSpinnerCtegory(feedClass.getEntryList(), spinnerCategory);

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                toastMessage(contexto,"Error Volley => )"+error.toString());
                Log.e(TAG, "Error Respuesta en JSON: " + error.getMessage());
            }
        });

        //Agregamoa la peticion a la cola
        queu.add(jsonObjectRequest);

        return feedClass;
    }


    private int determinarBloques(){
        int j=3;

        return j;
    }

    /**
     * Alimentamos el spinner con las categorias
     * @param entryClasses
     */
    private void feedSpinnerCtegory(ArrayList<EntryClass> entryClasses, Spinner spinner){

        List<String> listaSpinnerCategory = new ArrayList<String>();

        for (EntryClass itemEntryClass:entryClasses){
            listaSpinnerCategory.add(itemEntryClass.getCategory().getTerm().toString());
        }

        ArrayAdapter<String> adapter;
        //Asignas el origen de datos desde los recursos
        adapter = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_dropdown_item,listaSpinnerCategory);

        //Asignas el layout a inflar para cada elemento
        //al momento de desplegar la lista
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Seteas el adaptador
        spinner.setAdapter(adapter);

    }


    private static void toastMessage(Context context, String msn)
    {
        Toast.makeText(context, msn, Toast.LENGTH_SHORT).show();
    }
}
