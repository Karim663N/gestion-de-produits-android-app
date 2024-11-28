package com.example.gestionstockproduit;

public class Category {
    private int id;
    private String nom;
    private int quantite;

    public Category(int id, String nom, int quantite) {

        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
    }

    // Getters and setters for each field

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    // Optional: Override toString() for easy printing/debugging
    @Override
    public String toString() {
        return "Category{" +
                ", nom='" + nom + '\'' +
                '}';
    }
}


