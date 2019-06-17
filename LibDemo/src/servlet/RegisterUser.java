package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DB;



@WebServlet("/RegiUser")
public class RegisterUser extends HttpServlet
{
	int id=getCount();
	public int getCount()
	{
		int a=0;
		try
		{
			Connection con = DB.getCon();
			PreparedStatement ps = con.prepareStatement("select count(emp_id) from book_user");
			ResultSet rs=ps.executeQuery();
			if(rs.next()) 
			{
				a=rs.getInt(1);
			}
			con.close();
		}catch(Exception e1)
		{
			e1.printStackTrace();
		}
		
		return a;
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		PrintWriter out = resp.getWriter();
		id=getGeneratedEmpID(id);
		String empid="CEMP_"+id;
		String user = req.getParameter("uname");
		String email= req.getParameter("uemail");
		String psw = req.getParameter("psw");
		String cpsw = req.getParameter("cpsw");
		if(cpsw.equals(psw)) 
		{
			
				if(Register(empid,user,email,psw)) {
					out.println("<script type=\"text/javascript\">");
	                out.println("alert('Registration Successful');");
	                out.println("location='index.jsp';");
	                out.println("</script>");                         
				}
				else
				{
					out.println("<script type=\"text/javascript\">");
	                out.println("alert('Email Id already present');");
	                out.println("location='index.jsp';");
	                out.println("</script>");  				
				}
			
		}
		else {
			out.println("<script type=\"text/javascript\">");
            out.println("alert('Password and Confirm Password must be same');");
            out.println("location='index.jsp';");
            out.println("</script>");  
			resp.sendRedirect("RegisterUser.jsp");
			
		}
		
	}
	
	public boolean checkEmail(String email) 
	{
		Boolean sts=true;
		try
		{
			Connection con = DB.getCon();
			PreparedStatement ps = con.prepareStatement("select * from book_request where email=?");
			ps.setString(1, email);
			ResultSet rs =ps.executeQuery();
			if(rs.next()) 
			{
				sts=false;
			}
			con.close();
		}catch(Exception e1)
		{
			e1.printStackTrace();
		}
		return sts;
	}
	private int getGeneratedEmpID(int i) 
	{
		int id=i;
			id=id+1;
	return id;
	}
	public boolean Register(String i,String u, String email,String p) 
	{
		boolean sts=false;
		try
		{
			Connection con = DB.getCon();
			
			PreparedStatement ps = con.prepareStatement("insert into book_user values (?,?,?,?,?);");
			ps.setString(1, i);
			ps.setString(2, u);
			ps.setString(3, email);
			ps.setString(4, p);
			ps.setLong(5, 0);
			ps.executeUpdate();
			sts=true;
			con.close();
		}catch(Exception e1)
		{
			e1.printStackTrace();
		}
		return sts;
	}
}
