package com.grability.iliuminate.grabilityprueba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        // Clase en la que está el código a ejecutar
        TimerTask timerTask = new TimerTask()
        {
            public void run()
            {
                Lanzar_Inicio();
            }
        };
        // Aquí se pone en marcha el timer cada segundo.
        Timer timer = new Timer();
        // Dentro de 0 milisegundos avísame cada 1000 milisegundos
        timer.schedule(timerTask, 3200);
        //timer.scheduleAtFixedRate(timerTask, 0, 3000);//Para controlar ciclos


        /*mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);*/
    }



    private void Lanzar_Inicio()
    {
        Intent i=new Intent(this, MyRecycler.class);
        startActivity(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
