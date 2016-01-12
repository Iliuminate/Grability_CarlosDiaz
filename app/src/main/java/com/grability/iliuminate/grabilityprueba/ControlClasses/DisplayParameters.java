package com.grability.iliuminate.grabilityprueba.ControlClasses;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iliuminate on 10/01/2016.
 */
public class DisplayParameters extends Activity{

    Context context;

    public DisplayParameters(Context context) {
        this.context=context;
    }

    /**
     * Float [0-2]: Width, Height, Density
     * @return
     */
    public List<Float> getDisplayParameters()
    {
        DisplayMetrics outMetrics = new DisplayMetrics ();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;

        List<Float> pantalla=new ArrayList<Float>();
        pantalla.add(dpWidth);
        pantalla.add(dpHeight);
        pantalla.add(density);

        return pantalla;
    }
}
