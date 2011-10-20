package org.whisked.whiskedit;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Structure {
	
	public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	public String protocol = "jdbc:derby:";
	private Connection connection;
	private String database = "Mp3Base";
	
	public static void main(String[] args) {
		Structure str = new Structure();
	}
	
	public Structure() {
		start();
		
		File file = new File("/Users/dennis/Desktop/C22");
		
		File[] farray = file.listFiles();
		
		for(int i=0;i<farray.length;i++) {
			if(farray[i].getName().endsWith("mp3") || farray[i].getName().endsWith("Mp3"))
				System.out.println(farray[i].getName());
		}
		
		stop();
	}
	
	public void getTableData(String qry) throws SQLException {
		  ResultSet rs=connection.createStatement().executeQuery(qry);
		  while (rs.next()){
		      String title=rs.getString("username");
		      String last=rs.getString("password");
		      System.out.println("Title: " + title + "  Password: " + last);
		  }
		  rs.close();
	}
	
	public void getMetaData() {
		try {
			DatabaseMetaData dbmd = connection.getMetaData();
			String[] types = {"TABLE"};
		    ResultSet resultSet = dbmd.getTables(null, null, "%", types);
		    // Get the table names
		    while (resultSet.next()) {
		        // Get the table name
		        String tableName = resultSet.getString(3);

		        // Get the table's catalog and schema names (if any)
		        String tableCatalog = resultSet.getString(1);
		        String tableSchema = resultSet.getString(2);
		    }		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean start() {
		try {
			Class.forName(driver).newInstance();   
			if(new File(database).exists()) {
				connection = DriverManager.getConnection("jdbc:derby:" + database);
			} else {
		        connection = DriverManager.getConnection("jdbc:derby:" + database + ";create=true");
		    }
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
		    try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		try {
			DriverManager.getConnection("jdbc:derby:" + database + ";shutdown=true");
		} catch(Exception e) {
		    e.printStackTrace();
		}    
	}
}
