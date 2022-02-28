package com.prueba.paeproyecto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

public class Usuario2 {
    String codigo;
    String imagen;
    String nombre;

    public Usuario2(){

    }
    public Usuario2(JSONObject objeto){
        try {
            this.codigo = objeto.getString("codigo");
            this.imagen = objeto.getString("imagen");
            this.nombre = objeto.getString("nombre");
        }catch (JSONException e){
            e.fillInStackTrace();
        }
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {this.imagen = imagen;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
