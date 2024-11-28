package com.example.gestionstockproduit;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryAjoutForm extends AppCompatActivity {

    Button ajouter;
    EditText nom, quantite;
    SQLiteDatabase bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoryajoutform);
        bd = openOrCreateDatabase("GestionStock", MODE_PRIVATE,null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS categories(id integer primary key autoincrement,nom VARCHAR,quantite integer);");
        /* String[] item = {"smartphone", "laptop"};  */
        /* screaching for existing categories from database */
        List<String> categoriesList = new ArrayList<>();


        ajouter = findViewById(R.id.ajout);
        nom = findViewById(R.id.editTextNom);
        quantite = findViewById(R.id.editTextQuantiteCateg);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the add (insert) query
                String sqlAddQuery = "INSERT INTO categories(nom, quantite) VALUES (?, ?)";
                bd.execSQL(sqlAddQuery, new String[]{nom.getText().toString(), quantite.getText().toString()});
                Toast.makeText(CategoryAjoutForm.this, "Insert successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
