<%--
  Created by IntelliJ IDEA.
  User: panat
  Date: 14 июн. 2021 г.
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="common.back" var="locale_common_back"/>
<fmt:message key="common.edit" var="locale_common_edit"/>
<fmt:message key="admin.flower.water" var="locale_flower_water"/>
<fmt:message key="main.category" var="locale_main_category"/>
<fmt:message key="main.soil" var="locale_main_soil"/>
<fmt:message key="admin.flower.watering" var="locale_flower_watering"/>
<fmt:message key="admin.country.name" var="locale_country_name"/>
<fmt:message key="admin.light.name" var="locale_light_name"/>
<fmt:message key="admin.price.name" var="locale_price_name"/>
<fmt:message key="admin.storage.name" var="locale_storage_name"/>
<fmt:message key="main.description" var="locale_main_description"/>

<!doctype html>
<html lang="en">
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <div>
        <ol class="breadcrumb">
            <li><a href="Controller?command=go_to_flower_list_page_command">${locale_common_back}</a>
            </li>
            <li>
                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="go_to_update_item_page_command"/>
                    <input type="hidden" name="flowerId" value="${flower.id}"/>
                    <button class="btn btn-link" type="submit">${locale_common_edit}</button>
                </form>
            </li>
        </ol>
    </div>
    <div class="row">
        <div class="col-xs-3">
            <div class="panel panel-default">
                <div class="panel-body">
                    <img class="img-responsive center-block" src="static/image/flower/${flower.flowerImage}"/>
                </div>
            </div>
        </div>
        <div class="col-xs-9">
            <h3>${flower.name}</h3>
            <div class="row">
                <div class="col-xs-6">
                    <p><strong>${locale_main_category} </strong><span>${flower.flowerType.description}</span></p>
                    <p><strong>${locale_main_soil} </strong>
                        <c:if test="${flower.soil == 'PODZOLIC'}">
                            <span>Подзолистая</span>
                        </c:if>
                        <c:if test="${flower.soil == 'SODPODZOLIC'}">
                            <span>Дерново-подзолистая</span>
                        </c:if>
                        <c:if test="${flower.soil == 'UNPAVED'}">
                            <span>Грунтовая</span>
                        </c:if>
                    </p>
                    <p><strong>${locale_flower_watering} </strong><span>${flower.watering} ${locale_flower_water}</span>
                    </p>
                </div>
                <div class="col-xs-6">
                    <p><strong>${locale_country_name} </strong><span>${flower.originCountry}</span></p>
                    <p>
                        <strong>${locale_light_name} </strong>
                        <c:if test="${flower.light}">
                            <span>светолюбивое</span>
                        </c:if>
                        <c:if test="${!flower.light}">
                            <span>темнолюбивое</span>
                        </c:if>
                    </p>
                    <p><strong>${locale_price_name} </strong><span>${flower.price}</span></p>
                    <p><strong>${locale_storage_name} </strong><span>${flower.storage.count}</span></p>
                </div>
            </div>
            <p><strong>${locale_main_description} </strong><span>${flower.description}</span></p>
        </div>
    </div>
</div>
</body>
</html>
