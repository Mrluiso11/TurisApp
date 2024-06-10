package com.lagn.turisapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.lagn.turisapp.*;
import com.lagn.turisapp.Clases.Sitios;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SitiosAdapter extends RecyclerView.Adapter<SitiosAdapter.ViewHolder> {
    Context contexto;
    ArrayList <Sitios> lista;

    public SitiosAdapter(Context contexto, ArrayList<Sitios> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }

    @NonNull
    @Override
    public SitiosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(contexto).inflate(R.layout.lugares_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SitiosAdapter.ViewHolder holder, int position) {
        holder.lbl_titulo.setText(lista.get(position).getTitulo());
        holder.lbl_autor.setText(lista.get(position).getAutor());
        holder.lbl_like.setText(String.valueOf(lista.get(position).getMegusta()));

        holder.img_lugares.setImageDrawable(lista.get(position).getImagen());
        holder.img_user.setImageDrawable(lista.get(position).getIcono());

       holder.img_like.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int cantidad = Integer.parseInt(holder.lbl_like.getText().toString());
               cantidad ++;
               holder.lbl_like.setText(String.valueOf(cantidad));
           }
       });


       holder.img_lugares.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Drawable imagenDrawable = lista.get(position).getImagen();
               Bitmap bitmapImagen = ((BitmapDrawable) imagenDrawable).getBitmap();
               Intent intent = new Intent(contexto,atv_info_sitios.class);
               intent.putExtra("titulo",lista.get(position).getTitulo());
               //intent.putExtra("lugares_imagen", bitmapImagen);
               contexto.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return lista.size();

    }
    public static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView lbl_titulo,lbl_autor,lbl_like;
        ImageView img_lugares,img_user,img_like,img_compartir;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lbl_titulo= itemView.findViewById(R.id.lbl_titulo);
            lbl_autor= itemView.findViewById(R.id.lbl_autor);
            lbl_like= itemView.findViewById(R.id.lbl_like);

            img_lugares = itemView.findViewById(R.id.img_lugares);
            img_lugares = itemView.findViewById(R.id.img_lugares);
            img_user = itemView.findViewById(R.id.img_user);
            img_like = itemView.findViewById(R.id.img_like);
            img_compartir = itemView.findViewById(R.id.img_compartir);


        }
    }

}
