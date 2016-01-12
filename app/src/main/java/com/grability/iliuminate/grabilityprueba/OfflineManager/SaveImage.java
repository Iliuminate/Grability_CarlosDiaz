package com.grability.iliuminate.grabilityprueba.OfflineManager;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import android.app.Activity;



/**
 * Created by Iliuminate on 10/01/2016.
 */
public class SaveImage extends Activity{

    String filename;
    Bitmap bitmap;
    Context context;
    private final String TAG="SaveImage";


    public SaveImage(String fileName, Bitmap bitmap, Context context) {

        this.filename = fileName;
        this.bitmap=bitmap;
        this.context=context;

        FileOutputStream outputStream;

        try {
            Log.d(TAG,"FileName: "+filename);
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            //Escribimos la Imagen en el almacenamiento Interno
            outputStream.write(bitmapToByte(bitmap));
            outputStream.close();
        } catch (Exception e) {
            Log.e(TAG,"Error en SaveImage: "+e.getMessage());
        }
    }


    //Convertimos el Bitmap en Byte oara almacenar la imagen en la memoria interna
    private byte[] bitmapToByte(Bitmap bitmap) throws Exception
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return  byteArray;
    }
}
