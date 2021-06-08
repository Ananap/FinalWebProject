<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="common.contact" var="locale_common_contact"/>

<footer class="footer custom-footer">
    <div class="container">
        <div class="col-md-5">
            <h3 style="color: lightgray" class="text-muted">&copy; MY FlowerShop</h3>
        </div>
        <div class="col-md-7">
            <h5 style="color: cornsilk">${locale_common_contact}</h5>
            <span style="color: cornsilk">+375(33)6109879</span>
        </div>
    </div>
</footer>