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
@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet
{
	int id=getCount();
	public int getCount()
	{
		int a=0;
		try
		{
			Connection con = DB.getCon();
			PreparedStatement ps = con.prepareStatement("select count(book_id) from book_details");
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
		id=book_id(id);
		
		String book_id = "CLBN_"+id;
		String book_name = req.getParameter("bname");
		String auther_name = req.getParameter("auname");
		String publication_name = req.getParameter("pname");
		String book_price = req.getParameter("price");
		String book_stock  = req.getParameter("bstock");
		if(AddBook(book_id,book_name,auther_name,publication_name,book_price,book_stock)) 
		{
			out.println("<script type=\"text/javascript\">");
            out.println("alert('Book Added Successful');");
            out.println("location='viewbook.jsp';");
            out.println("</script>"); 
		}
		
	}
	public static boolean AddBook(String book_id,String book_name,String auther_name,String publication_name,String book_price,String book_stock) 
	{
		boolean sts=false;
		try
		{
			Connection con = DB.getCon();
			
			PreparedStatement ps = con.prepareStatement("insert into book_details values(?,?,?,?,?,?)");
			ps.setString(1, book_id);
			ps.setString(2, book_name);
			ps.setString(3, auther_name);
			ps.setString(4, publication_name);
			ps.setString(5, book_price);
			ps.setString(6, book_stock);
			
			ps.executeUpdate();
			sts=true;
			con.close();
		}catch(Exception e1)
		{
			e1.printStackTrace();
		}
		return sts;
	}
	public static int book_id(int i) 
	{
		int id=i;
			id=id+1;
		return id;
	}
}


