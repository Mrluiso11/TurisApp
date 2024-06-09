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
import androidx.core.content.res.ResourcesCompat;

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
        String usuario = "user";
        recSitios=findViewById(R.id.recSitios);
        ArrayList<Sitios> sitios = new ArrayList<>();
        sitios.add(new Sitios("Canal de panamá","Michael Cordones","el canal de panampá",0,null, getDrawable(R.drawable.user)));
        SitiosAdapter adaptador =  new SitiosAdapter(this,sitios);
        recSitios.setLayoutManager(new LinearLayoutManager(this));
        recSitios.setAdapter(adaptador);*/
    }


    private ArrayList<Sitios> leerSitiosDesdeJSON(Context context, String fileName) {
        ArrayList<Sitios> listaSitios = new ArrayList<>();
        String json;
        try {
            // Lee el archivo JSON desde la carpeta de assets
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            // Parsea el archivo JSON
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String titulo = jsonObject.getString("titulo");
                String autor = jsonObject.getString("autor");
                String detalles = jsonObject.getString("detalles");
                int megusta = jsonObject.getInt("megusta");
                String imagenNombre = jsonObject.getString("imagen");
                String iconoNombre = jsonObject.getString("icono");

                // Carga la imagen desde la carpeta de recursos drawable
                int imagenResource = context.getResources().getIdentifier(imagenNombre, "drawable", context.getPackageName());
                int iconoResource = context.getResources().getIdentifier(iconoNombre, "drawable", context.getPackageName());

                Drawable imagenDrawable = imagenResource != 0 ? ResourcesCompat.getDrawable(context.getResources(), imagenResource, null) : null;
                Drawable iconoDrawable = iconoResource != 0 ? ResourcesCompat.getDrawable(context.getResources(), iconoResource, null) : null;

                Sitios sitio = new Sitios(titulo, autor, detalles, megusta, imagenDrawable, iconoDrawable);

                listaSitios.add(sitio);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al leer o parsear el archivo JSON", Toast.LENGTH_SHORT).show();
        }
        return listaSitios;
    }



}