package com.example.gestionstockproduit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductAjoutForm extends AppCompatActivity {

    Button ajouter;
    EditText nom, description, prix, quantite;
    SQLiteDatabase bd;
    Spinner spin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productajoutform);
        bd = openOrCreateDatabase("GestionStock", MODE_PRIVATE,null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS categories(id integer primary key autoincrement,nom VARCHAR,quantite integer);");
        /* String[] item = {"smartphone", "laptop"};  */
        /* screaching for existing categories from database */
        List<String> categoriesList = new ArrayList<>();

        // Query to fetch values from the 'nom' column
        Cursor cursor = bd.rawQuery("SELECT * FROM categories", null);

// Iterate through the cursor to extract values
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String category = cursor.getString(1);
                categoriesList.add(category);
            } while (cursor.moveToNext());

            // Close the cursor after fetching data
            cursor.close();
        }

        // Convert the list to an array
        String[] item = categoriesList.toArray(new String[0]);

        /* ---------------------------*/


        ajouter = findViewById(R.id.ajout);
        nom = findViewById(R.id.editTextNom);
        description = findViewById(R.id.editTextDescription);
        prix = findViewById(R.id.editTextPrix);
        quantite = findViewById(R.id.editTextQuantiteCateg);
        spin = findViewById(R.id.demo_spinner);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, item);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the add (insert) query
                String selectedCategory = spin.getSelectedItem().toString();
                String sqlAddQuery = "INSERT INTO produits(nom, decription, prix, quantite, categorie) VALUES (?, ?, ?, ?, ?)";
                bd.execSQL(sqlAddQuery, new String[]{nom.getText().toString(), description.getText().toString(),
                        prix.getText().toString(), quantite.getText().toString(), selectedCategory});
                Toast.makeText(ProductAjoutForm.this, "Insert successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
