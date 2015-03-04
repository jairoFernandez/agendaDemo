package com.tucompualdia.app.listas;

/**
 * Creado por Jairo Fernández para Tu compu al día 2/03/15.
 */
public class TipoDatos {
    private String titulo;
    private String subTitulo;

    public TipoDatos(String titulo,String subTitulo){
        this.titulo = titulo;
        this.subTitulo = subTitulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }
}
