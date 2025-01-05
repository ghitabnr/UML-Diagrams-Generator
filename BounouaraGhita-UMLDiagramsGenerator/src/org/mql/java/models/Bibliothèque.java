package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;

public class Bibliothèque implements Ressource {
    private String nom;
    private List<Livre> livres;

    public Bibliothèque(String nom) {
        this.nom = nom;
        this.livres = new ArrayList<>();
    }

    public void ajouterLivre(Livre livre) {
        livres.add(livre);
    }

    @Override
    public String getDescription() {
        return "Bibliothèque: " + nom + " (" + livres.size() + " livres)";
    }
}