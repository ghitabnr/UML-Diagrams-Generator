package org.mql.java.models;

import java.awt.print.Book;
import java.util.List;

import org.mql.java.annotations.Form;
import org.mql.java.annotations.Relation;

@Form
public class Author extends Personne{
    public Author(String firstname, String nationalite, String lastname, int age, String cNE) {
		super(firstname, nationalite, lastname, age, cNE);
		// TODO Auto-generated constructor stub
	}

	private String name;
    private String nationality;
    @Relation(type = "Aggregation")
    private Adresse adresse;
    @Relation(type = "Composition")
    private List<Book> books; 

   
    

	public Author(String firstname, String nationalite, String lastname, int age, String cNE, String name,
			String nationality, Adresse adresse, List<Book> books) {
		super(firstname, nationalite, lastname, age, cNE);
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