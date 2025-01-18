package org.mql.java.models;

import org.mql.java.annotations.Relation;

public class Book {
	private String title;
    private String isbn;
    @Relation(type = "Aggregation")
    private Author author;

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void Details() {
    	author = new Author(null, null, null, 0, null);
        System.out.println("Auteur: " + author.getName() + " (" + author.getNationality() + ")");
    }
}
