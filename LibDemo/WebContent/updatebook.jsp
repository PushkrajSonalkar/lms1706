<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin View Book</title>
<link rel="icon" href="favicon.png" type="image/gif" >

<!-- Bootstrap core CSS-->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Page level plugin CSS-->
    <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin.css" rel="stylesheet">

  </head>

  <body id="page-top">

<%
	response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
  if(session.getAttribute("email")==null){
    response.sendRedirect("index.jsp");
  }
%>
     <jsp:include page="nav.jsp" />  
    

    <div id="wrapper">

     
      <jsp:include page="sidenav.jsp" /> 
      <div id="content-wrapper">

        <div class="container-fluid">

          <!-- Breadcrumbs-->
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <a href="dashboard.jsp">Dashboard</a>
            </li>
            <li class="breadcrumb-item active">Update Books</li>
          </ol>
<!-- 			Update Book Form -->
			<%@page import="java.sql.DriverManager"%>
			<%@page import="java.sql.ResultSet"%>
			<%@page import="java.sql.Statement"%>
			<%@page import="java.sql.Connection"%>
			<%
				String book_id = request.getParameter("book_id");
				String driver = "oracle.jdbc.driver.OracleDriver";
				String connectionUrl = "jdbc:oracle:thin:@localhost:1521:xe";
				String userid = "system";
				String password = "root123";
				try {
				Class.forName(driver);
				} catch (ClassNotFoundException e) {
				e.printStackTrace();
				}
				Connection connection = null;
				Statement statement = null;
				ResultSet resultSet = null;
			%>
			
      	  <div class="card card-register mx-auto mt-5">
            <div class="card-header">Update Book</div>
        		<div class="card-body">
        		<form method="post" action="UpdateBookServlet">
              	<%
						try{
						connection = DriverManager.getConnection(connectionUrl, userid, password);
						statement=connection.createStatement();
						String sql ="select * from book_details where book_id ='"+ book_id+"'";
						resultSet = statement.executeQuery(sql);
						while(resultSet.next()){
					%>
                <div class="form-row">
                  <div class="col-md-6">
                    <div class="form-group">
                            <div class="form-label-group">
                              <input type="text" value=<%=resultSet.getString("book_id") %> class="form-control" name="bid" placeholder="Book Call Id" required="required" autofocus="autofocus" READONLY>
                              <label for="firstName">Book Call Id</label>
                            </div>
                      </div>
                  </div>
                  <div class="col-md-6">
                            <div class="form-group">
                            <div class="form-label-group">
                              <input type="text" value=<%=resultSet.getString("book_name") %> class="form-control" name="bname"  title="Book Name must be valid" pattern="[a-zA-z0-9?': ]*" maxlength=55required="required" autofocus="autofocus">
                              <label for="firstName">Book Name</label>
                            </div>
                       </div>
                  </div>
                </div>
                <div class="form-row">
                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="form-label-group">
                        <input type="text" value=<%=resultSet.getString("auther_name") %> class="form-control" name="auname" title="Auther Name must not be Numeric or Any special Symbol" pattern="[a-zA-z ]*" maxlength=55 required="required" autofocus="autofocus">
                        <label for="firstName">Auther Name</label>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="form-label-group">
                          <input type="text" value=<%=resultSet.getString("publication_name") %> class="form-control"  name="pname" title="Publication Name must not be Numeric or Any special Symbol" pattern="[a-zA-z ]*" maxlength=55 required="required" autofocus="autofocus">
                          <label for="firstName">Publication Name</label>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <div class="form-row">
                    <div class="col-md-6">
                      <div class="form-label-group">
                        <input type="text" value=<%=resultSet.getString("book_price") %> class="form-control" name="price" title="Price must not be Alphabates or Any special Symbol" pattern="[0-9.]*" maxlength=7 required="required">
                        <label for="inputPassword">Book Price</label>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-label-group">
                        <input type="text" value=<%=resultSet.getString("book_stock") %> class="form-control" name="bstock" title="Book Stock must be in Numeric " pattern="[0-9]*" maxlength=5 required="required">
                        <label for="confirmPassword">Book Stock</label>
                      </div>
                    </div>
                  </div>
                </div>
                <input type="submit" class="btn btn-primary btn-block" value="Update Book">
                <%
						}
						connection.close();
						} catch (Exception e) {
						e.printStackTrace();
						}
					%>
              </form>
            </div>
          </div>
          </div>
        </br>
        <!-- /.container-fluid -->
		<jsp:include page="footer.jsp" /> 