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

@WebServlet("/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet
{
	int stock=0;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		PrintWriter out = resp.getWriter();
		String book_id = req.getParameter("book_id");
		String emp_id = req.getParameter("emp_id");
		if(ReturnBook(book_id,emp_id)) 
		{
			out.println("<script type=\"text/javascript\">");
	        out.println("alert('Book Returned Successful');");
	        out.println("location='user_dashboard.jsp';");
	        out.println("</script>"); 
		}
			
	}
	
	public boolean ReturnBook(String book_id,String emp_id) 
	{
		boolean sts=false;
		try
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date d = new Date();
			Connection con = DB.getCon();
			
			PreparedStatement ps = con.prepareStatement("update book_issue_return set issue_sts=0 , issue_date=null , r_status=1 , return_date= ? where emp_id= ? and book_id = ?");
			ps.setString(1, d.toString());
			ps.setString(2, emp_id);
			ps.setString(3, book_id);
			ps.executeUpdate();
			
			PreparedStatement ps1 = con.prepareStatement("select book_stock from book_details where book_id=?");
			ps1.setString(1, book_id);
			ResultSet rs= ps1.executeQuery();
			if(rs.next()) 
			{
				stock=rs.getInt("book_stock");
			}
			stock=stock+1;
			PreparedStatement ps2 = con.prepareStatement("update book_details set book_stock ="+ stock +"where book_id=?");
			ps2.setString(1, book_id);
			ps2.executeUpdate();
			sts=true;
			con.close();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		return sts;
	}
}
