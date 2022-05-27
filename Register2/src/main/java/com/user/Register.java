package com.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class Register extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String name = req.getParameter("user_name");
		String password= req.getParameter("user_password");
		String email = req.getParameter("user_email");
		
		Part part= req.getPart("image");
		String filename = part.getSubmittedFileName();
		
		PrintWriter out = resp.getWriter();
//		out.println(filename);
		String query = "insert into registersite.user(name,password,email,imageName) values(?,?,?,?)";
		
		try {
			
			Thread.sleep(3000);
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=megh&password=root");
			PreparedStatement pstmt= con.prepareStatement(query);
			pstmt.setString(1, name); 	
			pstmt.setString(2, password); 	
			pstmt.setString(3, email); 	
			pstmt.setString(4, filename); 	
			
			pstmt.executeUpdate();
			
			
			//file upload to the server start
			InputStream is = part.getInputStream();
			byte[] data = new byte[is.available()];
			is.read(data);
			
			String path = req.getRealPath("/")+"images"+File.separator+filename;
			
			out.println(path);
			
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(data);
			fos.close();
			
			//file upload to the server end
			
			out.println("<h1>Thank you for Signing In</h1>");
			
			
		} catch (ClassNotFoundException | SQLException | InterruptedException e) {
		
			e.printStackTrace();
		}
		
		
	}
	
	
	
	

}
