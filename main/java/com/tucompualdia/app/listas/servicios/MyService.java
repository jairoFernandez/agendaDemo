package com.tucompualdia.app.listas.servicios;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.tucompualdia.app.listas.DataBaseManager;

import java.util.Date;


public class MyService extends Service {

    DataBaseManager manager;
    Cursor c;
    Runnable runnable;

    private static MyService instance  = null;

    public MyService() {
        super();
    }

    public static boolean isRunning() {
        return instance != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final Handler handler = new Handler();
        manager = new DataBaseManager(this);
        runnable = new Runnable() {
            public void run() {
                imprimirAlgo();
                handler.postDelayed(runnable, 5000);
            }
        };

        handler.post(runnable);
        //runnable.run();
        // detenerlo handler.removeCallbacks(runnable);

    }

    public void imprimirAlgo(){
        java.util.Date fecha = new Date();
        c = manager.cargarCursorContactos();
        String nombre = null;
        if(c.moveToFirst()){
            do{
               /** telefonos = telefono.getString(telefono.getColumnIndex("telefono"));**/
                //nombre = c.getString(c.getColumnIndex("nombre"));
                nombre = c.getString(c.getColumnIndex("nombre"));
                /** peticiones = telefono.getString(telefono.getColumnIndex("peticion"));**/

                Log.d("Ejemplo", nombre);
            }while(c.moveToNext());
        }

    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }




}
