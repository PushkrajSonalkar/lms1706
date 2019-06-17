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
import javax.servlet.http.HttpSession;

import database.DB;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet
{
	String emp_id=null;
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String email = req.getParameter("email");
		String psw = req.getParameter("psw");
		
		PrintWriter out = resp.getWriter();
		if(authenticate(email, psw)) 
		{
			HttpSession session= req.getSession();
			session.setAttribute("email", email);
			session.setAttribute("emp_id", emp_id);
			resp.sendRedirect("user_dashboard.jsp");
		}
		else
		{
			  out.println("<script type=\"text/javascript\">");
			  out.println("alert('Your Accout is not Approved or Check your Credentials');");
			  out.println("location='index.jsp';"); out.println("</script>");
		}
	}
	public boolean authenticate(String email, String pass)
	{
		boolean status = false;
		try
		{
			Connection con = DB.getCon();
			PreparedStatement ps = con.prepareStatement("select * from book_user where email=? and password=? and status=1");
			ps.setString(1, email);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				emp_id=rs.getString("emp_id");
				status = true;
			}
			
			con.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
}
