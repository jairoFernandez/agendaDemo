package com.tucompualdia.app.listas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Creado por Jairo Fernández para Tu compu al día 2/03/15.
 */
public class DataBaseManager {

    public static final String TABLE_NAME = "contactos";

    public static final String CN_ID = "_id";
    public static final String CN_NAME = "nombre";
    public static final String CN_PHONE = "telefono";
    public static final String CN_PETITION = "peticion";
    public static final String CN_FECHACREACION = "fechaCreacion";

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
            + CN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CN_NAME + " TEXT NOT NULL, "
            + CN_PHONE + " TEXT, "
            + CN_PETITION + " TEXT, "
            + CN_FECHACREACION + "TIMESTAMP NOT NULL DEFAULT current_timestamp);";

    private DbHelper helper;
    private SQLiteDatabase db;


    public DataBaseManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();



    }

    private ContentValues generarContentValues(String nombre, String telefono, String peticion){
        ContentValues valores = new ContentValues();
        valores.put(CN_NAME,nombre);
        valores.put(CN_PHONE,telefono);
        valores.put(CN_PETITION,peticion);

        return valores;
    }

    public void insertar(String nombre, String telefono, String peticion){
        db.insert(TABLE_NAME,CN_PHONE,generarContentValues(nombre,telefono,peticion));//devuelve long
    }

    public void eliminar(String id){
        db.delete(TABLE_NAME,CN_ID + "=?",new String[]{id});
    }

    public void eliminarMultiple(String nom1,String nom2){
        db.delete(TABLE_NAME,CN_NAME + "IN (?,?)",new String[]{nom1,nom2});
    }

    public void modificar(String id,String nombre,String nuevoTelefono,String peticion){
        db.update(TABLE_NAME,generarContentValues(nombre,nuevoTelefono,peticion),CN_ID + "=?",new String[]{id});
    }

    public Cursor cargarCursorContactos(){
        String[] columnas = new String[]{CN_ID,CN_NAME,CN_PHONE,CN_PETITION};

        return db.query(TABLE_NAME,columnas,null,null,null,null,CN_NAME+" ASC");

    }

    public Cursor buscarNombre(String nombre){
        String[] columnas = new String[]{CN_ID,CN_NAME,CN_PHONE,CN_PETITION};
        return db.query(TABLE_NAME,columnas,CN_NAME + "=?",new String[]{nombre},null,null,null);
//        return db.rawQuery("SELECT * FROM contactos WHERE nombre LIKE %"+nombre+"%",null);
    }

    public Cursor buscarId(String id){
        String[] columnas = new String[]{CN_ID,CN_NAME,CN_PHONE,CN_PETITION};
        return db.query(TABLE_NAME,columnas,CN_ID + "=?",new String[]{id},null,null,null);
    }

}
