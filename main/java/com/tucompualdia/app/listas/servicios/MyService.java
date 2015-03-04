package com.tucompualdia.app.listas.servicios;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.tucompualdia.app.listas.DataBaseManager;
import com.tucompualdia.app.listas.R;
import com.tucompualdia.app.listas.RecomendacionesActivity;

import java.util.Date;


public class MyService extends Service {

    DataBaseManager manager;
    Cursor c;
    Runnable runnable;
    NotificationManager nm;



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
                handler.postDelayed(runnable, 3600000);
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

                final int ID_NOTIFICACION_PERSONAL = c.getPosition();
                nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


                nombre = c.getString(c.getColumnIndex("nombre"));

                Notification notification = new Notification(R.drawable.editar,"Recordatorio de Tu Agenda", System.currentTimeMillis());
                PendingIntent intencionPendiente = PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),RecomendacionesActivity.class),0);

                notification.setLatestEventInfo(getApplicationContext(),"No olvides ","LLamar a "+nombre,intencionPendiente);

                nm.notify(ID_NOTIFICACION_PERSONAL,notification);

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
