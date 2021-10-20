package com.example.flora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VisitanteListar extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitante_listar);
    }
    public void irActivityListarEspecies (View view){
        Intent inten = new Intent(this, ListarEspecies.class);
        startActivity(inten);

    }
    public void irActivityListarClasificacion (View view){
        Intent inten = new Intent(this, ListarClasificacion.class);
        startActivity(inten);


    }
}
