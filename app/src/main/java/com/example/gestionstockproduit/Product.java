package com.example.gestionstockproduit;

public class Product {

    private int id;
    private String nom;
    private String description;
    private int quantite;
    private String categorie;
    private float prix;

    public Product(int id, String nom, String description, float prix, int quantite, String categorie) {

        this.id = id;
        this.nom = nom;
        this.description = description;
        this.quantite = quantite;
        this.categorie = categorie;
        this.prix = prix;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    // Optional: Override toString() for easy printing/debugging
    @Override
    public String toString() {
        return "Product{" +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", quantite=" + quantite +
                ", categorie=" + categorie +
                ", prix=" + prix +
                '}';
    }
}
