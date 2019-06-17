<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Dashboard</title>
<link rel="icon" href="favicon.png" type="image/gif" >
</head>
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

      <!-- Sidebar -->
      <jsp:include page="sidenav.jsp" />  
      
      <div id="content-wrapper">

        <div class="container-fluid ">

          <!-- Breadcrumbs-->
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <a href="#">Dashboard</a>
            </li>
            <li class="breadcrumb-item active">List of Books</li>
            
          </ol>

          <!-- Icon Cards-->
          <div class="row dn">
            <div class="col-xl-3 col-sm-6 mb-3">
              <div class="card text-white bg-primary o-hidden h-100">
                <div class="card-body">
                  <div class="card-body-icon">
                    <i class="fas fa-fw fa-comments"></i>
                  </div>
                  <div class="mr-5">26 New Messages!</div>
                </div>
                <a class="card-footer text-white clearfix small z-1" href="#">
                  <span class="float-left">View Details</span>
                  <span class="float-right">
                    <i class="fas fa-angle-right"></i>
                  </span>
                </a>
              </div>
            </div>
            <div class="col-xl-3 col-sm-6 mb-3">
              <div class="card text-white bg-warning o-hidden h-100">
                <div class="card-body">
                  <div class="card-body-icon">
                    <i class="fas fa-fw fa-list"></i>
                  </div>
                  <div class="mr-5">11 New Tasks!</div>
                </div>
                <a class="card-footer text-white clearfix small z-1" href="#">
                  <span class="float-left">View Details</span>
                  <span class="float-right">
                    <i class="fas fa-angle-right"></i>
                  </span>
                </a>
              </div>
            </div>
            <div class="col-xl-3 col-sm-6 mb-3">
              <div class="card text-white bg-success o-hidden h-100">
                <div class="card-body">
                  <div class="card-body-icon">
                    <i class="fas fa-fw fa-shopping-cart"></i>
                  </div>
                  <div class="mr-5">123 New Orders!</div>
                </div>
                <a class="card-footer text-white clearfix small z-1" href="#">
                  <span class="float-left">View Details</span>
                  <span class="float-right">
                    <i class="fas fa-angle-right"></i>
                  </span>
                </a>
              </div>
            </div>
            <div class="col-xl-3 col-sm-6 mb-3">
              <div class="card text-white bg-danger o-hidden h-100">
                <div class="card-body">
                  <div class="card-body-icon">
                    <i class="fas fa-fw fa-life-ring"></i>
                  </div>
                  <div class="mr-5">13 New Tickets!</div>
                </div>
                <a class="card-footer text-white clearfix small z-1" href="#">
                  <span class="float-left">View Details</span>
                  <span class="float-right">
                    <i class="fas fa-angle-right"></i>
                  </span>
                </a>
              </div>
            </div>
          </div>

          <!-- Area Chart Example-->
          <div class="card mb-3 dn">
            <div class="card-header">
              <i class="fas fa-chart-area"></i>
              Area Chart Example</div>
            <div class="card-body">
              <canvas id="myAreaChart" width="100%" height="30"></canvas>
            </div>
            <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
          </div>
		<!-- DataTables Example -->
			<%@page import="java.sql.DriverManager"%>
			<%@page import="java.sql.ResultSet"%>
			<%@page import="java.sql.Statement"%>
			<%@page import="java.sql.Connection"%>
			<%
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
          <!-- DataTables Example -->
          <div class="card mb-3">
          	<div class="card-header">
              <i class="fas fa-table"></i>
              Book List Table</div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Book Call No</th>
                      <th>Book Name</th>
                      <th>Auther Name</th>
                      <th>Publication Name</th>
                      <th>Price</th>
                      <th>Stock</th>
                    </tr>
                  </thead>
                  <tbody>
                    <%
						try{
						connection = DriverManager.getConnection(connectionUrl, userid, password);
						statement=connection.createStatement();
						String sql ="select * from book_details";
						resultSet = statement.executeQuery(sql);
						while(resultSet.next()){
					%>
						<tr>
						<td><%=resultSet.getString("book_id") %></td>
						<td><%=resultSet.getString("book_name") %></td>
						<td><%=resultSet.getString("auther_name") %></td>
						<td><%=resultSet.getString("publication_name") %></td>
						<td><%=resultSet.getString("book_price") %></td>
						<td><%=resultSet.getString("book_stock") %></td>
						</tr>
					<%
						}
						connection.close();
						} catch (Exception e) {
						e.printStackTrace();
						}
					%>
                  </tbody>
                </table>
              </div>
            </div>
            
          </div>

        </div>
        <!-- /.container-fluid -->

        <!-- Sticky Footer -->
           <jsp:include page="footer.jsp" />  
       