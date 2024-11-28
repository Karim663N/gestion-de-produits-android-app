package com.example.gestionstockproduit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, ArrayList<Product> arrayList) {
        super(context, R.layout.product_item, arrayList);
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }

        Product product = getItem(position);

        TextView nomTextView = convertView.findViewById(R.id.nomTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        TextView prixTextView = convertView.findViewById(R.id.prixTextView);
        TextView quantiteTextView = convertView.findViewById(R.id.quantiteTextView);
        TextView categorieTextView = convertView.findViewById(R.id.categorieTextView);

        if (product != null) {
            nomTextView.setText("Nom: " + product.getNom());
            descriptionTextView.setText("Description: " + product.getDescription());
            prixTextView.setText("Prix: " + product.getPrix());
            quantiteTextView.setText("Quantite: " + product.getQuantite());
            categorieTextView.setText("Categorie: " + product.getCategorie());
        }

        return convertView;
    }
}
