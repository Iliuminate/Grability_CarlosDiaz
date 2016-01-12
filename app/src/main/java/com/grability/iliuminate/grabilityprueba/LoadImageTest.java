package com.grability.iliuminate.grabilityprueba;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

public class LoadImageTest extends AppCompatActivity {


    //Definicion de los Otros elementos principales
    SaveImage saveImage;
    GetInternalImage getInternalImage;
    ArrayAdapter adapterList;
    JsonOffline jsonOffline;
    FeedClass feedClass;

    Button btn1, btn2;
    ListView lsita1;
    private final String TAG="LoadImageTest";
    String aux;

    //Definicion de elementos para el Recycler View
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private static RecyclerView recyclerView;
    private static MyRecyclerViewAdapter myAdapter;
    private ArrayList<EntryClass> articleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image_test);

        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        lsita1=(ListView)findViewById(R.id.lista1);

        jsonOffline=new JsonOffline(LoadImageTest.this);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    aux=jsonOffline.readLocalFile();
                    feedClass= ParseJson.parseJsonFeedClass(new JSONObject(aux));
                } catch (JSONException e) {
                    Log.e(TAG,"Read/Parse JSON Offline: "+e.getMessage());
                }
                adapterList = new EntryAdapter(LoadImageTest.this, feedClass.getEntryList(),1,getDisplayParameters());

                //Cargamos la lista en pantalla
                lsita1.setAdapter(adapterList);
            }
        });
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
