package com.prueba.paeproyecto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

public class Usuario {

    String nombre;
    String direccion;
    String email;
    String telefono;
    String id;
    public Usuario(){

    }
    public Usuario(JSONObject objetoJ){
        try {
            this.nombre = objetoJ.getString("nombre");
            this.direccion = objetoJ.getString("direccion");
            this.email = objetoJ.getString("email");
            this.telefono = objetoJ.getString("telefono");
            this.id = objetoJ.getString("id");
        }catch (JSONException e){
            e.fillInStackTrace();
        }
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
