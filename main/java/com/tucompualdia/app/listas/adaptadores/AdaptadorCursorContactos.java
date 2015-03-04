package com.tucompualdia.app.listas.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.tucompualdia.app.listas.R;

/**
 * Creado por Jairo Fernández para Tu compu al día 3/03/15.
 */
public class AdaptadorCursorContactos extends CursorAdapter {

    private LayoutInflater inflador;
    TextView nombre,telefono;



    public AdaptadorCursorContactos(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vista = inflador.inflate(R.layout.titulo_listas,parent,false);

        return vista;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        nombre = (TextView)view.findViewById(R.id.titulo);
        telefono = (TextView)view.findViewById(R.id.subtitulo);

        String nombres = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
        String telefonos = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));
        nombre.setText(nombres);
        telefono.setText(telefonos);


    }
}
