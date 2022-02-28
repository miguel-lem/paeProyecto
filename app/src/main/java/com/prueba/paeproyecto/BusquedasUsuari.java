package com.prueba.paeproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.BreakIterator;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class BusquedasUsuari extends AppCompatActivity {

    EditText eNombre2, eDireccion2, eCorreo2, eTelefono2, eId2;
    //TextView tTver2;
    RequestQueue reques;
    ArrayList<Usuario> listausuarios;
    int contador=0,contador2=0;
    ImageView foto2;
    ArrayList<Usuario2> listausuarios2;
    Bitmap bitma64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busquedas_usuari);
        eNombre2 = (EditText)findViewById(R.id.eNombre2);
        eDireccion2 = (EditText)findViewById(R.id.eDireccion2);
        eCorreo2 = (EditText)findViewById(R.id.eCorreo2);
        eTelefono2 = (EditText)findViewById(R.id.eTelefono2);
        eId2 = (EditText)findViewById(R.id.eId2);
        foto2=(ImageView)findViewById(R.id.imView2);
        //tTver2=findViewById(R.id.tTver2);
        reques= Volley.newRequestQueue(this);
        //tTver2.setMovementMethod(new ScrollingMovementMethod());


    }

    public  void extraer(View view){
        listausuarios = new ArrayList<Usuario>();
        //tTver2.setText("");
        if(eNombre2.getText().toString().isEmpty()){
            Toast.makeText(this,"El campo Nombre (necesario para la busqueda) esta vacio: ",Toast.LENGTH_LONG).show();
        }else{
            contador2=1;
            String url = "http://localhost/leerPaeSeleccionado.php?nombre="+eNombre2.getText().toString();
            url=url.replace(" ","%20");
            JsonArrayRequest pedido= new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //contador2=response.length();
                            for(int i=0;i< response.length();i++){
                                try {
                                    listausuarios.add(new Usuario(response.getJSONObject(i)));
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                            String datos ="";
                            for(int i=0;i<listausuarios.size();i++){
                                datos=datos+" nombre: "+listausuarios.get(i).nombre+
                                        " direccion: "+ listausuarios.get(i).direccion+" email: "+listausuarios.get(i).email+
                                        " telefono: "+ listausuarios.get(i).telefono+
                                        " id: "+ listausuarios.get(i).id+"\n";
                            }
                            //tTver2.setText(datos);
                            if(listausuarios.size()>0){
                                eNombre2.setText(listausuarios.get(0).nombre);
                                eDireccion2.setText(listausuarios.get(0).direccion);
                                eCorreo2.setText(listausuarios.get(0).email);
                                eTelefono2.setText(listausuarios.get(0).telefono);
                                eId2.setText(listausuarios.get(0).id);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
            reques.add(pedido);
            extraerImagenes();
        }

    }

    public void siguiente(View view){
        if(contador2==0){
            Toast.makeText(this,"No se a hecho aun una busqueda: ",Toast.LENGTH_LONG).show();
        }else{
            contador++;
            if(contador<listausuarios.size()){
                eNombre2.setText(listausuarios.get(contador).nombre);
                eDireccion2.setText(listausuarios.get(contador).direccion);
                eCorreo2.setText(listausuarios.get(contador).email);
                eTelefono2.setText(listausuarios.get(contador).telefono);
                eId2.setText(listausuarios.get(contador).id);
                foto2.setImageBitmap(convertiraImagen(listausuarios2.get(contador).imagen));
            }
            else {
                Toast.makeText(this,"Ya no existe mas conactos: ",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void anterior(View view){
        if(contador2==0){
            Toast.makeText(this,"No se a hecho aun una busqueda: ",Toast.LENGTH_LONG).show();
        }else{
            contador--;
            if(contador>=0){
                eNombre2.setText(listausuarios.get(contador).nombre);
                eDireccion2.setText(listausuarios.get(contador).direccion);
                eCorreo2.setText(listausuarios.get(contador).email);
                eTelefono2.setText(listausuarios.get(contador).telefono);
                eId2.setText(listausuarios.get(contador).id);
                foto2.setImageBitmap(convertiraImagen(listausuarios2.get(contador).imagen));
            }
            else{
                Toast.makeText(this,"Ya no existe mas contactos: ",Toast.LENGTH_LONG).show();
            }
        }

    }

    public void retornar(View view){
        Intent intento = new Intent(this,MainActivity.class);
        startActivity(intento);
    }

    public  void extraerImagenes(){
        listausuarios2 = new ArrayList<Usuario2>();
        String url = "http://localhost/leerPaeSeleccionadoImagen.php?nombre="+eNombre2.getText().toString();
        JsonArrayRequest pedido= new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0;i< response.length();i++){
                            try {
                                listausuarios2.add(new Usuario2(response.getJSONObject(i)));
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        String datos ="";
                        for(int i=0;i<listausuarios2.size();i++){
                            datos=datos+" codigo: "+listausuarios2.get(i).codigo+
                                    " imagen: "+ listausuarios2.get(i).imagen+
                                    " nombre: "+listausuarios2.get(i).nombre+"\n";
                        }
                        //texV.setText(datos);
                        if(listausuarios2.size()>0){
                            
                            foto2.setImageBitmap(convertiraImagen(listausuarios2.get(0).imagen));
                            

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        //Toast.makeText(MainActivity.this,"Info"+eId.getText().toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Se extrajo con exito: ",Toast.LENGTH_LONG).show();
        reques.add(pedido);
    }

    private Bitmap convertiraImagen(String bitma){
        byte[] decodedString = Base64.decode(bitma,Base64.DEFAULT);
        bitma64 = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
        return bitma64;
    }
}