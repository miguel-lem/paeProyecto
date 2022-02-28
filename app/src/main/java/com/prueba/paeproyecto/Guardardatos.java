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
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Guardardatos extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    EditText eNombre1, eDireccion1, eCorreo1, eTelefono1,eId1;
    RequestQueue requestQueue;
    //TextView tTver1;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<Usuario> listausuarios;
    ImageView foto1;
    private static final int REQUEST_PERMISSION_CAMERA =101;
    private static final int REQUEST_IMAGE_CAMERA =101;
    Bitmap bitmap64;
    StringRequest stringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardardatos);
        eNombre1 = (EditText)findViewById(R.id.eNombre1);
        eDireccion1 = (EditText)findViewById(R.id.eDireccion1);
        eCorreo1 = (EditText)findViewById(R.id.eCorreo1);
        eTelefono1 = (EditText)findViewById(R.id.eTelefono1);
        eId1 = (EditText)findViewById(R.id.eId1);
        foto1=(ImageView)findViewById(R.id.imView1);
        //tTver1=(TextView)findViewById(R.id.tTver1);
        requestQueue = Volley.newRequestQueue(this.getBaseContext());
    }

    public void registroContacto(View view){
        if(eNombre1.getText().toString().isEmpty() || eDireccion1.getText().toString().isEmpty() || eCorreo1.getText().toString().isEmpty() ||eTelefono1.getText().toString().isEmpty() ||eId1.getText().toString().isEmpty()||foto1.getDrawable()==null){
            Toast.makeText(this,"Todos los campos deben estar llenos para poder continuar: ",Toast.LENGTH_LONG).show();
        }else{
            String url = "http://localhost/insertarPae.php?nombre="+eNombre1.getText().toString()+"&direccion="+eDireccion1.getText().toString()+"&email="+eCorreo1.getText().toString()+"&telefono="+eTelefono1.getText().toString()+"&id="+eId1.getText().toString();
            url=url.replace(" ","%20");
            //tTver1.setText(url);
            jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(), this, this);
            requestQueue.add(jsonObjectRequest);
            enviarImagen();
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
            eNombre1.setText("");
            eDireccion1.setText("");
            eCorreo1.setText("");
            eTelefono1.setText("");
            eId1.setText("");
        }
    }
    public void retornar(View view){
        Intent intento = new Intent(this,MainActivity.class);
        startActivity(intento);
    }


    public void tomarFoto(View view){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(Guardardatos.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                irAcamara();
            }else{
                ActivityCompat.requestPermissions(Guardardatos.this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
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
                foto1.setImageBitmap(bitmap64);
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
    public void enviarImagen(){
        String url="http://localhost/guardarfotoPae.php";
        url=url.replace(" ","%20");
        stringRequest = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        foto1.setImageBitmap(null);
                        Toast.makeText(Guardardatos.this,"Se guardo la informacion",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Guardardatos.this,"No se guardo la informacion",Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id = eId1.getText().toString();
                String imagen = getStringImage(bitmap64);
                String nombre = eNombre1.getText().toString();
                Map<String,String> params = new HashMap<>();
                params.put("codigo",id);
                params.put("imagen",imagen);
                params.put("nombre",nombre);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private String getStringImage(Bitmap bitma){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitma.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);
    }

}