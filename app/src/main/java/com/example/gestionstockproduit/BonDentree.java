package com.example.gestionstockproduit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BonDentree extends AppCompatActivity {
    Button ajouter;
    EditText description, prix, quantite;
    SQLiteDatabase bd;
    Spinner itemc;
    String[] categories;
    AutoCompleteTextView edit;
    String[] items; // Declare items as a class-level variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bondentree);
        ajouter = findViewById(R.id.ajout);
        edit = findViewById(R.id.editTextNom);
        description = findViewById(R.id.editTextDescription);
        prix = findViewById(R.id.editTextPrix);
        quantite = findViewById(R.id.editTextQuantiteCateg);
        itemc = findViewById(R.id.demo_spinner);
        bd = openOrCreateDatabase("GestionStock", MODE_PRIVATE, null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS categories(id integer primary key autoincrement,nom VARCHAR,quantite integer);");

        // Fetch data and set up AutoCompleteTextView
        Cursor x = bd.rawQuery("SELECT * FROM produits", null);
        List<String> itemList = new ArrayList<>();
        while (x.moveToNext()) {
            String itemn = x.getString(1); // Retrieve data from column 1
            itemList.add(itemn); // Add the retrieved item to the list
        }
        x.close();
        items = itemList.toArray(new String[0]);
        edit.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, items));
        /* looking for existing category------------------------------------------------------------------- */
        Cursor cursorCategories = bd.rawQuery("SELECT * FROM categories", null);
        List<String> categoriesList = new ArrayList<>();
        while (cursorCategories.moveToNext()) {
            String categoryName = cursorCategories.getString(1); // Retrieve data from column 1
            categoriesList.add(categoryName); // Add the retrieved item to the list
        }
        cursorCategories.close();

        // Set categories to the Spinner adapter
        categories = categoriesList.toArray(new String[0]);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemc.setAdapter(categoriesAdapter);
    /*--------------------------------------------------------------------------------------------*/
        // TextWatcher implementation
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Implementation if needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Implementation if needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputText = s.toString().trim().toLowerCase(); // Get the current text in the AutoCompleteTextView
                List<String> filteredItems = new ArrayList<>();

                // Filter the items based on the input text
                for (String item : items) {
                    if (item.toLowerCase().contains(inputText)) {
                        filteredItems.add(item);
                    }
                }

                // Convert the filtered list to an array and update the adapter
                edit.setAdapter(new ArrayAdapter<>(BonDentree.this,
                        android.R.layout.simple_dropdown_item_1line, filteredItems.toArray(new String[0])));
            }
        };

        // Attach the TextWatcher to your AutoCompleteTextView
        edit.addTextChangedListener(textWatcher);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCategory = itemc.getSelectedItem().toString();
                String nomValue = edit.getText().toString();
                String descriptionValue = description.getText().toString();
                String prixValue = prix.getText().toString();
                String quantiteValue = quantite.getText().toString();

// Check if the product already exists in the database
                String checkIfExistsQuery = "SELECT quantite FROM produits WHERE nom = ?";
                Cursor cursor = bd.rawQuery(checkIfExistsQuery, new String[]{nomValue});

                if (cursor != null && cursor.moveToFirst()) {
                    // If the product exists, update its quantity
                    int existingQuantity = cursor.getInt(0);
                    int newQuantity = Integer.parseInt(quantiteValue);
                    int updatedQuantity = existingQuantity + newQuantity;

                    String updateQuery = "UPDATE produits SET quantite = ? WHERE nom = ?";
                    bd.execSQL(updateQuery, new String[]{String.valueOf(updatedQuantity), nomValue});

                    Toast.makeText(BonDentree.this, "Update successful!", Toast.LENGTH_SHORT).show();
                } else {
                    // If the product doesn't exist, perform an insert
                    String sqlAddQuery = "INSERT OR REPLACE INTO produits(nom, decription, prix, quantite, categorie) VALUES (?, ?, ?, ?, ?)";
                    bd.execSQL(sqlAddQuery, new String[]{nomValue, descriptionValue, prixValue, quantiteValue, selectedCategory});
                    Toast.makeText(BonDentree.this, "Insert successful!", Toast.LENGTH_SHORT).show();
                }

                if (cursor != null) {
                    cursor.close();
                }

            }
        });
    }
}
