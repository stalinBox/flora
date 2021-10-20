package com.example.flora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ListarEspecies extends AppCompatActivity {

//    private ListView lvEspecies;
    //private ArrayAdapter <String>adapter;
    public static  final ArrayList<Flora> arrayFlora = new ArrayList<Flora>();
//    Flora flores;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_especies);
        final ListView list = findViewById(R.id.lvEspecies);
        final Context mContext = this;
        final ArrayList<Flora> arrayList = new ArrayList<Flora>();



        AndroidNetworking.initialize(getApplicationContext());

        AndroidNetworking.get(Constantes.URL_LISTAR_ESPECIES).setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    arrayFlora.clear();
                    String respuesta = response.getString("respuesta");
                    if (respuesta.equals("200")){
                        JSONArray arrayEspecies = response.getJSONArray("data");
                        for (int i=0 ; i<arrayEspecies.length(); i++){
                            JSONObject jsonEspecie =arrayEspecies.getJSONObject(i);


                            String nombre_cientifico = jsonEspecie.getString("nombre_cientifico");
                            String nombre_comun = jsonEspecie.getString("nombre_comun");
                            String ecosistema = jsonEspecie.getString("ecosistema");
                            String tipo_clima = jsonEspecie.getString("tipo_clima");
                            String uso = jsonEspecie.getString("uso");
                            String geolocalizacion= jsonEspecie.getString("geolocalizacion");
                            String imagen = jsonEspecie.getString("imagen");



                            arrayFlora.add(new Flora("Nombre Científico: " + nombre_cientifico +
                                    "\n Nombre común: "+nombre_comun +
                                    "\n Ecosistema: "+ecosistema+
                                    "\n Tipo de Clima: "+tipo_clima+
                                    "\n Uso: "+uso+
                                    "\n Ubicaciòn: "+geolocalizacion,
                                     imagen));


                            CustomAdapter customAdapter = new CustomAdapter(mContext, arrayFlora);
                            list.setAdapter(customAdapter);



                        }


                    }else{
                        Toast.makeText(ListarEspecies.this, "NO EXISTE NINGUNA ESPECIE REGISTRADA", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ListarEspecies.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(ListarEspecies.this, "ERROR: " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
            }

        });

    }

}
