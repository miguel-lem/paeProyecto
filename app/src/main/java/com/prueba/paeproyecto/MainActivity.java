package com.prueba.paeproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText eNombre, eDireccion, eCorreo, eTelefono, eId;
    ImageView foto;
    RequestQueue reques;
    //RequestQueue requestQueue;
    ArrayList<Usuario> listausuarios;
    int contador=0,contador2=0;

    //JsonObjectRequest jsonObjectRequest;
    ArrayList<Usuario2> listausuarios2;
    Bitmap bitma64;
    String imagenes="";
    //TextView texV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eNombre = (EditText)findViewById(R.id.eNombre);
        eDireccion = (EditText)findViewById(R.id.eDireccion);
        eCorreo = (EditText)findViewById(R.id.eCorreo);
        eTelefono = (EditText)findViewById(R.id.eTelefono);
        eId = (EditText)findViewById(R.id.eId);
        foto=(ImageView)findViewById(R.id.imView);
        //texV=(TextView)findViewById(R.id.textView);
        reques= Volley.newRequestQueue(this);
    }

    public  void extraer(View view){
        contador2=1;
        listausuarios = new ArrayList<Usuario>();
        String url = "http://localhost/leerPae.php";
        JsonArrayRequest pedido= new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
                        if(listausuarios.size()>0){
                            eNombre.setText(listausuarios.get(0).nombre);
                            eDireccion.setText(listausuarios.get(0).direccion);
                            eCorreo.setText(listausuarios.get(0).email);
                            eTelefono.setText(listausuarios.get(0).telefono);
                            eId.setText(listausuarios.get(0).id);
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

    public void siguiente(View view){
        if(contador2==0){
            Toast.makeText(this,"No se a hecho aun una extraccion: ",Toast.LENGTH_LONG).show();
        }else{
            contador++;
            if(contador<listausuarios.size()){
                eNombre.setText(listausuarios.get(contador).nombre);
                eDireccion.setText(listausuarios.get(contador).direccion);
                eCorreo.setText(listausuarios.get(contador).email);
                eTelefono.setText(listausuarios.get(contador).telefono);
                eId.setText(listausuarios.get(contador).id);
                foto.setImageBitmap(convertiraImagen(listausuarios2.get(contador).imagen));
            }
            else {
                Toast.makeText(this,"Ya no existe mas conactos: ",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void anterior(View view){
        if(contador2==0){
            Toast.makeText(this,"No se a hecho aun una extraccion: ",Toast.LENGTH_LONG).show();
        }else{
            contador--;
            if(contador>=0){
                eNombre.setText(listausuarios.get(contador).nombre);
                eDireccion.setText(listausuarios.get(contador).direccion);
                eCorreo.setText(listausuarios.get(contador).email);
                eTelefono.setText(listausuarios.get(contador).telefono);
                eId.setText(listausuarios.get(contador).id);
                foto.setImageBitmap(convertiraImagen(listausuarios2.get(contador).imagen));
            }
            else{
                Toast.makeText(this,"Ya no existe mas contactos: ",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void irAguardar(View view){
        Intent intento = new Intent(this,Guardardatos.class);
        startActivity(intento);
    }
    public void irAbuscar(View view){
        Intent intento = new Intent(this,BusquedasUsuari.class);
        startActivity(intento);
    }
    public void irAmodificar(View view){
        Intent intento = new Intent(this,ModificarDat.class);
        startActivity(intento);
    }
    ///modificacion hechas para poder extraer fotos desde el servidor
    public  void extraerImagenes(){
        //contador2=1;
        listausuarios2 = new ArrayList<Usuario2>();
        String url = "http://localhost/extraerPaeImagentodo.php";
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
                            
                            foto.setImageBitmap(convertiraImagen(listausuarios2.get(0).imagen));
                            

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