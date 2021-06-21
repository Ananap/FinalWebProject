<%--
  Created by IntelliJ IDEA.
  User: panat
  Date: 11 июн. 2021 г.
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="customtag" prefix="mytag" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="user.account" var="locale_user_account"/>
<fmt:message key="user.order.confirmation" var="locale_order_confirmation"/>

<!doctype html>
<html>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="row" style="margin-bottom: -100px">
        <div class="col-xs-8">
            <h2 class="section-headline"><span>${locale_user_account}</span></h2>
        </div>
        <div class="col-xs-4">
            <img src="static/image/logo1.jpg" class="img-responsive">
        </div>
    </div>
    <hr style="position: absolute; width: 75%; height: 6px; background-color: #1b6d85; z-index: -1; margin-top: -80px;"/>
    <mytag:image/>

    <div class="row" style="margin-top: 10px;">
        <div class="col-xs-12">
            <div class="alert alert-success">
                <h3><img style="width: 45px;" class="img-thumbnail"
                         src="static/image/greencheck.jpg">${locale_order_confirmation}</h3>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>

