package org.mql.java.models;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.annotations.Relation;

public class Bibliotheque implements Resource {
    private String name;
    @Relation(type = "Composition")
    private List<Book> books;

    public Bibliotheque(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book b) {
    	books.add(b);
    }

    @Override
    public String getDescription() {
        return "Bibliothèque: " + name + " (" + books.size() + " livres)";
    }
}