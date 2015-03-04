package com.tucompualdia.app.listas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tucompualdia.app.listas.adaptadores.AdaptadorCursorContactos;
import com.tucompualdia.app.listas.servicios.MyService;


public class MainActivity extends ActionBarActivity {

    ListView listado;
    DataBaseManager manager;
    Cursor cursor;
    AdaptadorCursorContactos adapter;
    Cursor c;
    Cursor telefono;
    TextView tv;
    ImageButton bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent in = new Intent(MainActivity.this,MyService.class);
        if(!MyService.isRunning())
            MainActivity.this.startService(in);


        tv = (TextView) findViewById(R.id.texto);
        bt = (ImageButton) findViewById(R.id.btnSearch);

        manager = new DataBaseManager(this);

        cursor = manager.cargarCursorContactos();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        listado = (ListView) findViewById(R.id.listado);

        String[] from = new String[]{manager.CN_NAME, manager.CN_PHONE};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        adapter = new AdaptadorCursorContactos(this,cursor);
        listado.setAdapter(adapter);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), DatosActivity.class);
                i.putExtra("id", "" + id);
                startActivity(i);
            }
        });


        listado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String idCont = String.valueOf(id);
                dialogo(idCont);
                return false;
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BuscarTags().execute();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.nuevo) {
            Intent i = new Intent(getApplicationContext(),NuevoActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class BuscarTags extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Buscando ....",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(),"Finalizado ....",Toast.LENGTH_LONG).show();

            adapter.changeCursor(c);

        }

        @Override
        protected Void doInBackground(Void... params) {

            c = manager.buscarNombre(tv.getText().toString());

            return null;
        }
    }

    public void dialogo(final String idContacto){
        String [] opciones = {"Llamar","Editar","Eliminar"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones")
                .setItems(opciones, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        String telefonos = null;
                        String peticiones = null;
                        String nombres = null;

                        switch(which) {
                            case 0:

                                telefono = manager.buscarId(idContacto);

                                if(telefono.moveToFirst()){
                                    do{
                                        telefonos = telefono.getString(telefono.getColumnIndex("telefono"));

                                    }while(telefono.moveToNext());
                                }


                                String fono = telefonos;
                                Uri numero = Uri.parse( "tel:" + fono.toString() );
                                Intent intent = new Intent(Intent.ACTION_CALL, numero);
                                startActivity(intent);

                            break;
                            case 1:
                                telefono = manager.buscarId(idContacto);

                                if(telefono.moveToFirst()){
                                    do{
                                        telefonos = telefono.getString(telefono.getColumnIndex("telefono"));
                                        nombres = telefono.getString(telefono.getColumnIndex("nombre"));
                                        peticiones = telefono.getString(telefono.getColumnIndex("peticion"));
                                    }while(telefono.moveToNext());
                                }

                                Intent editar = new Intent(getApplicationContext(),EditarActivity.class);
                                editar.putExtra("id", idContacto);
                                editar.putExtra("nombre",nombres);
                                editar.putExtra("telefono",telefonos);
                                editar.putExtra("peticion",peticiones);
                                startActivity(editar);


                            break;
                            case 2:
                               dialogo2(idContacto);
                            break;
                        }
                    }
                });
        builder.show();
    }

    public void dialogo2(final String idContacto){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Eliminar contacto");
        dialog.setMessage("¿Estás seguro?");
        dialog.setCancelable(false);

        dialog.setPositiveButton("Si",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                positivo(idContacto);
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

    public void positivo(final String  idContacto){
        manager.eliminar(idContacto);
        Toast.makeText(getApplicationContext(),"Eliminado correctamente",Toast.LENGTH_SHORT).show();
        Cursor nuevo = manager.cargarCursorContactos();
        adapter.changeCursor(nuevo);
    }

    public void negativo(){
        Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_SHORT).show();
    }
}

