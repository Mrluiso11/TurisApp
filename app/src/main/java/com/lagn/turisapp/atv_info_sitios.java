package com.lagn.turisapp;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class atv_info_sitios extends AppCompatActivity {
 Toolbar toolbar;
 TextView lbl_info_titulo,lbl_detalles;
 ImageView img_infoSitio;
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
        lbl_info_titulo.setText(getIntent().getStringExtra("titulo"));
        lbl_detalles.setText(getIntent().getStringExtra("detalles"));

        Uri imagenUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imagenUri != null) {
            img_infoSitio.setImageURI(imagenUri);
        } else {
            Log.e("IMG_ERROR", "No se pudo obtener el URI de la imagen");
        }

        setSupportActionBar(toolbar);
       toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(view -> {

            finish();
        });
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
            intent.setData(Uri.parse("tel:"+ getIntent().getStringExtra("telefono")));
            startActivity(intent);}
        else if (item.getItemId() == R.id.btn_location) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("google.navigation:q="+getIntent().getStringExtra("titulo")));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}