package com.tucompualdia.app.listas.adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tucompualdia.app.listas.R;
import com.tucompualdia.app.listas.TipoDatos;

/**
 * Creado por Jairo Fernández para Tu compu al día 2/03/15.
 */
public class AdaptadorListas extends ArrayAdapter {
    Activity context;
    TipoDatos [] datos;

    public AdaptadorListas(Activity context,TipoDatos[] datos){
        super(context, R.layout.titulo_listas, datos);
        this.datos = datos;
        this.context = context;
    }

    @Override
    public View getView(int position,View view,ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater(); // genera del xml la vista necesaria
        View item = inflater.inflate(R.layout.titulo_listas,null); // inflamos el xml que hemos generado

        TextView titulo = (TextView)item.findViewById(R.id.titulo);
        titulo.setText(datos[position].getTitulo());

        TextView subitulo = (TextView)item.findViewById(R.id.subtitulo);
        subitulo.setText(datos[position].getSubTitulo());

        return item;
    }
}
