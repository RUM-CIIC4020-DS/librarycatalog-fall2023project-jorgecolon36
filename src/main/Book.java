package main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book {
	
	// Private variables:
	private int id;
	private String title;
	private String author;
	private String genre;
	private LocalDate lastCheckOut;
	private boolean checkedOut;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public LocalDate getLastCheckOut() {
		return lastCheckOut;
	}
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut = lastCheckOut;
	}
	public boolean isCheckedOut() {
		if(checkedOut) {
			return true;
		}
		return false;
	}
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	
	public Book() {
		this.id = 0;
		this.title = "";
		this.author = "";
		this.genre = "";
		this.lastCheckOut = LocalDate.of(2000, 01, 1);
		this.checkedOut = true;
	}
	
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
