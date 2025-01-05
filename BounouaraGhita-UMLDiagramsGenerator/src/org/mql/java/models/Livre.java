package org.mql.java.models;

import org.mql.java.annotations.isObject;

public class Livre {
	private String titre;
    private String isbn;
    @isObject(type = "dependency")
    private Auteur auteur;

    public Livre(String titre, String isbn) {
        this.titre = titre;
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void afficherDetails() {
        auteur = new Auteur();
        System.out.println("Auteur: " + auteur.getNom() + " (" + auteur.getNationalite() + ")");
    }
}
