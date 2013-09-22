package com.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.demo.model.Circle;

// This class is resonsible for talking to the database
// and getting the value.

public class JdbcDaoImpl {

	public Circle getCircle(int circleId){
		
		Connection connection = null;
		try{
			
			// Initializing the client driver:
			String driver = "org.apache.derby.jdbc.ClientDriver";
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection("jdbc:derby://localhost:1527/db");
			
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM circle where id = ?");
			ps.setInt(1, circleId);
			
			Circle circle = null;
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				circle = new Circle(circleId, rs.getString("name"));
			}
			rs.close();
			ps.close();
			return circle;
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			try {
				connection.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
}
