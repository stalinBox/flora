package com.example.flora;

import android.content.Intent;
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

public class Login extends AppCompatActivity {

    private EditText edtUsuario;
    private EditText edtClave;
    private Button btnLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsuario= findViewById(R.id.edtUsuario);
        edtClave= findViewById(R.id.edtClave);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    private void Login(){

        if (isValidarCampos()){
            String usuario = edtUsuario.getText().toString();
            String clave = edtClave.getText().toString();

            Map<String,String> datos = new HashMap<>();
            datos.put("usuario",usuario);
            datos.put("clave", clave);


            AndroidNetworking.post(Constantes.URL_LOGIN).addJSONObjectBody(new JSONObject(datos)).setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String estado = response.getString("estado");
                        String cadena= "Validacion Correcta";
                      //  Toast.makeText(Login.this, ""+estado, Toast.LENGTH_SHORT).show();
                            //   Toast.makeText(Login.this, estado, Toast.LENGTH_SHORT).show();
                           if(estado.equals(cadena)){
                            Intent intent = new Intent(getApplicationContext(), MenuEspecies.class);
                            startActivity(intent);
                        }else{
                               Toast.makeText(Login.this, ""+estado, Toast.LENGTH_SHORT).show();
                           }
                    } catch (Exception e) {
                        Toast.makeText(Login.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(ANError anError) {

                    Toast.makeText(Login.this, "Error"+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                }
            });

        }else{
            Toast.makeText(this, "El Usuario o la Contraseña son Incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidarCampos(){
        return  !edtUsuario.getText().toString().trim().isEmpty() &&
                !edtClave.getText().toString().trim().isEmpty() ;

    }
}
