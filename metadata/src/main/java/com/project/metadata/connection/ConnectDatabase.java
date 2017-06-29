package com.project.metadata.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectDatabase {
	
	

	Properties properties = new Properties();
	InputStream inputStream = null;
	String driver = null;
	String url = null;
	String username = null;
	String password = null;
	
	public Connection getMySqlConnection(){
		Connection conn =null;
		try {
			 inputStream = new FileInputStream(new File("./src/main/resources/database.properties"));
			 properties.load(inputStream);
			 driver = properties.getProperty("db.mysql.driver");
			 url = properties.getProperty("db.mysql.server.url");
			 username = properties.getProperty("db.mysql.server.username");
			 password = properties.getProperty("db.mysql.server.password");
			 
		 Class.forName(driver);
		  conn = DriverManager.getConnection(url, username, password);
		 return conn;			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	
}
