package com.tucompualdia.app.listas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DatosActivity extends ActionBarActivity {


    TextView tv,tvTelefono,tvPeticion;
    DataBaseManager manager;
    String idContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        Bundle extras = getIntent().getExtras();

        manager = new DataBaseManager(this);

        idContacto = extras.getString("id");
        Cursor c = manager.buscarId(idContacto);

        tv = (TextView)findViewById(R.id.tvID);
        tvTelefono = (TextView)findViewById(R.id.tvtelefono);
        tvPeticion = (TextView)findViewById(R.id.tvpeticion);

        String nombres = null;
        String telefonos = null;
        String peticiones = null;

        if(c.moveToFirst()){
            do{
               nombres = c.getString(c.getColumnIndex("nombre"));
               telefonos = c.getString(c.getColumnIndex("telefono"));
               peticiones = c.getString(c.getColumnIndex("peticion"));
            }while(c.moveToNext());
        }
        /**String nombres = c.getString(c.getColumnIndex("nombre"));
        String telefonos = c.getString(c.getColumnIndexOrThrow("telefono"));
        String peticion = c.getString(c.getColumnIndexOrThrow("peticion"));*/

        tv.setText(nombres);
        tvTelefono.setText(telefonos);
        tvPeticion.setText(peticiones);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_datos, menu);
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
        if (id == R.id.eliminar) {
            dialogo();
            return true;
        }

        if (id == R.id.editar) {
            Intent editar = new Intent(getApplicationContext(),EditarActivity.class);
            editar.putExtra("id", idContacto);
            editar.putExtra("nombre",tv.getText().toString());
            editar.putExtra("telefono",tvTelefono.getText().toString());
            editar.putExtra("peticion",tvPeticion.getText().toString());
            startActivity(editar);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dialogo(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Eliminar contacto");
        dialog.setMessage("¿Está seguro?");
        dialog.setCancelable(false);

        dialog.setPositiveButton("Si",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                positivo();
            }
        });

        dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                negativo();
            }
        });

        dialog.show();
    }

    public void positivo(){
        manager.eliminar(idContacto);
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    public void negativo(){
        //Toast.makeText(getApplicationContext(),"Negativo",Toast.LENGTH_SHORT).show();
    }
}
