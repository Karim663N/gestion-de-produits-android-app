package com.example.gestionstockproduit;

import android.content.Intent;
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


public class ProductListActivity extends AppCompatActivity {

    public ArrayList<Product> produits;
    TextView nom, description, prix, quantite, categorie;
    SQLiteDatabase bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_par_produit);
        ListView list = findViewById(R.id.listView);


        bd = openOrCreateDatabase("GestionStock", MODE_PRIVATE,null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS produits(id integer primary key autoincrement,nom VARCHAR,decription VARCHAR,prix REAL,quantite integer,categorie VARCHAR);");


        /* test ajout*******************************************
        bd.execSQL("INSERT INTO produits(id,nom,decription,prix,quantite,categorie) VALUES(null,'Lenovo','gaming edition',3500,2,'laptops');");
         -----------------------------------------------------*/

        /*Affichage*/
        Cursor c=bd.rawQuery("SELECT * FROM produits",null);
        if(c.getCount()==0)
        {
            Toast msg=Toast.makeText(this, "Rupture de stock",Toast.LENGTH_LONG);
            msg.show();
            return;
        }
        StringBuffer buffer=new StringBuffer();

        produits= new ArrayList<Product>();

        while(c.moveToNext())
        {
            int id = c.getInt(0);
            String nom = c.getString(1);
            String description = c.getString(2);
            int quantite = c.getInt(4);
            Float prix = c.getFloat(3);
            String categorie = c.getString(5);
            Product product = new Product(id,nom, description, prix, quantite, categorie);
            produits.add(product);


// Set values into TextViews

        }
        c.close();

        for (Product product : produits) {
            int id = product.getId();
            String nom = product.getNom();
            String description = product.getDescription();
            int quantite = product.getQuantite();
            float prix = product.getPrix();
            String categorie = product.getCategorie();

            if (produits.isEmpty()) {
                Log.d("ProductListActivity", "The produits list is empty.");
            } else {
                Log.d("ProductListActivity", "The produits list is not empty.");
            }

            Log.d("Product Details", "Nom: " + nom + ", Description: " + description +
                    ", Quantite: " + quantite + ", Prix: " + prix + ", Categorie: " + categorie);
            System.out.println("Nom: " + nom + ", Description: " + description +
                    ", Quantite: " + quantite + ", Prix: " + prix + ", Categorie: " + categorie);
        }

        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this, R.layout.product_item, produits) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // Check if the view is being reused, otherwise inflate a new view for an item
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
                }

                // Get the Product at the current position
                Product currentProduct = getItem(position);

                // Find TextViews in the item layout
                TextView nomTextView = convertView.findViewById(R.id.nomTextView);
                TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
                TextView prixTextView = convertView.findViewById(R.id.prixTextView);
                TextView quantiteTextView = convertView.findViewById(R.id.quantiteTextView);
                TextView categorieTextView = convertView.findViewById(R.id.categorieTextView);

                // Set values into TextViews from the --------current---------- Product
                if (currentProduct != null) {
                    nomTextView.setText(currentProduct.getNom());
                    descriptionTextView.setText(currentProduct.getDescription());
                    prixTextView.setText(String.valueOf(currentProduct.getPrix()));
                    quantiteTextView.setText(String.valueOf(currentProduct.getQuantite()));
                    categorieTextView.setText(currentProduct.getCategorie());
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
