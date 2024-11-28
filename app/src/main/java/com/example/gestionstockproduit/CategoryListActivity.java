package com.example.gestionstockproduit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {
    public ArrayList<Category> categories;
    TextView nom, quantite;
    SQLiteDatabase bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_par_categorie);
        ListView list = findViewById(R.id.listView);


        bd = openOrCreateDatabase("GestionStock", MODE_PRIVATE,null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS categories(id integer primary key autoincrement,nom VARCHAR,quantite integer);");


        /* test ajout********************************************
        bd.execSQL("INSERT INTO categories(id,nom,quantite) VALUES(null,'smartphone',0);");
         -----------------------------------------------------*/

        /* Saisie de la quantite de chaque categorie selon les produits*/
        Cursor z = bd.rawQuery("SELECT categorie, SUM(quantite) AS totalQuantity FROM produits GROUP BY categorie", null);
        while (z.moveToNext()) {
            String nomcateg = z.getString(0); // Get the category name
            int totalQuantity = z.getInt(1); // Get the total quantity for the category

            // Update the 'quantite' column in the 'categories' table with the total quantity for the category
            String sqlUpdateQuery = "UPDATE categories SET quantite = ? WHERE nom = ?";
            bd.execSQL(sqlUpdateQuery, new String[]{String.valueOf(totalQuantity), nomcateg});
        }

        z.close();

        /*Affichage*/
        Cursor c=bd.rawQuery("SELECT * FROM categories",null);
        if(c.getCount()==0)
        {
            Toast msg=Toast.makeText(this, "aucun categorie !",Toast.LENGTH_LONG);
            msg.show();
            return;
        }
        StringBuffer buffer=new StringBuffer();

        categories= new ArrayList<Category>();

        while(c.moveToNext())
        {
            int id = c.getInt(0);
            String nom = c.getString(1);
            int quantite = c.getInt(2);
            Category category = new Category(id,nom,quantite);
            categories.add(category);


// Set values into TextViews

        }
        c.close();

        for (Category category : categories) {
            int id = category.getId();
            String nom = category.getNom();
            int quantite = category.getQuantite();

            if (categories.isEmpty()) {
                Log.d("CategoryListActivity", "The category list is empty.");
            } else {
                Log.d("CategoryListActivity", "The category list is not empty.");
            }

            Log.d("Category Details", "Nom: " + nom + ", Quantite: " + quantite);
            System.out.println("Nom: " + nom + ", Quantite: " + quantite);
        }

        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, R.layout.categorie_item, categories) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // Check if the view is being reused, otherwise inflate a new view for an item
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.categorie_item, parent, false);
                }

                // Get the Product at the current position
                Category currentCategory = getItem(position);

                // Find TextViews in the item layout
                TextView nom = convertView.findViewById(R.id.nomTextView);
                TextView quantite = convertView.findViewById(R.id.quantiteTextView);

                // Set values into TextViews from the --------current---------- Product
                if (currentCategory != null) {
                    nom.setText(currentCategory.getNom());
                    quantite.setText(String.valueOf(currentCategory.getQuantite()));
                    /**********
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i= new Intent(ProductListActivity.this,ProductUpdateForm.class);
                            i.putExtra("Nom",nomTextView.getText().toString());
                            i.putExtra("Description",descriptionTextView.getText().toString());
                            i.putExtra("Prix",prixTextView.getText().toString());
                            i.putExtra("Quantite",quantiteTextView.getText().toString());
                            i.putExtra("Categorie",categorieTextView.getText().toString());
                            i.putExtra("id",currentProduct.getId());
                            startActivity(i);
                        }
                    });
                     *///////////////
                }

                return convertView;
            }




        };
        // Find the ListView
        ListView listView = findViewById(R.id.listView);

        // Set the adapter for the ListView
        listView.setAdapter(adapter);


    }
}
