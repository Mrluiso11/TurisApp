package com.lagn.turisapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lagn.turisapp.Clases.Sitios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class atv_info_sitios extends AppCompatActivity {
 Toolbar toolbar;
 TextView lbl_info_titulo,lbl_detalles;
 ImageView img_infoSitio;
 private String telefono;
 private String titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_atv_info_sitios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbar);
        img_infoSitio = findViewById(R.id.img_infoSitio);
        lbl_info_titulo=findViewById(R.id.lbl_info_titulo);
        lbl_detalles = findViewById(R.id.lbl_detalles);

        Intent intent = getIntent();
        String idString = intent.getStringExtra("id");

        if (idString != null) {
            try {
                int id = Integer.parseInt(idString);

                Sitios sitio = leerSitioDesdeJSON(this, "sitios.json", id);
                if (sitio != null) {
                    lbl_info_titulo.setText(sitio.getTitulo());
                    lbl_detalles.setText(sitio.getDetalles());
                    img_infoSitio.setImageDrawable(sitio.getImagen());
                    telefono = sitio.getTelefono();
                    titulo = sitio.getTitulo();
                } else {
                    Toast.makeText(this, "Sitio no encontrado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "ID no proporcionado", Toast.LENGTH_SHORT).show();
            finish();
        }

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        if (item.getItemId() == R.id.btn_telefono) {
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + telefono));
            startActivity(intent);
        } else if (item.getItemId() == R.id.btn_location) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("google.navigation:q=" + titulo));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private Sitios leerSitioDesdeJSON(Context context, String fileName, int sitioId) {
        String json;
        try (InputStream is = context.getAssets().open(fileName)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            json = new String(buffer, StandardCharsets.UTF_8);

            // Parsear el archivo JSON
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");

                // Verificar si el ID coincide con el ID buscado
                if (id == sitioId) {
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
                    Drawable imagenDrawable = (imagenResource != 0) ? ResourcesCompat.getDrawable(context.getResources(), imagenResource, null) : null;
                    Drawable iconoDrawable = (iconoResource != 0) ? ResourcesCompat.getDrawable(context.getResources(), iconoResource, null) : null;

                    // Crear y devolver el objeto Sitios
                    return new Sitios(id, titulo, autor, detalles, telefono, megusta, imagenDrawable, iconoDrawable);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // Mostrar un mensaje de error en caso de fallo
            Toast.makeText(context, "Error al leer o parsear el archivo JSON", Toast.LENGTH_SHORT).show();
        }

        // Si no se encuentra el sitio con el ID proporcionado, se devuelve null
        return null;
    }


}