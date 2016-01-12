package com.grability.iliuminate.grabilityprueba.OfflineManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.grability.iliuminate.grabilityprueba.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Iliuminate on 10/01/2016.
 */
public class GetInternalImage extends Activity{

    String filename;
    ImageView imageView;
    Context context;
    private static final String TAG="GetIntetnalImage";

    /**
     *
     * @param filename
     * @param imageView
     */
    public GetInternalImage(String filename, ImageView imageView, Context context) {
        this.filename=filename;
        this.imageView=imageView;
        this.context=context;

        /*Iniciamo el proceso de recuperación y de visualización de la imagen*
        / en el ImageView que se pase como parametro
         */
        getBitmap();
    }

    private void getBitmap(){

        Bitmap bitmap = null;

        try{
            FileInputStream fileInputStream =
                    new FileInputStream(context.getApplicationContext().getFilesDir().getPath()+ "/"+filename);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            imageView.setImageBitmap(bitmap);
        }catch (IOException io){
            Log.e(TAG, "Error recuperando Imagen: "+io.getMessage());
            imageView.setImageResource(R.mipmap.ic_launcher);
        }


    }


    public static boolean existBitmap(Context contexto, String filename){

        FileInputStream fileInputStream =null;

        try {
            fileInputStream = new FileInputStream(contexto.getApplicationContext().getFilesDir().getPath()+ "/"+filename);
            if(fileInputStream.equals(null))
            { return false;
            }else{ return true;}
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error Verificando Archivo: " + e.getMessage());
            return false;
        }


    }

}
