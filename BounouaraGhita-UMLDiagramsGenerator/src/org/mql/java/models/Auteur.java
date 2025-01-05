package org.mql.java.models;

import java.util.List;

import org.mql.java.annotation.Form;
import org.mql.java.annotation.isObject;

@Form
public class Auteur {
    private String nom;
    private String nationalite;
    @isObject(type = "Aggregation")
    private Adresse adresse; // Agrégation
    @isObject(type = "Composition")
    private List<Livre> livres; // Composition: La durée de vie des livres est liée à la durée de vie de l'auteur

    public Auteur() {
    }

    public Auteur(String nom, String nationalite, Adresse adresse, List<Livre> livres) {
        this.nom = nom;
        this.nationalite = nationalite;
        this.adresse = adresse;
        this.livres = livres;
    }

    public String getNom() {
        return nom;
    }

    public String getNationalite() {
        return nationalite;
    }

    private void ecrireLivre() {
        System.out.println("Livre écrit par " + nom);
    }
}