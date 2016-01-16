package com.grability.iliuminate.grabilityprueba;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grability.iliuminate.grabilityprueba.AdaptersParsing.ParseJson;
import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;
import com.grability.iliuminate.grabilityprueba.ModelClasses.FeedClass;
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.ItemClickSupport;
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.MarginDecoration;
import com.grability.iliuminate.grabilityprueba.AdaptersParsing.MyRecyclerViewAdapterTablet;
import com.grability.iliuminate.grabilityprueba.OfflineManager.JsonOffline;
import com.grability.iliuminate.grabilityprueba.ParametersClasses.KeysFeed;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainNavigationTablet extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //Definicion de los Otros elementos principales
    FeedClass feedClass;
    ArrayList<EntryClass> entries;

    private final String TAG="MainNavigationTablet";
    private final int BloquesIniciales=1;
    Context contexto=MainNavigationTablet.this;
    SeekBar barraColumnas;
    TextView txtColumnas;
    int numeroBloques;
    List<String> listaCategorias;

    //Definicion de elementos para el Recycler View
    private static MyRecyclerViewAdapterTablet recyclerViewAdapter;
    private static RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    NavigationView navigationView;

    //Evento Click
    ItemClickSupport.OnItemClickListener onItemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation_tablet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setMyScreenOrientation();
        instanciarEventosClick();


        barraColumnas=(SeekBar)findViewById(R.id.seekBar);
        txtColumnas=(TextView)findViewById(R.id.txtColumn);
        txtColumnas.setText(BloquesIniciales + "");

        loadItems();
        numeroBloques=(BloquesIniciales);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(contexto, numeroBloques);
        recyclerView.setLayoutManager(gridLayoutManager);


        ItemClickSupport itemClickSupport= ItemClickSupport.addTo(recyclerView);
        itemClickSupport.setOnItemClickListener(onItemClickListener);


        //********************************************************************************************

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.your_action_event, Snackbar.LENGTH_LONG)
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

        barraColumnas.setOnSeekBarChangeListener(onSeekBarChangeListener);



    }



    //***************************************************************************************************************
    //***************************************************************************************************************

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
                        alimentarVistas(feedClass, new JSONObject(aux));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    feedClass = ParseJson.parseJsonFeedClass(response);
                    alimentarVistas(feedClass,response);
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
     * Cargamos los Itema en pantalla (RecyclerView)
     * @param feedClass
     * @param response
     */
    private void alimentarVistas(FeedClass feedClass, JSONObject response){
        entries=new ArrayList<EntryClass>();
        entries.addAll(feedClass.getEntryList());
        forDetailEntries.addAll(feedClass.getEntryList());
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
     * Util para definir la imagen que se va a usar en el adapter
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

        // dpi
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        int densityDpi = metrics.densityDpi;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;

        // Orientación (1 portrait, 2 Landscape)
        int orientation = getResources().getConfiguration().orientation;

        displayParameters.add(widthPixels);//Width
        displayParameters.add(heightPixels);//Height
        displayParameters.add(densityDpi);//Density
        displayParameters.add(orientation);

        return displayParameters;
    }



    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        int j=1;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            j=(progress+1);

            recyclerViewAdapter=new MyRecyclerViewAdapterTablet(contexto, entries, 0, getDisplayParameters(), j);
            recyclerView.setAdapter(recyclerViewAdapter);

            //Ajustamos el numero de columnas que queremos mostrar
            gridLayoutManager.setSpanCount(j);
            gridLayoutManager.onItemsChanged(recyclerView);
            //recyclerViewAdapter.reloadData(entries);
            Log.d(TAG, "Numero de BloquesSeek: " + j);
            txtColumnas.setText((j) + "");
            numeroBloques=j;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };



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


    private boolean IsTablet()
    {
        Log.d(TAG, "ScreenType: " + getResources().getString(R.string.screen_type));
        if(KeysFeed.SCREEN_TYPE.equals(getResources().getString(R.string.screen_type))){
            return false;
        }
        else{ return true;}
    }

    private void setMyScreenOrientation()
    {
        if (IsTablet())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    /**
     * Recuperamos el tamaño de la pantalla
     * @return
     */
    private double screenInches() {
        //DisplayMetrics dm = contexto.Resources.DisplayMetrics;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float screenWidth = dm.widthPixels/ dm.xdpi;
        float screenHeight = dm.heightPixels / dm.ydpi;
        double size = Math.sqrt(Math.pow(screenWidth, 2) + Math.pow(screenHeight, 2));
        Log.d(TAG,"ScreeInches: "+size);
        return size;
    }



    //***************************************************************************************************************
    //***************************************************************************************************************

    /**
     * Inicializamos los eventos Click
     */
    public void instanciarEventosClick(){

        onItemClickListener=new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                mostrarDetalles(forDetailEntries.get(position));
            }
        };
    }


    private void mostrarDetalles(EntryClass entryClass){
        Intent intent=new Intent(contexto, DetailsActivity.class);
        intent.putExtra(KeysFeed.KEY_ENTRY,entryClass);
        startActivity(intent);
    }


    //***************************************************************************************************************
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
        int id = item.getItemId();

        /*if (id == R.id.rotarLandscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if (id == R.id.rotarPortrait) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }*/

        return super.onOptionsItemSelected(item);
    }

    ArrayList<EntryClass> forDetailEntries=new ArrayList<EntryClass>();

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        String label=(String)item.getTitle();
        if(entries.equals(null))
        {entries= new ArrayList<EntryClass>();}
        else{entries.clear();}
        entries.addAll(feedClass.getEntryList());
        toastMessage(label);
        forDetailEntries.clear();

        for (String lb: listaCategorias){
            if(lb.equals(label)){
                forDetailEntries.addAll(resultQuery(lb, entries));
                recyclerViewAdapter.reloadData(forDetailEntries);
            }
        }

        if(label.equals(getResources().getString(R.string.all_aplication))){
            forDetailEntries.addAll(feedClass.getEntryList());
            recyclerViewAdapter.reloadData(feedClass.getEntryList());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
