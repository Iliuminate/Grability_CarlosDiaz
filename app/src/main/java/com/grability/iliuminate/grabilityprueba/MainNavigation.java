package com.grability.iliuminate.grabilityprueba;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grability.iliuminate.grabilityprueba.ControlClasses.ParseJson;
import com.grability.iliuminate.grabilityprueba.ControlClasses.Urls;
import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;
import com.grability.iliuminate.grabilityprueba.ModelClasses.FeedClass;
import com.grability.iliuminate.grabilityprueba.MyRecyclerView.MyRecyclerViewAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    FeedClass feedClass;
    Button btn1, botonN;
    private final String TAG="MyRecycler";
    List<String> listaCategorias;
    Context context=this;
    NavigationView navigationView;

    //Definicion de elementos para el Recycler View
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private static RecyclerView recyclerView;
    private static MyRecyclerViewAdapter recyclerViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        btn1=(Button)findViewById(R.id.btn1);
        botonN=(Button)findViewById(R.id.buttonN);

        recyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
        listaCategorias=new ArrayList<String>();

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

        botonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //invalidateOptionsMenu();
                ArrayList<EntryClass> aux=new ArrayList<EntryClass>();
                aux=feedClass.getEntryList();
                Log.d("BUTTON2", "Tamaño: " + aux.size());
                recyclerViewAdapter.reloadData(resultQuery("Games", aux));
                toastMessage("Se esta tratando de manejar el GAMES");
            }
        });


        //************************************************************************************************

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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.inflateMenu(R.menu.grability_menu);
        navigationView.setNavigationItemSelectedListener(this);

        //************************************************************************************************


    }


    //************************************************************************************************

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            toastMessage("Se preciocó un elemento");
            /*String selectedArticleUrl = getSelectedArticleUrl(v);
            showSelectedArticle(selectedArticleUrl);*/
        }
    }

    /**
     * Parametros de la pantalla
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


    /**
     * Para cargar todos los Items en pantalla
     * y cargar el menu en el NavigationView.Menu
     * @return
     */
    private FeedClass loadItems()
    {
        /*Creamos la cola de peticiones (Representacion logica "RequestQueue")
         *INICIALIZAMOS para realizar las peticiones
         */
        RequestQueue queu = Volley.newRequestQueue(this);
        String url = Urls.URL_GLOBAL;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                // Capturamos la respuesta y la dejamos a disposición
                feedClass = ParseJson.parseJsonFeedClass(response);
                ArrayList<EntryClass> entryList= new ArrayList<EntryClass>();
                entryList.addAll(feedClass.getEntryList());

                //Creamos el Adapter y cargamos en pantalla
                recyclerViewAdapter = new MyRecyclerViewAdapter(context, entryList, 0,getDisplayParameters());
                recyclerView.setAdapter(recyclerViewAdapter);

                //Cargamos la lista de categorias
                listaCategorias = getListCategory(feedClass.getEntryList());
                agregarItemsNavigationViewMenu(listaCategorias,navigationView);
                invalidateOptionsMenu();
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
            menues.add(listaNames.get(j));
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


    private void toastMessage(String msn){
        Toast.makeText(this, msn, Toast.LENGTH_SHORT).show();
    }


    //************************************************************************************************
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
        getMenuInflater().inflate(R.menu.grability_menu, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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
