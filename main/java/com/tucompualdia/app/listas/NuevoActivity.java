package com.tucompualdia.app.listas;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class NuevoActivity extends ActionBarActivity {

    TextView etNombre,etTelefono,etPeticion;
    Button btnAgregar;
    DataBaseManager manager;
    NotificationManager nm;
    private static final int ID_NOTIFICACION_PERSONAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);



        etNombre  = (TextView)findViewById(R.id.etNombres);
        etTelefono = (TextView)findViewById(R.id.edTelefono);
        etPeticion = (TextView)findViewById(R.id.edPeticion);
        btnAgregar = (Button)findViewById(R.id.btnAgregar);

        manager = new DataBaseManager(this);

        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialogo();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nuevo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void dialogo(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Agregar Nuevo Contacto");
        dialog.setMessage("¿La información es correcta?");
        dialog.setCancelable(false);

        dialog.setPositiveButton("Si",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                positivo();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                negativo();
            }
        });
        dialog.show();
    }

    public void positivo(){
        String nombreAgegado= etNombre.getText().toString();
        String telefonoAgegado= etTelefono.getText().toString();

        manager.insertar(etNombre.getText().toString(),etTelefono.getText().toString(),etPeticion.getText().toString());
        Toast.makeText(getApplicationContext(),"Agregado",Toast.LENGTH_SHORT).show();
        etNombre.setText("");
        etTelefono.setText("");
        etPeticion.setText("");

        Notification notification = new Notification(R.drawable.editar,"Has agregado un nuevo contacto", System.currentTimeMillis());
        PendingIntent intencionPendiente = PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),RecomendacionesActivity.class),0);

        notification.setLatestEventInfo(getApplicationContext(),"Felicitaciones","Has Agregado a "+nombreAgegado,intencionPendiente);
        nm.notify(ID_NOTIFICACION_PERSONAL,notification);
    }

    public void negativo(){
        Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_SHORT).show();
    }
}
