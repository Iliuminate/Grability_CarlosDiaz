package com.grability.iliuminate.grabilityprueba;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grability.iliuminate.grabilityprueba.Adapters.EntryAdapter;
import com.grability.iliuminate.grabilityprueba.ControlClasses.ParseJson;
import com.grability.iliuminate.grabilityprueba.ModelClasses.FeedClass;

import com.grability.iliuminate.grabilityprueba.OfflineManager.JsonOffline;
import com.grability.iliuminate.grabilityprueba.RecyclerView.MyActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapterList;
    TextView display;
    Button btnWrite, btnRead;
    String TAG="MainActivity2";
    Context context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView= (ListView) findViewById(R.id.listViewItems);
        display=(TextView)findViewById(R.id.textView);

        btnWrite=(Button)findViewById(R.id.btn1);
        btnRead=(Button)findViewById(R.id.btn2);



        testJSONVolley();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarActividadTestRecycler();
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });


        //Prueba de Escritura y Lectura del Archivo plano
        final JsonOffline jsonOffline=new JsonOffline(context);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Guardamos el String en un archivo de Texto
                Log.d(TAG,"JSON1: "+jsonTexto);
                if(jsonOffline.writeLocalFile(jsonTexto))
                {Log.d(TAG,"JSON2: "+jsonTexto);}
                else{Log.d(TAG,"No se pudo escribir el fichero");}

            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"READ JsonLocal: "+jsonOffline.readLocalFile());
            }
        });


    }


    private void toastMessage(String msn)
    {
        Toast.makeText(this, msn, Toast.LENGTH_SHORT).show();
    }

    private void lanzarRecyclerView()
    {
        Intent i=new Intent(context, MyActivity.class);
        startActivity(i);
    }

    private void lanzarActividadTestImageLoad()
    {
        Intent i=new Intent(context, LoadImageTest.class);
        startActivity(i);
    }

    private void lanzarActividadTestRecycler()
    {
        Intent i=new Intent(context, MyRecycler.class);
        startActivity(i);
    }





    FeedClass feedClass;
    String jsonTexto="Nada";

    private FeedClass testJSONVolley()
    {
        /*Creamos la cola de peticiones (Representacion logica "RequestQueue")
         *INICIALIZAMOS para realizar las peticiones
         */
        RequestQueue queu = Volley.newRequestQueue(this);
        //String url = "http://servidorexterno.site90.com/datos/social_media.json";
        String url = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                // Capturamos la respuesta y la dejamos a disposición
                String respuestabk=response.toString();
                display.setText("Response => " + "EVENTO");

                feedClass=ParseJson.parseJsonFeedClass(response);

                // Aquí debemos parsear el JSON
                Log.d(TAG,"RESPONSE: "+ respuestabk);
                jsonTexto=respuestabk;
                //Creamos el Adapter
                //adapterList = new EntryAdapter(context, feedClass.getEntryList(),0);
                adapterList = new EntryAdapter(context, feedClass.getEntryList(),0,getDisplayParameters());

                //Cargamos la lista en pantalla
                listView.setAdapter(adapterList);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                toastMessage("Error Volley => )"+error.toString());
                Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());
            }
        });

        //Agregamoa la peticion a la cola
        queu.add(jsonObjectRequest);

        return feedClass;
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
        /*Log.i(TAG, "Ancho             = " + width);
        Log.i(TAG, "Alto              = " + height);*/

        // dpi
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        int densityDpi = metrics.densityDpi;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;
        /*Log.i(TAG, "Ancho en píxeles  = " + widthPixels);
        Log.i(TAG, "Alto en píxeles   = " + heightPixels);
        Log.i(TAG, "Densidad dpi      = " + densityDpi);
        Log.i(TAG, "x dpi             = " + xdpi);
        Log.i(TAG, "y dpi             = " + ydpi);*/


        // Orientación (1 portrait, 2 Landscape)
        int orientation = getResources().getConfiguration().orientation;
        /*Log.i(TAG, "Orientación       = " + orientation);*/


        displayParameters.add(widthPixels);//Width
        displayParameters.add(heightPixels);//Height
        displayParameters.add(densityDpi);//Density
        displayParameters.add(orientation);

        return displayParameters;
    }


}
