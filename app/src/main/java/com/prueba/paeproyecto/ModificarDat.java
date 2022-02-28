package com.prueba.paeproyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModificarDat extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    EditText eNombre3, eDireccion3, eCorreo3, eTelefono3,eId3;
    RequestQueue reques;
    ArrayList<Usuario> listausuarios;
    String codigo="";
    int contador=0, contador2=0;
    JsonObjectRequest jsonObjectRequest;
    ImageView foto3;
    ArrayList<Usuario2> listausuarios2;
    Bitmap bitma64,bitmap64;
    StringRequest stringRequest;
    private static final int REQUEST_PERMISSION_CAMERA =101;
    private static final int REQUEST_IMAGE_CAMERA =101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_dat);
        eNombre3 = (EditText)findViewById(R.id.eNombre3);
        eDireccion3 = (EditText)findViewById(R.id.eDireccion3);
        eCorreo3 = (EditText)findViewById(R.id.eCorreo3);
        eTelefono3 = (EditText)findViewById(R.id.eTelefono3);
        eId3 = (EditText)findViewById(R.id.eId3);
        foto3=(ImageView)findViewById(R.id.imView3);
        reques= Volley.newRequestQueue(this);
    }

    public  void extraer(View view){
        listausuarios = new ArrayList<Usuario>();
        if(eNombre3.getText().toString().isEmpty()){
            Toast.makeText(this,"El campo Nombre (necesario para la busqueda) esta vacio: ",Toast.LENGTH_LONG).show();
        }else{
            contador2=1;
            Toast.makeText(this,"Si existen mas datos con el nombre recorra: anterior o siguiente ",Toast.LENGTH_LONG).show();
            String url = "http://localhost/leerPaeSeleccionado.php?nombre="+eNombre3.getText().toString();
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
                            if(listausuarios.size()>0){
                                eNombre3.setText(listausuarios.get(0).nombre);
                                eDireccion3.setText(listausuarios.get(0).direccion);
                                eCorreo3.setText(listausuarios.get(0).email);
                                eTelefono3.setText(listausuarios.get(0).telefono);
                                eId3.setText(listausuarios.get(0).id);
                                codigo=eId3.getText().toString();
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
            Toast.makeText(this,"No se a hecho aun una extraccion: ",Toast.LENGTH_LONG).show();
        }else{
            contador++;
            if(contador<listausuarios.size()){
                eNombre3.setText(listausuarios.get(contador).nombre);
                eDireccion3.setText(listausuarios.get(contador).direccion);
                eCorreo3.setText(listausuarios.get(contador).email);
                eTelefono3.setText(listausuarios.get(contador).telefono);
                eId3.setText(listausuarios.get(contador).id);
                foto3.setImageBitmap(convertiraImagen(listausuarios2.get(contador).imagen));
                codigo=eId3.getText().toString();
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
                eNombre3.setText(listausuarios.get(contador).nombre);
                eDireccion3.setText(listausuarios.get(contador).direccion);
                eCorreo3.setText(listausuarios.get(contador).email);
                eTelefono3.setText(listausuarios.get(contador).telefono);
                eId3.setText(listausuarios.get(contador).id);
                foto3.setImageBitmap(convertiraImagen(listausuarios2.get(contador).imagen));
                codigo=eId3.getText().toString();
            }
            else{
                Toast.makeText(this,"Ya no existe mas contactos: ",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void registrarEditado(View view){
        if(contador2==0){
            Toast.makeText(this,"Aun no a realizado la busqueda: ",Toast.LENGTH_LONG).show();
        }else{
            if(eNombre3.getText().toString().isEmpty() || eDireccion3.getText().toString().isEmpty() || eCorreo3.getText().toString().isEmpty() ||eTelefono3.getText().toString().isEmpty()||eId3.getText().toString().isEmpty()||foto3.getDrawable()==null){
                Toast.makeText(this,"Todos los campos deben estar llenos para poder continuar: ",Toast.LENGTH_LONG).show();
            }else{
                String url = "http://localhost/editarPaeSeleccionado.php?nombre="+eNombre3.getText().toString()+"&direccion="+eDireccion3.getText().toString()+"&email="+eCorreo3.getText().toString()+"&telefono="+eTelefono3.getText().toString()+"&id="+eId3.getText().toString()+"&codigo="+codigo;
                url=url.replace(" ","%20");
                jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(), this, this);
                reques.add(jsonObjectRequest);
                enviarImagen();
            }
        }
    }
    @Override
    public void onErrorResponse(VolleyError error){
        Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();
        error.printStackTrace();
    }
    @Override
    public void onResponse(JSONObject response){
        Toast.makeText(this,jsonObjectRequest.toString(),Toast.LENGTH_LONG).show();
        listausuarios = new ArrayList<Usuario>();
        try {
            JSONArray json_array = response.optJSONArray("usuario");
            for(int i=0;i<json_array.length();i++){
                listausuarios.add(new Usuario(json_array.getJSONObject(i)));
            }
            Toast.makeText(this,"Se a grabado la informacion: ",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"No se a grabado la informacion: ",Toast.LENGTH_LONG).show();
        }
        if(listausuarios.get(0).nombre==null) {
            Toast.makeText(this,"No se logro grabar",Toast.LENGTH_LONG).show();
        }else{
            eNombre3.setText("");
            eDireccion3.setText("");
            eCorreo3.setText("");
            eTelefono3.setText("");
            eId3.setText("");
        }
    }

    public void retornar(View view){
        Intent intento = new Intent(this,MainActivity.class);
        startActivity(intento);
    }
    public  void extraerImagenes(){
        //contador2=1;
        listausuarios2 = new ArrayList<Usuario2>();
        String url = "http://localhost/leerPaeSeleccionadoImagen.php?nombre="+eNombre3.getText().toString();
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
                            foto3.setImageBitmap(convertiraImagen(listausuarios2.get(0).imagen));
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

    public void enviarImagen(){
        String url="http://localhost/editarPaeSeleccionadoFoto.php";
        url=url.replace(" ","%20");
        stringRequest = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        
                        foto3.setImageBitmap(null);
                        Toast.makeText(ModificarDat.this,"Se guardo la informacion",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModificarDat.this,"No se guardo la informacion",Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id = eId3.getText().toString();
                String imagen = getStringImage(bitmap64);
                String nombre = eNombre3.getText().toString();
                String codigo2 = codigo;
                Map<String,String> params = new HashMap<>();
                params.put("codigo",id);
                params.put("imagen",imagen);
                params.put("nombre",nombre);
                params.put("codigo2",codigo2);
                return params;
            }
        };
        reques.add(stringRequest);
    }

    private String getStringImage(Bitmap bitma){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitma.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);
    }

    public void tomarFoto(View view){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(ModificarDat.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                irAcamara();
            }else{
                ActivityCompat.requestPermissions(ModificarDat.this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
            }
        }else{
            irAcamara();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_PERMISSION_CAMERA){
            if(permissions.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                irAcamara();
            }else{
                Toast.makeText(this,"Necesitas habilitar permisos",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_IMAGE_CAMERA){
            if(resultCode== Activity.RESULT_OK){
                bitmap64 = (Bitmap) data.getExtras().get("data");
                foto3.setImageBitmap(bitmap64);
                Log.i("TAG","Result->"+bitmap64);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void irAcamara(){
        Intent cameraint = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraint.resolveActivity(getPackageManager())!=null){
            startActivityForResult(cameraint,REQUEST_IMAGE_CAMERA);
        }
    }
}