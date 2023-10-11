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

public class LibraryCatalog {
	
	private List<Book> Catalog = new ArrayList<>();
	private List<User> Users = new ArrayList<>();
	
	public LibraryCatalog() throws IOException {
		this.Catalog = getBooksFromFiles();
		this.Users = getUsersFromFiles();
		
	}
	private List<Book> getBooksFromFiles() throws IOException {

		List<Book> books = new ArrayList<Book>();
		BufferedReader br = new BufferedReader(new FileReader("data"+"/catalog.csv"));
		
		String line = "";  
		String splitBy = ",";  
		line = br.readLine();
//		String[] tempCI = line.split(splitBy);
//		Book book1 = new Book(0, tempCI[1],tempCI[2],tempCI[3],LocalDate.now(), true);
//		books.add(book1);
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
	
	public List<Book> getBookCatalog() {
		
		List<Book> books = new ArrayList<Book>();
		for(Book book: this.Catalog) {
			books.add(book);
		}
		return books;
	}
	
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
	public void addBook(String title, String author, String genre) {
		Book book = new Book(this.Catalog.size()+1, title, author, genre, LocalDate.of(2023, 9, 15), false);
		this.Catalog.add(book);
		return;
	}
	public void removeBook(int id) {
		for(Book book:this.Catalog) {
			if(book.getId() == id) {
				this.Catalog.remove(book);
			}
		}
		return;
	}	
	
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
	public boolean returnBook(int id) {
		for(Book book: this.Catalog) {
			if(book.isCheckedOut() && book.getId() == id) {
				book.setCheckedOut(false);
				return true;
			}
		}
		return false;
	}
	
	public boolean getBookAvailability(int id) {
		
//		for(Book book: this.Catalog) {
//			if(!book.isCheckedOut() && book.getId() == id) {
//				return true;
//			}
//		}
//		return false;
		for(Book book: this.Catalog) {
			if(book.getId() == id) {
				return book.isCheckedOut();
			}
		}
		return false;
	}
	public int bookCount(String title) {
		int count = 0;
		for(Book book:this.Catalog) {
			if(book.getTitle().equals(title)) {
				count++;
			}
		}
		return count;
	}
	
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
