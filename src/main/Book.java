package main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * A class representing a book with parameters id, title, author, genre, last check out date and 
 * it is checked out. The methods within the class are getters and setters a toSting() override and 
 * a calculation for how much a user owes.
 * 
 *  @author Jorge L. Colon
 */
public class Book {
	
	// Private variables:
	private int id;
	private String title;
	private String author;
	private String genre;
	private LocalDate lastCheckOut;
	private boolean checkedOut;
	
	/**
	 * Getter for id of the book
	 * 
	 * @return The id of the book
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter for If of the book
	 * @param id Id of the book
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Getter for name of the book
	 * 
	 * @return The name of the book
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Setter for title of the book
	 * @param title Title of the book
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter for author of the book
	 * 
	 * @return The author of the book
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * Setter for author of the book
	 * @param author Author of the book
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * Getter for genre of the book
	 * 
	 * @return The genre of the book
	 */
	public String getGenre() {
		return genre;
	}
	/**
	 * Setter for genre of the book
	 * @param genre Genre of the book
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
	 * Getter for the checkout date of the book
	 * 
	 * @return The checkout date of the book
	 */
	public LocalDate getLastCheckOut() {
		return lastCheckOut;
	}
	/**
	 * Setter for the checkout date of the book
	 * @param lastCheckOut last checked out date
	 */
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut = lastCheckOut;
	}
	
	/**
	 * Verifies if the book is checked out
	 * 
	 * @return The check out verification
	 */
	public boolean isCheckedOut() {
		if(checkedOut) {
			return true;
		}
		return false;
	}
	/**
	 * Setter for when a book is checked out
	 * @param checkedOut last checked out date
	 */
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	
	/**
	 * This is a default constructor for testing
	 */
	public Book() {
		this.id = 0;
		this.title = "";
		this.author = "";
		this.genre = "";
		this.lastCheckOut = LocalDate.of(2000, 01, 1);
		this.checkedOut = true;
	}
	
	/**
	 * Contructor for the class Book.
	 *
	 * @param id - The id of the book
	 * @param title - Title of the book
	 * @param author - Who authored the book
	 * @param genre - Genre of the book
	 * @param lastCheckOut - The last date where someone borrowed the book.
	 * @param checkedOut - Indicates whether the book is currently checked out of the library
	 */
	public Book(int id, String title, String author, String genre, LocalDate lastCheckOut, boolean checkedOut) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.lastCheckOut = lastCheckOut;
		this.checkedOut = checkedOut;
	}
	
	@Override
	public String toString() {
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		String title = getTitle().toUpperCase();
		String author = getAuthor().toUpperCase();
		
		return title + " BY " +author;
	}
	
	/**
	 * Calculates the amount of dollars owed from a book. The book will owe if 
	 * 31 days have passed. The amount is given by $10 as a base fee and $1.50 
	 * for every extra day.
	 *
	 * @return The amount of money owed for a book
	 */
	public float calculateFees() {
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 */
		if(isCheckedOut() == false) {
			return 0;
		}
		LocalDate today = LocalDate.of(2023, 9, 15);
		
		long days = getLastCheckOut().until(today,ChronoUnit.DAYS);
		if(days>=31) {
			return (float) ((days-31)*1.5 + 10);
		}
		return 0;
	}
}
