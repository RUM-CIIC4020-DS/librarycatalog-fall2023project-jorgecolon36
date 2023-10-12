package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;


import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;

/**
 * This is the class that will manage all the books and any operation related 
 * to them. It will have 2 fields: Catalog - (List Book) a List of books that the library owns.
 * and Users - (List User) a List of users that are clients of the library.
 * 
 * The list used in this class is array list, this is because of its simplicity and
 * the fact that searching for an element within the array is easier.
 * 
 *  @author Jorge L. Colon
 */
public class LibraryCatalog {
	
	private List<Book> Catalog = new ArrayList<>();
	private List<User> Users = new ArrayList<>();
	
	/**
	 * Catalog constructor
	 * 
	 * @throws IOException if there is an error readingor writing files
	 */
	public LibraryCatalog() throws IOException {
		this.Catalog = getBooksFromFiles();
		this.Users = getUsersFromFiles();
		
	}
	
	/**
	 * This method will read the catalog.csv file present in the data folder 
	 * and generate a List of Books from it.
	 * 
	 * @return A list of objects type Book from a csv file
	 */
	private List<Book> getBooksFromFiles() throws IOException {

		List<Book> books = new ArrayList<Book>();
		BufferedReader br = new BufferedReader(new FileReader("data"+"/catalog.csv"));
		
		String line = "";  
		String splitBy = ",";  
		line = br.readLine();
		try   {
			while ((line = br.readLine()) != null) {
				String[] catalogInfo = line.split(splitBy);
				Book book = new Book(Integer.parseInt(catalogInfo[0]), catalogInfo[1],catalogInfo[2],catalogInfo[3],LocalDate.parse(catalogInfo[4]), Boolean.parseBoolean(catalogInfo[5]));
				books.add(book);
			}
		}
		catch (IOException e)   {
			e.printStackTrace();  
		}	
		return books;
	}
	
	/**
	 * This method will read the file user.csv in the data folder. 
	 * From it we will generate a User List. 
	 * 
	 * @return A list of objects type User of a csv file
	 */
	private List<User> getUsersFromFiles() throws IOException {
		
		// Creates variables
		List<User> users = new ArrayList<User>();

		BufferedReader br = new BufferedReader(new FileReader("data"+"/user.csv"));
		String line = "";  
		String splitBy = ",";  
		line = br.readLine();
		// Reads csv files
		try   {
			while ((line = br.readLine()) != null) {
				List<Book> books = new ArrayList<Book>();
				String[] catalogInfo = line.split(splitBy);
				if(catalogInfo.length == 2) {
					User user = new User(Integer.parseInt(catalogInfo[0]), catalogInfo[1], books);
					users.add(user);
				}
				
				else {
					// Removes curly braces
					String cleanedInput = catalogInfo[2].substring(1, catalogInfo[2].length() - 1);
					
					// Devides numbers in spaces
					String[] numberStrings = cleanedInput.split(" ");
					
					// Adds books owned to books ArrayList
					for (String numberString : numberStrings) {
						int number = Integer.parseInt(numberString);
						
						for(Book book: getBooksFromFiles()) {
							if(book.getId()==number) {
								books.add(book);
								break;
							}
						}
					}
					
					User user = new User(Integer.parseInt(catalogInfo[0]), catalogInfo[1], books);
					users.add(user);
				}
			}
		}
		catch (IOException e)   {
			e.printStackTrace();  
		}	
		return users;
	}
	
	/**
	 * Returns the catalog. The List of the books that the library owns.
	 * 
	 * @return A list of Books owned by the library
	 */
	public List<Book> getBookCatalog() {
		
		List<Book> books = new ArrayList<Book>();
		for(Book book: this.Catalog) {
			books.add(book);
		}
		return books;
	}
	
	/**
	 * Returns the List of users that the library has.
	 * 
	 * @return A list of Users the library has.
	 */
	public List<User> getUsers() {
		
		List<User> users = new ArrayList<User>();
		try {
			for(User user: getUsersFromFiles()) {
				users.add(user);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * This method adds a new book to the catalog (Book List). The id, checkout date, 
	 * and checked-out status of the new Book will be given a default value. The date 
	 * will be set to September 15, 2023, and the checked-out status will be false. 
	 * The id will be the same as the current size of the catalog + 1.
	 * 
	 * @param title - Title of the book
	 * @param author - Who authored the book
	 * @param genre - Genre of the book
	 */
	public void addBook(String title, String author, String genre) {
		Book book = new Book(this.Catalog.size()+1, title, author, genre, LocalDate.of(2023, 9, 15), false);
		this.Catalog.add(book);
		return;
	}
	
	/**
	 * 	his method searches for the book in the catalog that has the given id and
	 *  removes it.
	 * @param id - The id of the book
	 */
	public void removeBook(int id) {
		for(Book book:this.Catalog) {
			if(book.getId() == id) {
				this.Catalog.remove(book);
			}
		}
		return;
	}	
	
	/**
	 * This method checks out a book from the library if it isn’t already checked out. 
	 * It changes its checked-out status and updates the checkout date to today’s 
	 * (September 15, 2023). It returns true if it was checked out, false if it was 
	 * already checked-out or the book doesn’t exist.
	 *
	 * @param id - The id of the book
	 * @return Verification of book check-out
	 */
	public boolean checkOutBook(int id) {
		for(Book book: this.Catalog) {
			if(!book.isCheckedOut() && book.getId() == id) {
				book.setLastCheckOut(LocalDate.of(2023, 9, 15));
				book.setCheckedOut(true);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method returns a book from the library if it isn’t already returned. It 
	 * changes its checked-out status to false. It returns true if it was successfully
	 * returned, false if it wasn’t checked out or the book doesn’t exist.
	 *  
	 * @param id - The id of the book
	 * @return True if the book is returned
	 */
	public boolean returnBook(int id) {
		for(Book book: this.Catalog) {
			if(book.isCheckedOut() && book.getId() == id) {
				book.setCheckedOut(false);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method returns whether the book of the given id is available for 
	 * check-out.
	 * 
	 * @param id - The id of the book
	 * @return If the book is checked out it is true
	 */
	public boolean getBookAvailability(int id) {
		for(Book book: this.Catalog) {
			if(book.getId() == id) {
				return book.isCheckedOut();
			}
		}
		return false;
	}
	
	/**
	 * This method returns how many books of the same title are present in the 
	 * catalog
	 * 
	 * @param title - Title of the book
	 * @return The amount of books for the same title
	 */
	public int bookCount(String title) {
		int count = 0;
		for(Book book:this.Catalog) {
			if(book.getTitle().equals(title)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * This method creates a report about the library. It includes information about 
	 * the books currently in the library. The report will be stored  in a text file 
	 * called report.txt, it will be saved in the report folder.
	 * 
	 * @throws IOException if there is an error generating the report
	 */
	public void generateReport() throws IOException {
		
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		output += "Adventure\t\t\t\t\t" + String.valueOf(searchForBooks("Adventure")) + "\n";
		output += "Fiction\t\t\t\t\t\t" + String.valueOf(searchForBooks("Fiction")) + "\n";
		output += "Classics\t\t\t\t\t" + String.valueOf(searchForBooks("Classics")) + "\n";
		output += "Mystery\t\t\t\t\t\t" + String.valueOf(searchForBooks("Mystery")) + "\n";
		output += "Science Fiction\t\t\t\t\t" + String.valueOf(searchForBooks("Science Fiction")) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + String.valueOf(this.Catalog.size()) + "\n\n";
		
		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		int count = 0;
		for(Book book: this.Catalog) {
			if(book.isCheckedOut()) {
				output += book.toString()+"\n";
				count++;
			}
		}
		
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + String.valueOf(count) + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */
		float total = 0;
		for(User user: getUsers()) {
			float userTotal = 0;
			for(Book book: user.getCheckedOutList()) {
				total+= book.calculateFees();
				userTotal+= book.calculateFees();
			}
			if(!user.getCheckedOutList().isEmpty()) {
				output += user.getName() + "\t\t\t\t\t" +"$"+ String.format("%.2f", userTotal)+ "\n";
			}
		}

			
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + String.format("%.2f", total) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		File report = new File ("report.txt");
		report.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter("report/report.txt"));
		writer.write(output);
		writer.close();
		
	}
	
	/**
	 * This method will return the amount of books the library has with a particular
	 * genre.
	 * 
	 * @param genre - The genre we are looking for
	 * @return The amount of books for a genre
	 */
	public int searchForBooks(String genre) {
		int result = 0;
		for(Book book: this.Catalog) {
			if(book.getGenre().equals(genre)) {
				result++;
			}
		}
		
		return result;
	}
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
//        List<Book> result = new ArrayList<>();
//        for (Book book : getBookCatalog()) {
//            if (func.filter(book)) {
//                result.add(book);
//            }
//        }
//        return result;
		List<Book> filteredBooks = searchForBook(book -> book.getGenre().equals("Mystery"));
		return filteredBooks;
	}
	
	public List<User> searchForUsers(FilterFunction<User> func) {
		return null;
	}
	
}
