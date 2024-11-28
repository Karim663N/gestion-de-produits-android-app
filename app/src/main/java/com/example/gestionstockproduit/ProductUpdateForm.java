package com.example.gestionstockproduit;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductUpdateForm extends AppCompatActivity {

    Button updateB;
    Button deleteB;
    EditText nom, description, prix, quantite;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productform);

        // Initialize your database in the onCreate method
        bd = openOrCreateDatabase("GestionStock", MODE_PRIVATE, null);

        updateB = findViewById(R.id.update);
        deleteB = findViewById(R.id.delete);
        nom = findViewById(R.id.editTextNom);
        description = findViewById(R.id.editTextDescription);
        prix = findViewById(R.id.editTextPrix);
        quantite = findViewById(R.id.editTextQuantiteCateg);

        Bundle param = getIntent().getExtras();
        if (param != null) {
            String n = param.getString("Nom");
            String d = param.getString("Description");
            String p = param.getString("Prix");
            String q = param.getString("Quantite");
            int IDP = param.getInt("id");
            nom.setText(n);
            description.setText(d);
            prix.setText(p);
            quantite.setText(q);
            Log.d("IDP Value", String.valueOf(IDP));
            updateB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform the update query

                    String sqlUpdateQuery = "UPDATE produits SET nom = ?, decription = ?, prix = ?, quantite = ? WHERE id = ?";
                    bd.execSQL(sqlUpdateQuery, new String[]{nom.getText().toString(), description.getText().toString(),
                            prix.getText().toString(), quantite.getText().toString(),
                            String.valueOf(IDP)});
                    Toast.makeText(ProductUpdateForm.this, "Update successful!", Toast.LENGTH_SHORT).show();
                }
            });

            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle delete action if needed
                }
            });
        }
    }
}
