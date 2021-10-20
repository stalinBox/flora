package com.example.flora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuEspecies extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


    }
    public void irActivityRegistrarEspecie(View view){
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
    }

    public void irActivityListarEspecies (View view){
        Intent inten = new Intent(this, ListarEspecies.class);
        startActivity(inten);

    }
    public void irActivityListarClasificacion (View view){
        Intent inten = new Intent(this, ListarClasificacion.class);
        startActivity(inten);


    }

    public void irActivityActualizarEspecie(View view){
        Intent inten = new Intent(this, ActualizarEspecie.class);
        startActivity(inten);


    }
    public void irActivityActualizarClasificacion (View view){
        Intent inten = new Intent(this, ActualizarClasificacion.class);
        startActivity(inten);


    }
    public void irActivityEliminarEspecie (View view){
        Intent inten = new Intent(this, EliminarEspecie.class);
        startActivity(inten);

    }

    public void irActivityEliminarClasificacionEspecie (View view){
        Intent inten = new Intent(this, EliminarClasificacionEspecie.class);
        startActivity(inten);
    }

}

