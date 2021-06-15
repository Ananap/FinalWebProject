<%--
  Created by IntelliJ IDEA.
  User: panat
  Date: 14 июн. 2021 г.
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="main.category" var="locale_main_category"/>
<fmt:message key="main.soil" var="locale_main_soil"/>
<fmt:message key="admin.flower.watering" var="locale_flower_watering"/>
<fmt:message key="admin.country.name" var="locale_country_name"/>
<fmt:message key="admin.light.name" var="locale_light_name"/>
<fmt:message key="main.name" var="locale_main_name"/>
<fmt:message key="main.description" var="locale_main_description"/>
<fmt:message key="admin.price.name" var="locale_price_name"/>
<fmt:message key="admin.storage.name" var="locale_storage_name"/>
<fmt:message key="admin.choose.operation" var="locale_choose_operation"/>
<fmt:message key="admin.operation.delete" var="locale_operation_delete"/>

<!doctype html>
<html lang="en">
<link rel="stylesheet" href="../../static/css/flower.css">
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <br/>
    <!--Table Header-->
    <div class="table-responsive">
        <table class="table table-bordered table-hover table-striped">
            <thead>
            <tr>
                <th>${locale_main_name}</th>
                <th>${locale_main_category}</th>
                <th>${locale_main_soil}</th>
                <th>${locale_flower_watering}</th>
                <th>${locale_country_name}</th>
                <th>${locale_light_name}</th>
                <th>${locale_main_description}</th>
                <th>${locale_price_name}</th>
                <th>${locale_storage_name}</th>
                <th>${locale_choose_operation}</th>
            </tr>
            </thead>
            <!--Table body-->
            <tbody>
            <c:forEach items="${flowerList}" var="flower">
                <tr>
                    <td>
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="go_to_item_info_page_command"/>
                            <input type="hidden" name="flowerId" value="${flower.id}">
                            <button type="submit" class="btn btn-link">${flower.name}</button>
                        </form>
                    </td>
                    <td>${flower.flowerType.description}</td>
                    <c:if test="${flower.soil == 'PODZOLIC'}">
                        <td>Подзолистая</td>
                    </c:if>
                    <c:if test="${flower.soil == 'SODPODZOLIC'}">
                        <td>Дерново-подзолистая</td>
                    </c:if>
                    <c:if test="${flower.soil == 'UNPAVED'}">
                        <td>Грунтовая</td>
                    </c:if>
                    <td>${flower.watering} Мл в неделю</td>
                    <td>${flower.originCountry}</td>
                    <c:if test="${flower.light}">
                        <td>светолюбивое</td>
                    </c:if>
                    <c:if test="${!flower.light}">
                        <td>темнолюбивое</td>
                    </c:if>
                    <td>${flower.description}</td>
                    <td>${flower.price}</td>
                    <td>${flower.storage.count}</td>
                    <td>
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="delete_item_command"/>
                            <input type="hidden" name="flowerId" value="${flower.id}"/>
                            <button type="submit" class="btn btn-default">${locale_operation_delete}</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <!--End table body-->
        </table>
    </div>
</div>
</body>
</html>
