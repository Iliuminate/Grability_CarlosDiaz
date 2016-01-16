package com.grability.iliuminate.grabilityprueba;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    private static final String TAG = "SPLASH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        getDisplayParameters();


        // Clase en la que está el código a ejecutar
        TimerTask timerTask = new TimerTask()
        {
            public void run()
            {
                int densidad=getDisplayParameters().get(2);

                if(densidad<380){
                    Log.e(TAG,"Desidad <380: "+densidad);
                    lanzarMainTablet();
                }else{
                    if (densidad>540)
                    {
                        Log.e(TAG,"Desidad >540: "+densidad);
                        lanzarMainTablet();
                    }
                    else
                        lanzarMain();
                }
            }
        };
        // Aquí se pone en marcha el timer cada segundo.
        Timer timer = new Timer();
        // Dentro de 0 milisegundos avísame cada 1000 milisegundos
        timer.schedule(timerTask, 3200);
        //timer.scheduleAtFixedRate(timerTask, 0, 3000);//Para controlar ciclos
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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

    private void lanzarMain()
    {
        Intent i=new Intent(this, MainNavigation.class);
        startActivity(i);
    }


    private void lanzarMainTablet()
    {
        Intent i=new Intent(this, MainNavigationTablet.class);
        startActivity(i);
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
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
        Log.i(TAG, "Orientación       = " + orientation);

        displayParameters.add(widthPixels);//Width
        displayParameters.add(heightPixels);//Height
        displayParameters.add(densityDpi);//Density
        displayParameters.add(orientation);



        return displayParameters;
    }

}
