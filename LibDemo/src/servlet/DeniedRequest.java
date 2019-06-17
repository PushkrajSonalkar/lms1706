package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DB;

@WebServlet("/DeniedRequest")
public class DeniedRequest extends HttpServlet
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		PrintWriter out = resp.getWriter();
		String book_id = req.getParameter("book_id");
		String emp_id = req.getParameter("emp_id");
		
		if(bookDenied(book_id,emp_id)) 
		{
			out.println("<script type=\"text/javascript\">");
            out.println("alert('Book Request Denied');");
            out.println("location='requestedbooks.jsp';");
            out.println("</script>"); 
		}
	}

	private boolean bookDenied(String book_id, String emp_id) 
	{
		boolean sts=false;
		try
		{
			Connection con = DB.getCon();
			
			PreparedStatement ps = con.prepareStatement("insert into book_request_denied values(?,?)");
			ps.setString(1, emp_id);
			ps.setString(2, book_id);
			ps.executeUpdate();

			String sql="delete from book_request where book_id=? and emp_id=?";
			PreparedStatement ps3= con.prepareStatement(sql);
			ps3.setString(1, book_id);
			ps3.setString(2, emp_id);
			ps3.executeUpdate();
			sts=true;
			con.close();
		}catch(Exception e1)
		{
			e1.printStackTrace();
		}
		return sts;
	}
}
