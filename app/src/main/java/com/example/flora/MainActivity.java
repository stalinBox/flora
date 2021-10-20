package com.example.flora;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText edtNombreCientifico;
    private EditText edtNombreComun;
    private EditText edtEcosistema;
    private EditText edtClima;
    private EditText edtUso;
    private Button btnGuardar;

    // CAMARA

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    Bitmap bitmap;
    String path;
    Button btnGallery;
    ImageView profile;

    //

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGENES= CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    private String pat;
    File fileImagen;

    private static final int REQUEST_PERMISSION_CODE =100;
    private static final int REQUEST_IMAGE_GALLERY =101;

    private static final int REQUEST_PERMISSION_CAMERA =100;
    private static final int REQUEST_IMAGE_CAMERA =101;


    // GPS

    TextView mensaje1;
    TextView mensaje2;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());

        edtNombreCientifico = findViewById(R.id.edtNombreCientifico);
        edtNombreComun = findViewById(R.id.edtNombreComun);
        edtEcosistema = findViewById(R.id.edtEcosistema);
        edtClima= findViewById(R.id.edtClima);
        edtUso = findViewById(R.id.edtUso);

        // CAMARA
        profile = findViewById(R.id.profile);
        btnGallery= findViewById(R.id.btnGallery);

        // GPS
        mensaje1 = findViewById(R.id.mensaje_id);
        mensaje2 = findViewById(R.id.mensaje_id2);
        btnGuardar= findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarFlora();
            }
        });
    }

    public String BitMapToString(Bitmap bitmap){
       String temp="";
        try {
            ByteArrayOutputStream baos=new  ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] b=baos.toByteArray();
             temp=Base64.encodeToString(b, Base64.NO_WRAP);
            return temp;

        }catch (Exception e){

        }
          return temp;

    }
    private void registrarFlora(){
        if (isValidarCampos()){

            String nombre_cientifico = edtNombreCientifico.getText().toString();
            String nombre_comun = edtNombreComun.getText().toString();
            String ecosistema= edtEcosistema.getText().toString();
            String tipo_clima = edtClima.getText().toString();
            String uso = edtUso.getText().toString();
            String geolocalizacion = mensaje1.getText().toString();
            String imagen = BitMapToString(bitmap);

            Map<String,String> datos = new HashMap<>();
            datos.put("nombre_cientifico",nombre_cientifico);
            datos.put("nombre_comun", nombre_comun);
            datos.put("ecosistema", ecosistema);
            datos.put("tipo_clima", tipo_clima);
            datos.put("uso", uso);
            datos.put("geolocalizacion" , geolocalizacion);
            datos.put("imagen", imagen);



            JSONObject jsonData = new JSONObject(datos);

            AndroidNetworking.post(Constantes.URL_INSERTAR_FLORA).setPriority(Priority.MEDIUM).addJSONObjectBody(jsonData)
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String estado = response.getString("estado");
                        Toast.makeText(MainActivity.this, estado, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onError(ANError anError) {
                    Toast.makeText(MainActivity.this, "Error"+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                }
            });



        }else{
            Toast.makeText(this, "No se puede insertar si existen campos vacios", Toast.LENGTH_SHORT).show();
        }
    }




    // Devuelve verdaero si es que hay campos vacios
    //Devuelve falso si esq hay como minimo campos vacios
    // Elimina los espacios del cominzo o final de la caja de texto
    private boolean isValidarCampos(){
        return  !edtNombreCientifico.getText().toString().trim().isEmpty() &&
                !edtNombreComun.getText().toString().trim().isEmpty() &&
                !edtEcosistema.getText().toString().trim().isEmpty() &&
                !edtClima.getText().toString().trim().isEmpty()&&
                !edtUso.getText().toString().trim().isEmpty();




    }



    // METODO PARA CARGAR LAS IMAGENES Y TOMAR FOTOS
    public void Imagen(View v) {


          cargarImagen();
    }

    private void cargarImagen() {
        tomarFoto();

    }


    // RESULTADO DE QUE LA IMAGEN O FOTO SE CARGUE EN EL IMAGVIEW


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {    // Obtener la foto q presionamos
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {

            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri miPath = data.getData();
                    profile.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento", "Path: "+path);
                                }
                            });
            }
        }



        if (requestCode == REQUEST_IMAGE_CAMERA){
            if (resultCode == Activity.RESULT_OK){
                bitmap = (Bitmap)data.getExtras().get("data");
                profile.setImageBitmap(bitmap);
                Log.i("TAG", "Result=>"+bitmap);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // PERMISO DE LA CAMARA GARANTIZAR QUE SI ESTA EN EL MANIFEST

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          // Rechazar o permitir los permisos


        if (requestCode == REQUEST_PERMISSION_CAMERA){
            if (permissions.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                goToCamera();
            }else{
                Toast.makeText(this, "Se necesita habilitar los permisos", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // METODO PARA TOMAR FOTO
    public void tomarFoto(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                goToCamera();
            }else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
            }
        }else{
            goToCamera();
        }

    }


    private void goToCamera(){


        Intent cameraIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(cameraIntent,REQUEST_IMAGE_CAMERA);
        }
    }



    // METODOS PARA LA GEOLOCALIZACION

    public void GPS(View view){

        locationStart();
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local); // PROVAR EN UN CELULAR FISICO
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);  // PARA PROVAR EN EMULADOR

        mensaje1.setText("Encontrando Ubicacion...");
        mensaje2.setText("");

    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);

                    mensaje2.setText("Mi direccion es: "+DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        MainActivity mainActivity;

        public MainActivity getMainActivity() {

            return mainActivity;
        }

        public void setMainActivity(MainActivity mainActivity) {

            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String Coordenadas = "Lat = "
                    + loc.getLatitude() + " / Long = " + loc.getLongitude();
            mensaje1.setText(Coordenadas);
            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            mensaje1.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            mensaje1.setText("GPS Activado");
        }
        // PERMITE ACTUALIZAR Y VER EN TIEMPO REAL LAS CORDENADAS DE LONGITUD Y LATITUD
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

    // PASAR AL ACTIVITY PARA REGISTRAR LA CLASIFICACION TAXONOMICA
    public void ActivityClasificacion (View v){
        // Pasar el dato Nombre Cientifico para registrar la Clasificacion

        Intent clasificacion = new Intent(this, Clasificacion.class);
        clasificacion.putExtra("dato", edtNombreCientifico.getText().toString());
        clasificacion.putExtra("dato1", edtNombreComun.getText().toString());
        startActivity(clasificacion);

        // Pasar el dato Nombre Comun para registrar la Clasificacion
     //   Intent clasificacion1 = new Intent(this, Clasificacion.class);
      //  clasificacion1.putExtra("dato", edtNombreComun.getText().toString());
   //     startActivity(clasificacion1);
    }


    // PASAR REGRESAR AL ACTIVITY MENU
  //  public void ActivityMenuEspeies (View v){
    //    Intent menu = new Intent(this, MenuEspecies.class);
  //      startActivity(menu);
  //  }
}
