package com.example.flora;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class CustomAdapter implements ListAdapter {
    ArrayList<Flora> arrayList;
    Context context;
    public CustomAdapter(Context context, ArrayList<Flora> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) { }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) { }

    @Override
    public int getCount() {

        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Flora floraData = arrayList.get(position);
        if(convertView == null){
          LayoutInflater layoutInflater = LayoutInflater.from(context);
          convertView = layoutInflater.inflate(R.layout.list_row, null);
         /* convertView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent(Intent.ACTION_VIEW);
                  i.setData(Uri.parse(floraData.imagen));
                  context.startActivity(i);
                  //Toast.makeText(context,subjectData.Link,Toast.LENGTH_LONG).show();
              }
          });*/

          Bitmap decodedByte = decodeBase64(floraData.getImagen());
          ImageView mImg = convertView.findViewById(R.id.list_image);
          mImg.setImageBitmap(decodedByte);

          TextView nombreCientifico=convertView.findViewById(R.id.nombre_cientifico);

          nombreCientifico.setText(floraData.getNombre_cientifico());

      }
        return convertView;


    }
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getViewTypeCount() {

        return arrayList.size();
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }
}
