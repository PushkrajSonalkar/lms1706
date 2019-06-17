package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DB;


@WebServlet("/DeniedUserApproval")
public class DeniedUserApproval extends HttpServlet
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		PrintWriter out = resp.getWriter();
		String book_id = req.getParameter("emp_id");
		if(userDenied(book_id)) 
		{
			out.println("<script type=\"text/javascript\">");
            out.println("alert('User Denied');");
            out.println("location='userapproval.jsp';");
            out.println("</script>"); 
		}
	}
	
	private boolean userDenied(String book_id) 
	{
		boolean sts=false;
		try
		{
			Connection con = DB.getCon();
			
			PreparedStatement ps = con.prepareStatement("update book_user set STATUS=0 where emp_id=?");
			ps.setString(1, book_id);
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
