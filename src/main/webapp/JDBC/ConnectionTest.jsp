<%@ page import="common.DBConnPool" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-11-17
  Time: 오후 8:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<html>
<head>
  <title>JDBC</title>
</head>
<body>
  <h2>커넥션 풀 테스트</h2>
  <%
    DBConnPool pool = new DBConnPool();
    pool.close();
  %>
</body>
</html>
