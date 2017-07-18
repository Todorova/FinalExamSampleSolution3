package edu.pragmatic.exam.utils;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import edu.pragmatic.exam.model.*;
import edu.pragmatic.exam.model.Dog.State;

public class Utils {
	private static final String SEPARATOR = ",";
	public static List<Dog> load() throws FileNotFoundException, IOException {

		List<Dog> dogs = new ArrayList<>();

		Connection conn;
		try {
			conn = Database.getConnection();

			String query = "SELECT * FROM Animals";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt("id");
				String Date = rs.getString("Date");
				String Name = rs.getString("Name");
				String Breed = rs.getString("Breed");
				String Color = rs.getString("Color");
				boolean Sex = rs.getBoolean("Sex");
				State State = rs.getString("State").equals("LOST") ? Dog.State.LOST : Dog.State.FOUND;
				String DateCreated = rs.getString("DateCreated");

				Dog dog = new Dog();
				dog.setId(id);
				dog.setDate(Date);
				dog.setName(Name);
				dog.setBreed(Breed);
				dog.setColor(Color);
				dog.setMale(Sex);
				dog.setState(State);
				dog.setDateCreated(DateCreated);
				dogs.add(dog);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
return dogs;

		}
	}
	
	// -----------

	public static void save(List<Dog> dogs) throws IOException {
		try {
			Connection conn = Database.getConnection();
			String query = "insert into Animals (Date, Name, Breed, Color, Sex, State, DateCreated)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
			for (Dog dog : dogs) {

				preparedStmt.setDate(1, new java.sql.Date(format.parse(dog.getDate()).getTime()));
				preparedStmt.setString(2, dog.getName());
				preparedStmt.setString(3, dog.getBreed());
				preparedStmt.setString(4, dog.getColor());
				preparedStmt.setBoolean(5, dog.isMale());
				preparedStmt.setString(6, dog.getState().toString());
				preparedStmt.setDate(7, new java.sql.Date(format.parse(dog.getDateCreated()).getTime()));
				preparedStmt.execute();
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dogs == null) {
			return;
		}
	}
	
	


	    public static List<Dog> loadFile(String pathToFile) throws FileNotFoundException, IOException {

	        List<Dog> entries = new ArrayList<>();

	        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(pathToFile));Scanner sc = new Scanner(input)) {

	            while (sc.hasNextLine()) {
	                String line = sc.nextLine();
	                Dog e = createEntry(line);
	                entries.add(e);
	            }
	        }
	        return entries;
	    }

	    private static Dog createEntry(String text) {

	        String[] elements = text.split(SEPARATOR);
//	        Dog e = new Dog(elements[0], elements[1], elements[2], elements[3], elements[4], elements[5], elements[6]);
	        Dog dog = new Dog();
    		dog.setFields(elements);

	        return dog;
	    }
	    
}
