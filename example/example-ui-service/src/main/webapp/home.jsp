<%@ page import="com.khtm.circuitbreaker.example.ui.model.Client" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: a.khatamidoost
  Date: 7/7/2019
  Time: 11:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Client List</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="css/bootstrap.min.css"%>
    </style>
    <script>
        <%@include file="js/popper.min.js"%>
    </script>
    <script>
        <%@include file="js/jquery.min.js"%>
    </script>
    <script>
        <%@include file="js/bootstrap.min.js"%>
    </script>
</head>
<body>
<div class="container">
    <table class="table table-hover table-striped">
        <thead class="thead-dark">
        <tr>
            <td>First Name</td>
            <td>Last Name</td>
            <td>Tel</td>
        </tr>
        </thead>
        <tbody>
        <% PrintWriter printWriter = response.getWriter(); %>
        <% for (Client client : (List<Client>) request.getAttribute("userlist")) { %>
        <tr>
            <td><% out.write(client.getFirstname()); %></td>
            <td><% out.write(client.getLastname()); %></td>
            <td><% out.write(client.getTel()); %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
