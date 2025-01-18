package org.mql.java.models;

public class Personne {
	 private String firstname;
	    private String nationalite;
	    private String lastname;
	    private int age;
	    private String CNE;
		public Personne(String firstname, String nationalite, String lastname, int age, String cNE) {
			super();
			this.firstname = firstname;
			this.nationalite = nationalite;
			this.lastname = lastname;
			this.age = age;
			CNE = cNE;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getNationality() {
			return nationalite;
		}
		public void setNationality(String nationalite) {
			this.nationalite = nationalite;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getCNE() {
			return CNE;
		}
		public void setCNE(String cNE) {
			CNE = cNE;
		}
		@Override
		public String toString() {
			return "Personne [firstname=" + firstname + ", nationality=" + nationalite + ", lastname=" + lastname
					+ ", age=" + age + ", CNE=" + CNE + "]";
		}
	    
	    
	    
	    
}
