package com.example.flora;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActualizarClasificacion extends AppCompatActivity {

    private EditText edtCodigo;
    private EditText edtReino;
    private EditText edtDivision;
    private EditText edtClase;
    private EditText edtOrden;
    private EditText edtFamilia;
    private EditText edtGenero;
    private EditText edtEspecie;


    private Button btnActualizarC;
    private Button btnRegresarAC;

    private ArrayList<ClasificacionEntidad> listaClasificacion = new ArrayList<ClasificacionEntidad>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_clasificacion);

        btnActualizarC = findViewById(R.id.btnActualizarC);


        edtCodigo = findViewById(R.id.edtCodigo);

        edtReino = findViewById(R.id.edtReino);
        edtDivision = findViewById(R.id.edtDivision);
        edtClase = findViewById(R.id.edtClase);
        edtOrden = findViewById(R.id.edtOrden);
        edtFamilia = findViewById(R.id.edtFamilia);
        edtGenero = findViewById(R.id.edtGenero);
        edtEspecie = findViewById(R.id.edtEspecie);
        AndroidNetworking.initialize(getApplicationContext());

        edtCodigo.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                System.out.println("ESTOOOOo: "+s.toString());
                String id = "";
                Integer idSend;
                if(s.toString() == ""){
                    idSend = 0;
                }else{
                    try {
                        idSend = Integer.parseInt(s.toString());
                    }catch (Exception e){
                        idSend = 0;
                    }

                }
                ClasificacionEntidad onlyOne = findId(idSend,listaClasificacion);
                if(onlyOne == null) {
                    edtReino.setText(null);
                    edtDivision.setText(null);
                    edtClase.setText(null);
                    edtOrden.setText(null);
                    edtFamilia.setText(null);
                    edtGenero.setText(null);
                    edtEspecie.setText(null);
                    Toast.makeText(ActualizarClasificacion.this, "No existe registro con el codigo" + s.toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ActualizarClasificacion.this, "Registro encontrado:"+ onlyOne.getCodigo(), Toast.LENGTH_SHORT).show();
                    edtReino.setText(onlyOne.getReino());
                    edtDivision.setText(onlyOne.getDivision());
                    edtClase.setText(onlyOne.getClase());
                    edtOrden.setText(onlyOne.getOrden());
                    edtFamilia.setText(onlyOne.getFamilia());
                    edtGenero.setText(onlyOne.getGenero());
                    edtEspecie.setText(onlyOne.getEspecie());
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Toast.makeText(ActualizarClasificacion.this, "esto:"+ s, Toast.LENGTH_SHORT).show();
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(ActualizarClasificacion.this, "Esto:" + s, Toast.LENGTH_SHORT).show();
            }
        });

        btnActualizarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarClasificacionEspecies();
            }
        });

        AndroidNetworking.get(Constantes.URL_LISTAR_CLASIFICACION).setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {   //Acer refeerencia al metodo de consulta del web service y a la base con el Jason
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String respuesta = response.getString("respuesta");         // Obtener el estado de la respuesta del WEB Service para saber si consulta a la base o no

                    if (respuesta.equals("200")){
                        JSONArray arrayClasificacion = response.getJSONArray("data");

                        for(int i=0 ; i<arrayClasificacion.length();i++){
                            ClasificacionEntidad clas = new ClasificacionEntidad();


                            JSONObject jsonClasificacion = arrayClasificacion.getJSONObject(i);
                            Integer codigo= jsonClasificacion.getInt("codigo");
                            String nombre_cientifico= jsonClasificacion.getString("nombre_cientifico");
                            String nombre_comun = jsonClasificacion.getString("nombre_comun");
                            String reino= jsonClasificacion.getString("reino");
                            String division= jsonClasificacion.getString("division");
                            String clase= jsonClasificacion.getString("clase");
                            String orden= jsonClasificacion.getString("orden");
                            String familia= jsonClasificacion.getString("familia");
                            String genero= jsonClasificacion.getString("genero");
                            String especie= jsonClasificacion.getString("especie");

                            clas.setCodigo(codigo);
                            clas.setNombre_cientifico(nombre_cientifico);
                            clas.setNombre_comun(nombre_comun);
                            clas.setReino(reino);
                            clas.setDivision(division);
                            clas.setClase(clase);
                            clas.setOrden(orden);
                            clas.setFamilia(familia);
                            clas.setGenero(genero);
                            clas.setEspecie(especie);
                            listaClasificacion.add(clas);
                        }
                       /* for (ClasificacionEntidad i :listaClasificacion) {
                            Toast.makeText(ActualizarClasificacion.this, "salio algo"+ i.getCodigo(), Toast.LENGTH_SHORT).show();
                        }*/
                    }else {
                        Toast.makeText(ActualizarClasificacion.this, "No existe ninguna Clasificaiòn de Especies", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ActualizarClasificacion.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    public ClasificacionEntidad findId(Integer id, List<ClasificacionEntidad> customers) {
        for (ClasificacionEntidad clasific : customers) {
            if (clasific.getCodigo() == id) {
                System.out.println(clasific.getCodigo());
                return clasific;
            }
        }
        return null;
    }

    public void actualizarClasificacionEspecies(){
        if(isValidarCampos()){

            String codigo = edtCodigo.getText().toString();
            String reino = edtReino.getText().toString();
            String division= edtDivision.getText().toString();
            String clase = edtClase.getText().toString();
            String orden = edtOrden.getText().toString();
            String familia = edtFamilia.getText().toString();
            String genero = edtGenero.getText().toString();
            String especie = edtEspecie.getText().toString();

            // if (nombre_cientifico.isEmpty()){
            //      Toast.makeText(this, "DEBE INGRESAR UNA ESPECIE PRIMERO", Toast.LENGTH_SHORT).show();
            //  }else {
            Map<String,String> datos1 = new HashMap<>();

            datos1.put("codigo", codigo);
           // datos1.put("nombre_cientifico",nombre_cientifico);
           // datos1.put("nombre_comun",nombre_comun);
            datos1.put("reino", reino);
            datos1.put("division", division);
            datos1.put("clase", clase);
            datos1.put("orden", orden);
            datos1.put("familia" , familia);
            datos1.put("genero", genero);
            datos1.put("especie", especie);

            JSONObject jsonData1 = new JSONObject(datos1);

            AndroidNetworking.post(Constantes.URL_ACTUALIZAR_CLASIFICACION).setPriority(Priority.MEDIUM).addJSONObjectBody(jsonData1).build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String estado = response.getString("estado");
                        //  String error =  response.getString("error");
                        // Toast.makeText(Clasificacion.this, error, Toast.LENGTH_SHORT).show();
                        Toast.makeText(ActualizarClasificacion.this, estado, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(ActualizarClasificacion.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(ANError anError) {

                    Toast.makeText(ActualizarClasificacion.this, "Error:"+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            Toast.makeText(this, "Existen campos vacios y no se puede actualizar la informacion", Toast.LENGTH_SHORT).show();
        }



    }
    private boolean isValidarCampos(){
        return
                !edtCodigo.getText().toString().trim().isEmpty()&&
                !edtReino.getText().toString().trim().isEmpty()&&
                !edtDivision.getText().toString().trim().isEmpty()&&
                !edtClase.getText().toString().trim().isEmpty()&&
                !edtOrden.getText().toString().trim().isEmpty()&&
                !edtFamilia.getText().toString().trim().isEmpty()&&
                !edtGenero.getText().toString().trim().isEmpty()&&
                !edtEspecie.getText().toString().trim().isEmpty();
    }
}

