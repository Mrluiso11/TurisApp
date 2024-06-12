package com.lagn.turisapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.widget.Toast;
import android.util.Log;
import com.lagn.turisapp.Adaptadores.SitiosAdapter;
import com.lagn.turisapp.Clases.Sitios;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recSitios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar RecyclerView
        recSitios = findViewById(R.id.recSitios);

        // Leer sitios desde JSON
        ArrayList<Sitios> sitios = leerSitiosDesdeJSON(this, "sitios.json");

        // Configurar adaptador y RecyclerView
        SitiosAdapter adaptador = new SitiosAdapter(this, sitios);
        recSitios.setLayoutManager(new LinearLayoutManager(this));
        recSitios.setAdapter(adaptador);
/*
        recSitios=findViewById(R.id.recSitios);
        ArrayList<Sitios> sitios = new ArrayList<>();
        sitios.add(new Sitios("Canal de panamá","Michael Cordones","el canal de panampá",0,getDrawable(R.drawable.canaldepanama), getDrawable(R.drawable.user)));
        sitios.add(new Sitios("Boquete","Edgardo Hernandez","Boquete Chiriqui",0,getDrawable(R.drawable.boquete), getDrawable(R.drawable.user2)));
        SitiosAdapter adaptador =  new SitiosAdapter(this,sitios);
        recSitios.setLayoutManager(new LinearLayoutManager(this));
        recSitios.setAdapter(adaptador);*/
    }


    // Método para leer los sitios desde un archivo JSON
    private ArrayList<Sitios> leerSitiosDesdeJSON(Context context, String fileName) {
        ArrayList<Sitios> listaSitios = new ArrayList<>();
        String json;
        try {
            // Leer el archivo JSON desde la carpeta de assets
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            // Parsear el archivo JSON
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String titulo = jsonObject.getString("titulo");
                String autor = jsonObject.getString("autor");
                String detalles = jsonObject.getString("detalles");
                String telefono = jsonObject.getString("telefono");
                int megusta = jsonObject.getInt("megusta");
                String imagenNombre = jsonObject.getString("imagen");
                String iconoNombre = jsonObject.getString("icono");

                // Cargar la imagen desde la carpeta de recursos drawable
                int imagenResource = context.getResources().getIdentifier(imagenNombre, "drawable", context.getPackageName());
                int iconoResource = context.getResources().getIdentifier(iconoNombre, "drawable", context.getPackageName());

                // Obtener los drawables de las imágenes
                Drawable imagenDrawable = imagenResource != 0 ? ResourcesCompat.getDrawable(context.getResources(), imagenResource, null) : null;
                Drawable iconoDrawable = iconoResource != 0 ? ResourcesCompat.getDrawable(context.getResources(), iconoResource, null) : null;

                // Crear el objeto Sitios y agregarlo a la lista
                Sitios sitio = new Sitios(titulo, autor, detalles, telefono,megusta, imagenDrawable, iconoDrawable);
                listaSitios.add(sitio);

                // Registro de depuración
                Log.d("JSON_DEBUG", "Sitio leído - Título: " + titulo + ", Autor: " + autor + ", Detalles: " + detalles + ",telefono:"+telefono+", Me gusta: " + megusta);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // Mostrar un mensaje de error en caso de fallo
            Toast.makeText(context, "Error al leer o parsear el archivo JSON", Toast.LENGTH_SHORT).show();
        }

        // Registro de depuración para verificar la cantidad de sitios en la lista
        Log.d("LIST_DEBUG", "Cantidad de sitios en la lista: " + listaSitios.size());

        return listaSitios;
    }
}
