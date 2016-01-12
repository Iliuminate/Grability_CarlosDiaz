package com.grability.iliuminate.grabilityprueba;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grability.iliuminate.grabilityprueba.Adapters.EntryAdapter;
import com.grability.iliuminate.grabilityprueba.ControlClasses.ParseJson;
import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;
import com.grability.iliuminate.grabilityprueba.ModelClasses.FeedClass;
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.MyRecyclerViewAdapter;
import com.grability.iliuminate.grabilityprueba.OfflineManager.GetInternalImage;
import com.grability.iliuminate.grabilityprueba.OfflineManager.JsonOffline;
import com.grability.iliuminate.grabilityprueba.OfflineManager.SaveImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyRecycler extends AppCompatActivity {

    //Definicion de los Otros elementos principales
    JsonOffline jsonOffline;
    FeedClass feedClass;
    Button btn1, botonN;
    Spinner spinnerCategory;
    private final String TAG="MyRecycler";
    String aux;
    Context context=this;

    //Definicion de elementos para el Recycler View
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private static RecyclerView recyclerView;
    private static MyRecyclerViewAdapter recyclerViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler);


        btn1=(Button)findViewById(R.id.btn1);
        botonN=(Button)findViewById(R.id.buttonN);
        spinnerCategory=(Spinner)findViewById(R.id.spinnerCategory);
        recyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);


        myOnClickListener = new MyOnClickListener();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);


        loadItems();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MyRecyclerTablet.class));
            }
        });


        /*btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonOffline=new JsonOffline(context);
                try {
                    //Recuperamos la información almacenada en el dispositivo
                    aux = jsonOffline.readLocalFile();
                    feedClass = ParseJson.parseJsonFeedClass(new JSONObject(aux));
                } catch (JSONException e) {
                    Log.e(TAG, "Read/Parse JSON Offline: " + e.getMessage());
                }

                //Parseamos la información y la llevamos a un ADAPTER
                //adapterList = new EntryAdapter(context, feedClass.getEntryList(), 1, getDisplayParameters());
                recyclerViewAdapter = new MyRecyclerViewAdapter(context, feedClass.getEntryList(), 0,getDisplayParameters());

                recyclerView.setAdapter(recyclerViewAdapter);
            }
        });*/

        botonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainNavigation.class));
            }
        });

    }



    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            toastMessage("Se preciocó un elemento");
            /*String selectedArticleUrl = getSelectedArticleUrl(v);
            showSelectedArticle(selectedArticleUrl);*/
        }
    }

   /* //
    private String getSelectedArticleUrl(View view) {
        int selectedItemPosition = recyclerView.getChildPosition(view);
        String url = MyArticleData.articles[selectedItemPosition][1];
        return url;
    }

    //
    private void showSelectedArticle(String articleUrl) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("articleUrl", articleUrl);
        startActivity(intent);
    }*/






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
        Log.i(TAG, "Orientación       = " + orientation);

        displayParameters.add(widthPixels);//Width
        displayParameters.add(heightPixels);//Height
        displayParameters.add(densityDpi);//Density
        displayParameters.add(orientation);

        return displayParameters;
    }


    private FeedClass loadItems()
    {
        /*Creamos la cola de peticiones (Representacion logica "RequestQueue")
         *INICIALIZAMOS para realizar las peticiones
         */
        RequestQueue queu = Volley.newRequestQueue(this);
        String url = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                // Capturamos la respuesta y la dejamos a disposición
                feedClass=ParseJson.parseJsonFeedClass(response);


                //Creamos el Adapter y cargamos en pantalla
                recyclerViewAdapter = new MyRecyclerViewAdapter(context, feedClass.getEntryList(), 0,getDisplayParameters());
                recyclerView.setAdapter(recyclerViewAdapter);

                feedSpinnerCtegory(feedClass.getEntryList(), spinnerCategory);

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                toastMessage("Error Volley => )"+error.toString());
                Log.e(TAG, "Error Respuesta en JSON: " + error.getMessage());
            }
        });

        //Agregamoa la peticion a la cola
        queu.add(jsonObjectRequest);

        return feedClass;
    }



    private void toastMessage(String msn)
    {
        Toast.makeText(this, msn, Toast.LENGTH_SHORT).show();
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

        //Asignas el origen de datos desde los recursos
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,listaSpinnerCategory);
        spinner.setAdapter(adapter);

    }
}
