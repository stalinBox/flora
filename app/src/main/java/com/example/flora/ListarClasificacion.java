package com.example.flora;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListarClasificacion extends AppCompatActivity {

private ListView lvClasificacion;
private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_clasificacion);


        lvClasificacion= findViewById(R.id.lvlClasificacion);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);// Diseño creado por el android studio de una interfaz de listview

        lvClasificacion.setAdapter(adapter);

        AndroidNetworking.get(Constantes.URL_LISTAR_CLASIFICACION).setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {   //Acer refeerencia al metodo de consulta del web service y a la base con el Jason
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String respuesta = response.getString("respuesta");         // Obtener el estado de la respuesta del WEB Service para saber si consulta a la base o no

                    if (respuesta.equals("200")){
                        JSONArray arrayClasificacion = response.getJSONArray("data");     // Los datos estan dentro de llavaes lo que dice que se encuentran varois objetos en un arrary .... Json Array en la variable data estan los datos de la base
                    for(int i=0 ; i<arrayClasificacion.length();i++){
                        JSONObject jsonClasificacion = arrayClasificacion.getJSONObject(i);

                        String codigo= jsonClasificacion.getString("codigo");
                        String nombre_cientifico= jsonClasificacion.getString("nombre_cientifico");
                        String nombre_comun = jsonClasificacion.getString("nombre_comun");
                        String reino= jsonClasificacion.getString("reino");
                        String division= jsonClasificacion.getString("division");
                        String clase= jsonClasificacion.getString("clase");
                        String orden= jsonClasificacion.getString("orden");
                        String familia= jsonClasificacion.getString("familia");
                        String genero= jsonClasificacion.getString("genero");
                        String especie= jsonClasificacion.getString("especie");


                        String dataString =  "Còdigo: "+codigo +"\n"+
                               "Nombre Cientìfico: "+nombre_cientifico +"\n"+
                                "Nombre Comùn: "+nombre_comun +"\n"+
                                "Reino: "+reino+ "\n"+
                                "Divisiòn: "+division +"\n"+
                                "Clase: "+clase +"\n"+
                                "Orden: "+orden +"\n"+
                                "Familia: "+familia + "\n"+
                                "Gènero: "+genero +"\n"+
                                "Especie: "+especie;
                        adapter.add(dataString);
                    }
                          adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(ListarClasificacion.this, "No existe ninguna Clasificaiòn de Especies", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ListarClasificacion.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(ANError anError) {

                Toast.makeText(ListarClasificacion.this, "Error"+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

            }
        });
        
    }
}
