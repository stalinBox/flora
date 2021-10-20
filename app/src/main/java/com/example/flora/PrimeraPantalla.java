package com.example.flora;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PrimeraPantalla extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_pantalla);
    }

    public void irActivityLogin(View view){
        Intent inten = new Intent(this, Login.class);
        startActivity(inten);

    }

    public void irActivityVisitanteListar(View view){
        Intent inten = new Intent(this, VisitanteListar.class);
        startActivity(inten);

    }
}
