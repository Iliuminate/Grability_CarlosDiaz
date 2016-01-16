package com.grability.iliuminate.grabilityprueba;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;

public class DetailsActivity extends AppCompatActivity {

    public static  String POSITION="posicion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());



        //**************************************************************************
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //**************************************************************************


        /*Window window = getWindow();
        // Elegir transiciones
        switch (position) {
            // EXPLODE
            case 0:
                Explode t0 = new Explode();
                window.setEnterTransition(t0);
                break;
            // SLIDE
            case 1:
                Slide t1 = new Slide();
                t1.setSlideEdge(Gravity.END);
                window.setEnterTransition(t1);
                break;
            // FADE
            case 2:
                Fade t2 = new Fade();
                window.setEnterTransition(t2);
                break;
            // PERSONALIZADA
            case 3:
                Transition t3 = TransitionInflater.from(this)
                        .inflateTransition(R.transition.detail_enter_trasition);
                window.setEnterTransition(t3);
                break;
            // EVENTOS DE TRANSICIÓN
            case 4:
                Fade t4 = new Fade();
                t4.addListener(
                        new Transition.TransitionListener() {
                            @Override
                            public void onTransitionStart(Transition transition) {

                            }

                            @Override
                            public void onTransitionEnd(Transition transition) {
                                Snackbar.make(
                                        findViewById(R.id.coordinator),
                                        "Terminó la transición",
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onTransitionCancel(Transition transition) {

                            }

                            @Override
                            public void onTransitionPause(Transition transition) {

                            }

                            @Override
                            public void onTransitionResume(Transition transition) {

                            }
                        }
                );
                window.setEnterTransition(t4);
                break;
            // POR DEFECTO
            case 5:
                window.setEnterTransition(null);
                break;

        }*/
    }

    //Declaramos el metodo que usaremos para llamar Actividad con los detalles
    public static void launch(Activity context, int position, EntryClass entryClass) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(POSITION, position);
        context.startActivity(intent);

    }
}
