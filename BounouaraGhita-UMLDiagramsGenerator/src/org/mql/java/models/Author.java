package org.mql.java.models;

import java.awt.print.Book;
import java.util.List;

import org.mql.java.annotations.Form;
import org.mql.java.annotations.isObject;

@Form
public class Author {
    private String name;
    private String nationality;
    @isObject(type = "Aggregation")
    private Adresse adresse; // Agrégation
    @isObject(type = "Composition")
    private List<Book> books; // Composition: La durée de vie des livres est liée à la durée de vie de l'auteur

    public Author() {
    }

    public Author(String name, String nationality, Adresse adresse, List<Book> books) {
        this.name = name;
        this.nationality = nationality;
        this.adresse = adresse;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    private void bookWritenBy() {
        System.out.println("Livre écrit par " + name);
    }
}