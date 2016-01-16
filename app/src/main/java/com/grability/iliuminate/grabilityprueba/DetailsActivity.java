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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.grability.iliuminate.grabilityprueba.ModelClasses.EntryClass;
import com.grability.iliuminate.grabilityprueba.OfflineManager.GetInternalImage;
import com.grability.iliuminate.grabilityprueba.ParametersClasses.KeysFeed;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DETAIL";
    public static  String POSITION="posicion";

    ImageView imageView;
    TextView txtTitulo,
            txtCategoria,
            txtPrecio,
            txtDescripcion,
            txtEnlace,
            txtLanzamiento;

    EntryClass entryClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());


        entryClass=(EntryClass)getIntent().getExtras().get(KeysFeed.KEY_ENTRY);
        Log.d(TAG,entryClass.toString());
        inicializarVariables();
        alimentarVistas();



        //**************************************************************************
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getResources().getText(R.string.your_action_event), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //**************************************************************************

    }

    private void inicializarVariables()
    {
        imageView=(ImageView)findViewById(R.id.imageDetails);
        txtTitulo=(TextView)findViewById(R.id.detailTitle);
        txtCategoria=(TextView)findViewById(R.id.detailCategoryTxt);
        txtPrecio=(TextView)findViewById(R.id.detailPrice);
        txtDescripcion=(TextView)findViewById(R.id.detailSumaryText);
        txtEnlace=(TextView)findViewById(R.id.detailLink);
        txtLanzamiento=(TextView)findViewById(R.id.detailRelease);
    }

    private void alimentarVistas()
    {
        GetInternalImage getInternalImage =new GetInternalImage(
                entryClass.getIdNameImage(),imageView,DetailsActivity.this);
        Log.d(TAG,"ImageName: "+entryClass.getIdNameImage());

        txtTitulo.setText(entryClass.getIm_name());
        txtCategoria.setText(entryClass.getCategory().getTerm());
        txtPrecio.setText("$ "+entryClass.getIm_price().getAmount()+" "+entryClass.getIm_price().getCurrency());
        txtDescripcion.setText(entryClass.getSummary());
        txtEnlace.setText(entryClass.getLink().getHref());
        txtLanzamiento.setText(entryClass.getIm_relaseDate().getLabelAttributes());

    }





    //Algunas animaciones para agregar posteriormente
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
