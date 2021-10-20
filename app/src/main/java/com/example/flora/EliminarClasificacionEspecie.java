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

public class EliminarClasificacionEspecie extends AppCompatActivity {

    private EditText edtCodigo;
    private Button btnEliminar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_clasificacion_especie);

        edtCodigo= findViewById(R.id.edtCodigo);
        btnEliminar = findViewById(R.id.btnEliminarClasificacion);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarClasificacionEspecie();
            }
        });
    }

    private void eliminarClasificacionEspecie(){
        if (isValidarCampos()){
            String codigo = edtCodigo.getText().toString();
            Map<String,String> datos = new HashMap<>();
            datos.put("codigo",codigo);

            AndroidNetworking.post(Constantes.URL_ELIMINAR_CLASIFICACION_ESPECIE).addJSONObjectBody(new JSONObject(datos)).setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String estado = response.getString("estado");
                        Toast.makeText(EliminarClasificacionEspecie.this, estado, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(EliminarClasificacionEspecie.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(ANError anError) {

                    Toast.makeText(EliminarClasificacionEspecie.this, "Error"+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                }
            });

        }else{
            Toast.makeText(this, "No a escrito el codigo de la Clasificaciòn a Eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidarCampos(){
        return  !edtCodigo.getText().toString().trim().isEmpty() ;

    }
}
