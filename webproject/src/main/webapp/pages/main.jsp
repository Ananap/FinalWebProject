<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: panat
  Date: 22 мар. 2021 г.
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<br/>
<table style="margin: auto" border = "1" width = "50%">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Age</th>
    </tr>
    <c:forEach var="user" items="${userList}" varStatus="status">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.age}"/></td>
        </tr>
    </c:forEach>
</table>
<div style="margin: auto; width: 50%;">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="go_to_original_command"/>
        <input type="submit" value="Original Table">
    </form>
    <br/>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="sort_user_command"/>
        <input type="submit" value="Sort By name">
    </form>
    <br/>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="age_criteria_command"/>
        <div class="input-group select-form">
            <input type="number" step="1" min="1" max

                    ="100"
                   class="form-control" name="ageFrom"
                   placeholder="Age From"/>
            <input type="number" step="0.1" min="1" max="100"
                   class="form-control" name="ageTo"
                   placeholder="Age To"/>
        </div>
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>
