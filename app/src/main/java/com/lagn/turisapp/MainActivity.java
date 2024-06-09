package com.lagn.turisapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        recSitios=findViewById(R.id.recSitios);
        ArrayList<Sitios> sitios = new ArrayList<>();
        sitios.add(new Sitios("Canal de panamá","Michael Cordones","el canal de panampá",0,getDrawable(R.drawable.canaldepanama), getDrawable(R.drawable.user)));
        SitiosAdapter adaptador =  new SitiosAdapter(this,sitios);
        recSitios.setLayoutManager(new LinearLayoutManager(this));
        recSitios.setAdapter(adaptador);
    }
}