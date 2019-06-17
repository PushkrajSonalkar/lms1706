package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DB;

@WebServlet("/ApproveRequest")
public class ApproveRequest extends HttpServlet
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		PrintWriter out = resp.getWriter();
		String book_id = req.getParameter("book_id");
		String emp_id = req.getParameter("emp_id");
		
		if(bookApprove(book_id,emp_id)) 
		{
			out.println("<script type=\"text/javascript\">");
            out.println("alert('Book Request Approved Successful');");
            out.println("location='requestedbooks.jsp';");
            out.println("</script>"); 
		}
	}
	public boolean bookApprove(String book_id,String emp_id) 
	{
		boolean sts=false;
		try
		{
			Connection con = DB.getCon();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			int stock=0;
			
			PreparedStatement ps1 = con.prepareStatement("select book_stock from book_details where book_id=?");
			ps1.setString(1, book_id);
			ResultSet rs= ps1.executeQuery();
			if(rs.next()) {
				stock=rs.getInt("book_stock");
			}
			stock=stock-1;
			PreparedStatement ps2 = con.prepareStatement("update book_details set book_stock ="+ stock +"where book_id=?");
			ps2.setString(1, book_id);
			ps2.executeUpdate();
			PreparedStatement ps = con.prepareStatement("insert into book_issue_return (emp_id,book_id,issue_sts,issue_date) values(?,?,?,?)");
			ps.setString(1, emp_id);
			ps.setString(2, book_id);
			ps.setInt(3, 1);
			ps.setString(4,date.toString());
			ps.executeUpdate();
			sts=true;
			String sql="delete from book_request where book_id=? and emp_id=?";
			PreparedStatement ps3= con.prepareStatement(sql);
			ps3.setString(1, book_id);
			ps3.setString(2, emp_id);
			ps3.executeUpdate();
			con.close();
		}catch(Exception e1)
		{
			e1.printStackTrace();
		}
		return sts;
	}
}
