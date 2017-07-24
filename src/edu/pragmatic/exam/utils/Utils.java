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
	private static final String MALE = "M";
	private static final String FEMALE = "F";
	
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
				String Color = rs.getString("Color");
				String Breed = rs.getString("Breed");
				boolean Sex = rs.getBoolean("Sex");
				State State = rs.getString("State").equals("LOST") ? Dog.State.LOST : Dog.State.FOUND;
				String Name = rs.getString("Name");
				String DateCreated = rs.getString("DateCreated");

				
				Dog dog = new Dog();
				dog.setId(id);
				dog.setDate(Date);
				dog.setColor(Color);
				dog.setBreed(Breed);
				dog.setMale(Sex);
				dog.setState(State);
				dog.setName(Name);
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
			String query = "insert into Animals (Date, Color, Breed, Sex, State, Name, DateCreated)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
			for (Dog dog : dogs) {

				preparedStmt.setDate(1, new java.sql.Date(format.parse(dog.getDate()).getTime()));
				preparedStmt.setString(2, dog.getColor());
				preparedStmt.setString(3, dog.getBreed());
				preparedStmt.setBoolean(4, dog.isMale());
				preparedStmt.setString(5, dog.getState().toString());
				preparedStmt.setString(6, dog.getName());
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
	        Dog dog = new Dog();
    		dog.setFields(elements);

	        return dog;
	    }
	    
	    public static void saveFile(File f, List<Dog> dogs) throws IOException {
			
			try (FileWriter fileWriter = new FileWriter(f);
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
					PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
				
				for(Dog a : dogs) {
					String line = createString(a);
					printWriter.println(line);
				}
			}
		}
	    
	    private static String createString(Dog a) {
			//Date,Color,Breed,Sex,State,Name,DateCreated
			StringBuilder builder = new StringBuilder();
			builder.append(a.getDate());
			builder.append(SEPARATOR);
			builder.append(a.getColor());
			builder.append(SEPARATOR);
			builder.append(a.getBreed());
			builder.append(SEPARATOR);
			builder.append(a.isMale() ? MALE : FEMALE);
			builder.append(SEPARATOR);
			builder.append(a.getState());
			builder.append(SEPARATOR);
			builder.append(a.getName());
			builder.append(SEPARATOR);
			builder.append(a.getDateCreated());
			
			return builder.toString();
		}
	    
}
