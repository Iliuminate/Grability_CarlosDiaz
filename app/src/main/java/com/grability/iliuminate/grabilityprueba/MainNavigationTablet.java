package com.grability.iliuminate.grabilityprueba;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.MyRecyclerViewAdapterTablet;
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.ViewHolderClass;
import com.grability.iliuminate.grabilityprueba.OfflineManager.JsonOffline;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainNavigationTablet extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //Definicion de los Otros elementos principales
    FeedClass feedClass;
    private final String TAG="MyRecyclerTablet";
    Context contexto=MainNavigationTablet.this;
    int numeroBloques;
    List<String> listaCategorias;

    //Definicion de elementos para el Recycler View
    private static MyRecyclerViewAdapterTablet recyclerViewAdapter;
    private static RecyclerView recyclerView;
    NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation_tablet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        loadItems();
        numeroBloques=determinarBloques();


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(contexto, numeroBloques));



        //********************************************************************************************

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //***************************************************************************************************************
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
                }
            });
        }

        @Override
        public int getItemCount() {
            return entryClasses.size();
        }
    }

    //*************************************************************************************************



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
                if(response.isNull("feed")) {
                    JsonOffline jsonOffline=new JsonOffline(contexto);
                    String aux=jsonOffline.readLocalFile();
                    try {
                        feedClass = ParseJson.parseJsonFeedClass(new JSONObject(aux));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    feedClass = ParseJson.parseJsonFeedClass(response);
                    prepararModoOffline(contexto, response.toString());
                    ArrayList<EntryClass> entryList = new ArrayList<EntryClass>();
                    entryList.addAll(feedClass.getEntryList());

                    //Creamos el Adapter y cargamos en pantalla
                    recyclerViewAdapter = new MyRecyclerViewAdapterTablet(contexto, entryList, 0, getDisplayParameters(), numeroBloques);
                    recyclerView.setAdapter(recyclerViewAdapter);

                    //Cargamos la lista de categorias
                    listaCategorias = getListCategory(feedClass.getEntryList());
                    agregarItemsNavigationViewMenu(listaCategorias, navigationView);
                    invalidateOptionsMenu();
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                toastMessage("Favor verifique su conexión a Internet"+error.toString());
                Log.e(TAG, "Error Respuesta en JSON: " + error.getMessage());
            }
        });

        //Agregamoa la peticion a la cola
        queu.add(jsonObjectRequest);

        return feedClass;
    }

    /**
     * Para optener categorias de las EntrClass
     * @param entryClasses
     */
    private List<String> getListCategory(ArrayList<EntryClass> entryClasses){

        List<String> listaNames=new ArrayList<String>();

        for (int i=0; i<entryClasses.size(); i++){
            listaNames.add(entryClasses.get(i).getCategory().getTerm());
        }

        //Quitamos los elementos duplicados
        listaNames = new ArrayList<String>(new HashSet<String>(listaNames));

        return listaNames;
    }


    /**
     * Para agregar la lista de elementos al NavigatorView
     * @param listaNames
     * @param navigationView
     */
    private void agregarItemsNavigationViewMenu(List<String> listaNames, NavigationView navigationView){

        //Seleccionamos el menu cargado en el NavigatorView
        Menu menues=navigationView.getMenu();
        for(int j=0;j<listaNames.size();j++)
        {
            //menues.add(elementoMenus.get(j).getGroupId(), elementoMenus.get(j).getItemId(), elementoMenus.get(j).getOrder(), elementoMenus.get(j).getTitle());
            menues.add(listaNames.get(j)).setIcon(R.drawable.abc_btn_radio_to_on_mtrl_015);

        }
    }


    /**
     * Retorna una lista con el resultado de la busqueda por categoria
     * @param paramQuery
     * @param entryClassList
     * @return
     */
    private ArrayList<EntryClass> resultQuery(String paramQuery, ArrayList<EntryClass> entryClassList){

        ArrayList<EntryClass> entryClasses=new ArrayList<EntryClass>();

        for (EntryClass entryClass:entryClassList){
            if(entryClass.getCategory().getTerm().equals(paramQuery))
            {
                entryClasses.add(entryClass);
                Log.d("QUERY",entryClass.getTitle()+" -Cat:"+entryClass.getCategory());
            }
        }
        return entryClasses;
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


    private void prepararModoOffline(Context context, String textoJson)
    {
        final JsonOffline jsonOffline=new JsonOffline(context);
        if(jsonOffline.writeLocalFile(textoJson))
        {Log.d(TAG,"JSON almacenado: "+textoJson);}
        else{Log.d(TAG,"No se pudo escribir el fichero");}
    }

    public void toastMessage(String msn){
        Toast.makeText(contexto, msn, Toast.LENGTH_SHORT).show();
    }

    private int determinarBloques(){
        int j=3;

        return j;
    }





    //***************************************************************************************************************


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation_tablet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        String label=(String)item.getTitle();
        ArrayList<EntryClass> entries= new ArrayList<EntryClass>();
        entries.addAll(feedClass.getEntryList());
        toastMessage(label);

        for (String lb: listaCategorias){
            if(lb.equals(label)){
                recyclerViewAdapter.reloadData(resultQuery(lb, entries));
            }
        }

        if(label.equals(getResources().getString(R.string.all_aplication))){
            recyclerViewAdapter.reloadData(entries);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
