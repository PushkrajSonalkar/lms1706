<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Return A Book</title>
<meta charset="ISO-8859-1">
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
  String email=session.getAttribute("email").toString();
  String emp_id=session.getAttribute("emp_id").toString();

%>
          <jsp:include page="user_nav.jsp" />  
    
    <div id="wrapper">

      <!-- Sidebar -->
      <jsp:include page="usersidenav.jsp" />  
      

      <div id="content-wrapper">

        <div class="container-fluid">

          <!-- Breadcrumbs-->
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <a href="dashboard.jsp">Dashboard</a>
            </li>
            <li class="breadcrumb-item active">Return a Books</li>
          </ol>
			<!-- DataTables Example -->
			<%@page import="java.sql.DriverManager"%>
			<%@page import="java.sql.ResultSet"%>
			<%@page import="java.sql.Statement"%>
			<%@page import="java.sql.Connection"%>
			<%
				int id=1;
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
				ResultSet resultSet1=null;
				Statement statement1 = null;
			%>
			
          <div class="card mb-3">
            <div class="card-header">
              <i class="fas fa-table"></i>
              Book List Table</div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Sr. No</th>
                      <th>Book Name</th>
                      <th>Return</th>
                    </tr>
                  </thead>
                  <tbody>
                    <%
						try{
							
							connection = DriverManager.getConnection(connectionUrl, userid, password);
							statement=connection.createStatement();
							String sql ="select * from book_issue_return where EMP_ID = '"+ emp_id +"' and issue_sts = 1";
							resultSet = statement.executeQuery(sql);
							while(resultSet.next()){
						%>
						<tr>
						<td><%out.println(id++);%></td>
						<td><%=resultSet.getString("book_id") %></td>
						<td><a class="btn btn-success" href="ReturnBookServlet?book_id=<%=resultSet.getString("book_id")%>&emp_id=<%=resultSet.getString("emp_id")%>">Return</a></td>
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
          

        <!-- /.container-fluid -->
		<jsp:include page="userfooter.jsp" />  
		