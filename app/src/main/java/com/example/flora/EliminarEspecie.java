package com.example.flora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EliminarEspecie extends AppCompatActivity {

    private EditText edtNombreCientifico;
    private Button btnEliminar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_especie);

        edtNombreCientifico= findViewById(R.id.edtNombreCientifico);
        btnEliminar = findViewById(R.id.btnEliminarEspecie);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEspecie();
            }
        });
    }

    private void eliminarEspecie(){
        if (isValidarCampos()){
            String nombre_cientifico = edtNombreCientifico.getText().toString();
            Map<String,String> datos = new HashMap<>();
            datos.put("nombre_cientifico",nombre_cientifico);

            AndroidNetworking.post(Constantes.URL_ELIMINAR_ESPECIE).addJSONObjectBody(new JSONObject(datos)).setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String estado = response.getString("estado");
                        Toast.makeText(EliminarEspecie.this, estado, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(EliminarEspecie.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(ANError anError) {

                    Toast.makeText(EliminarEspecie.this, "Error"+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                }
            });

        }else{
            Toast.makeText(this, "No el Nombre Cientifico de Especie a Eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidarCampos(){
        return  !edtNombreCientifico.getText().toString().trim().isEmpty() ;

    }
}
