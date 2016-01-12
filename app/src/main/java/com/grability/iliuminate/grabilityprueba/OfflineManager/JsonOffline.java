package com.grability.iliuminate.grabilityprueba.OfflineManager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Iliuminate on 10/01/2016.
 */
public class JsonOffline {

    private final String TAG="JsonOffline";
    private final String filename = "JSONObject";
    Context context;

    public JsonOffline(Context context) {
        this.context=context;
    }

    public boolean writeLocalFile(String jsonObject) {

        FileOutputStream outputStream;
        boolean ws=false;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonObject.getBytes());
            outputStream.close();
            ws=true;
        } catch (Exception e) {
            Log.e(TAG, "Error escribiendo el Archivo: " + e.getMessage());
        }

        return ws;

    }

    public String readLocalFile(){

        BufferedReader br;
        String response = null;

        try {
            StringBuffer output = new StringBuffer();
            br = new BufferedReader(new FileReader(context.getApplicationContext().getFilesDir().getPath()+ "/"+filename));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line +"n");
            }
            response = output.toString();

        } catch (IOException e) {
            Log.e(TAG, "Error leyendo el Archivo: " + e.getMessage());
        }

        return response;
    }
}
