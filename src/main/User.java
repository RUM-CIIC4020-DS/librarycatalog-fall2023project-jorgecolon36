package main;

import interfaces.List;

/**
 * A class representing a user with parameters id, name and checkedOutList.
 * The methods within the class are getters and contructor
 *  @author Jorge L. Colon
 */
public class User {
	
	private int id;
	private String name;
	private List<Book> checkedOutList;
	
	/**
	 * Getter for id of the user
	 * 
	 * @return The id of the user
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * Setter for If of the user
	 * @param id Id of the user
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Getter for name of the user
	 * 
	 * @return The name of the user
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Setter for the name of the user 
	 * 
	 * @param name Name of the user
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Getter for list of books checked out by a user 
	 * 
	 * @return List of books
	 */
	public List<Book> getCheckedOutList() {
		return this.checkedOutList;
	}
	/**
	 * Setter for list of books checked out by a user 
	 * 
	 * @param checkedOutList List of books checked out by a user
	 */
	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutList = checkedOutList;
	}
	
	/**
	 * Contructor for the class Book.
	 *
	 * @param id - Unique number that identifies the user.
	 * @param name - Full name of the user.
	 * @param checkedOutList - A List of books that the user has currently checkedout.
	 */
	public User(int id, String name, List<Book> checkedOutList) {
		this.id = id;
		this.name = name;
		this.checkedOutList = checkedOutList;
	}
	
}
