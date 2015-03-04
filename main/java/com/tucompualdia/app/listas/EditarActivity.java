package com.tucompualdia.app.listas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditarActivity extends ActionBarActivity {

    EditText edNombres,edTelefono,edPeticion;
    Button btnEditar;
    DataBaseManager manager;
    String idContacto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);



        manager = new DataBaseManager(this);

        Bundle extras = getIntent().getExtras();
        idContacto = extras.getString("id");
        String nombres = extras.getString("nombre");
        String telefono = extras.getString("telefono");
        String peticion = extras.getString("peticion");



        edNombres = (EditText)findViewById(R.id.etNombresEditar);
        edTelefono = (EditText)findViewById(R.id.edTelefonoEditar);
        edPeticion = (EditText)findViewById(R.id.edPeticionEditar);

        edNombres.setText(nombres);
        edTelefono.setText(telefono);
        edPeticion.setText(peticion);

        btnEditar = (Button)findViewById(R.id.btnEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar, menu);
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
        dialog.setTitle("Actualizar contacto");
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
        manager.modificar(idContacto, edNombres.getText().toString(), edTelefono.getText().toString(), edPeticion.getText().toString());
        Toast.makeText(getApplicationContext(),"Actualizado correctamente",Toast.LENGTH_LONG).show();
        Intent volver = new Intent(getApplicationContext(),DatosActivity.class);
        volver.putExtra("id",idContacto);
        startActivity(volver);
    }

    public void negativo(){

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent atras = new Intent(getApplicationContext(),DatosActivity.class);
        atras.putExtra("id", idContacto);
        startActivity(atras);
    }
}
