package edu.pragmatic.exam.model;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

	public static Connection getConnection() throws Exception{
		try {
					
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11185131";
			String username = "sql11185131";
			String password = "Qg4RYfyTHB";
			Class.forName(driver);
						
			Connection conn = DriverManager.getConnection(url,username, password);
			System.out.println("Connected");
			return conn;			
		}catch(Exception e){System.out.println(e);}
		return null;
	}
}
