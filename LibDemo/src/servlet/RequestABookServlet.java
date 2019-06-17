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

@WebServlet("/RequestABookServlet")
public class RequestABookServlet extends HttpServlet
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		PrintWriter out = resp.getWriter();
		String book_id = req.getParameter("book_id");
	    HttpSession session=req.getSession(false);  
        String n=(String)session.getAttribute("email");
		String emp_id=getEmpId(n);
		if(checkStatus(book_id,emp_id)) 
		{

			if(RequestBook(book_id,emp_id))
			{
				out.println("<script type=\"text/javascript\">");
	            out.println("alert('Book Requested Successful');");
	            out.println("location='user_dashboard.jsp';");
	            out.println("</script>"); 				
			}
		}
		else
		{

			out.println("<script type=\"text/javascript\">");
            out.println("alert('You already Requested for this Book');");
            out.println("location='RequestABook.jsp';");
            out.println("</script>"); 
		}
	}

	/*
	 * Get Emp Id
	 */
	
	private String getEmpId(String email_id) 
	{
		String i=null;
		try
		{
			Connection con = DB.getCon();
			PreparedStatement ps = con.prepareStatement("select emp_id from book_user where email=? ");
			ps.setString(1, email_id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) 
			{
				i=rs.getString("emp_id");
			}
			con.close();
		}catch(Exception e1)
		{
			e1.printStackTrace();
		}	
		return i;
	}
//	check status
	public Boolean checkStatus(String book_id,String emp_id) 
	{
		Boolean sts=true;
		try
		{
			Connection con = DB.getCon();
			PreparedStatement ps = con.prepareStatement("select * from book_request where book_id=? and emp_id=?");
			ps.setString(1, book_id);
			ps.setString(2, emp_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
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
	
	//request a book
	public boolean RequestBook(String book_id, String i) 
	{
		boolean sts=false;
		try
		{
			Connection con = DB.getCon();
				PreparedStatement ps = con.prepareStatement("insert into book_request values (?,?,?)");
				ps.setString(1, book_id);
				ps.setString(2, i);
				ps.setLong(3, 1);
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
