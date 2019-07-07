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
</head>
<body>
    <table border="1">
        <thead>
            <tr>
                <td>First Name</td>
                <td>Last Name</td>
                <td>Tel</td>
            </tr>
        </thead>
        <tbody>
            <% PrintWriter printWriter = response.getWriter(); %>
            <% for(Client client : (List<Client>) request.getAttribute("userlist")){ %>
            <tr>
                <td><% out.write(client.getFirstname()); %></td>
                <td><% out.write(client.getLastname()); %></td>
                <td><% out.write(client.getTel()); %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
