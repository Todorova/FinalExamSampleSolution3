package edu.pragmatic.exam.model;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

	public static Connection getConnection() throws Exception{
		try {
					
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://georgialexandrov.eu:3306/animals";
			String username = "ana";
			String password = "ngoxl7oZd9rBbIq!";
			Class.forName(driver);
			
			
//			Root User: ana
//			Root Password: ngoxl7oZd9rBbIq!
//			Database Name: animals
//			Connection URL: mysql://georgialexandrov.eu:3306/
						
			Connection conn = DriverManager.getConnection(url,username, password);
			System.out.println("Connected");
			return conn;			
		}catch(Exception e){System.out.println(e);}
		return null;
	}
}
