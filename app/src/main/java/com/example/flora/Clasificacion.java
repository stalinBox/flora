package com.example.flora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Clasificacion extends AppCompatActivity {

 private  EditText edtCodigo;

    private EditText edtReino;
    private EditText edtDivision;
    private EditText edtClase;
    private EditText edtOrden;
    private EditText edtFamilia;
    private EditText edtGenero;
    private EditText edtEspecie;
    private TextView txtNombreCientifico;
    private TextView txtNombreComun;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);

        edtCodigo = findViewById(R.id.edtCodigo);
        txtNombreCientifico = findViewById(R.id.txtNombreCientifico);
        txtNombreComun = findViewById(R.id.txtNombreComun);
        edtReino = findViewById(R.id.edtReino);
        edtDivision = findViewById(R.id.edtDivision);
        edtClase = findViewById(R.id.edtClase);
        edtOrden = findViewById(R.id.edtOrden);
        edtFamilia = findViewById(R.id.edtFamilia);
        edtGenero = findViewById(R.id.edtGenero);
        edtEspecie = findViewById(R.id.edtEspecie);
        AndroidNetworking.initialize(getApplicationContext());

        String nombreCientifico = getIntent().getStringExtra("dato");
        txtNombreCientifico.setText(nombreCientifico);

        String nombreComun= getIntent().getStringExtra("dato1");
        txtNombreComun.setText(nombreComun);
    }


    // METODO PARA REGRESAR
    public void Anterior (View v){
        Intent anterior = new Intent(this, MainActivity.class);
        startActivity(anterior);
    }


    public void registrarClasificacion(View view){
        if (isValidarCampos()){
            String codigo = edtCodigo.getText().toString();
            String nombre_cientifico = txtNombreCientifico.getText().toString();
            String nombre_comun = txtNombreComun.getText().toString();
            String reino = edtReino.getText().toString();
            String division= edtDivision.getText().toString();
            String clase = edtClase.getText().toString();
            String orden = edtOrden.getText().toString();
            String familia = edtFamilia.getText().toString();
            String genero = edtGenero.getText().toString();
            String especie = edtEspecie.getText().toString();

           if (nombre_cientifico.isEmpty()){
               Toast.makeText(this, "DEBE INGRESAR UNA ESPECIE PRIMERO", Toast.LENGTH_SHORT).show();
           }else {
            Map<String,String> datos1 = new HashMap<>();

           datos1.put("codigo", codigo);
           datos1.put("nombre_cientifico",nombre_cientifico);
           datos1.put("nombre_comun",nombre_comun);
           datos1.put("reino", reino);
           datos1.put("division", division);
           datos1.put("clase", clase);
           datos1.put("orden", orden);
           datos1.put("familia" , familia);
           datos1.put("genero", genero);
           datos1.put("especie", especie);

            JSONObject jsonData1 = new JSONObject(datos1);

            AndroidNetworking.post(Constantes.URL_INSERTAR_CLASIFICACION_TAXONOMICA).setPriority(Priority.MEDIUM).addJSONObjectBody(jsonData1)
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String estado = response.getString("estado");
                      //  String error =  response.getString("error");
                      // Toast.makeText(Clasificacion.this, error, Toast.LENGTH_SHORT).show();
                       Toast.makeText(Clasificacion.this, estado, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(Clasificacion.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(ANError anError) {
                    Toast.makeText(Clasificacion.this, "Error"+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                }
            });
           }

        }else{
            Toast.makeText(this, "No se puede insertar si existen campos vacios", Toast.LENGTH_SHORT).show();
        }
    }



    // Devuelve verdaero si es que hay campos vacios
    //Devuelve falso si esq hay como minimo campos vacios
    // Elimina los espacios del cominzo o final de la caja de texto
    private boolean isValidarCampos(){
        return  !edtReino.getText().toString().trim().isEmpty() &&
                !edtDivision.getText().toString().trim().isEmpty() &&
                !edtClase.getText().toString().trim().isEmpty() &&
                !edtOrden.getText().toString().trim().isEmpty()&&
                !edtFamilia.getText().toString().trim().isEmpty()&&
                !edtGenero.getText().toString().trim().isEmpty()&&
                !edtEspecie.getText().toString().trim().isEmpty();
    }
}
