package com.example.gestionstockproduit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button B1;
    Button B2;
    Button bonentre;
    Button bonsortie;
    Button nouveauproduit;
    Button nouveaucategorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        B1=findViewById(R.id.button1);
        B2=findViewById(R.id.button2);
        bonentre=findViewById(R.id.bonentree);
        bonsortie=findViewById(R.id.bonsortie);
        nouveauproduit=findViewById(R.id.nouveauproduit);
        nouveaucategorie=findViewById(R.id.nouveaucategorie);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, ProductListActivity.class);
                startActivity(i);
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, CategoryListActivity.class);
                startActivity(i);
            }
        });
        bonentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, BonDentree.class);
                startActivity(i);
            }
        });
        bonsortie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, BonDeSortie.class);
                startActivity(i);
            }
        });
        nouveauproduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, ProductAjoutForm.class);
                startActivity(i);
            }
        });
        nouveaucategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, CategoryAjoutForm.class);
                startActivity(i);
            }
        });

    }
}