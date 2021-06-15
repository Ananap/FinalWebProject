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

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="user.account" var="locale_user_accountt"/>
<fmt:message key="user.order.confirmation" var="locale_order_confirmation"/>
<fmt:message key="user.date.delivery" var="locale_date_delivery"/>
<fmt:message key="user.time.delivery" var="locale_time_delivery"/>
<fmt:message key="common.flower.detail" var="locale_flower_detail"/>
<fmt:message key="main.flower.list" var="locale_flower_list"/>

<!doctype html>
<html>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="row" style="margin-bottom: -100px">
        <div class="col-xs-8">
            <h2 class="section-headline"><span>${locale_user_accountt}</span></h2>
        </div>
        <div class="col-xs-4">
            <img src="static/image/logo1.jpg" class="img-responsive">
        </div>
    </div>
    <hr style="position: absolute; width: 75%; height: 6px; background-color: #1b6d85; z-index: -1; margin-top: -80px;"/>
    <img class="img-responsive" src="static/image/flower1.jpg" style="margin-top: -75px;">

    <div class="row" style="margin-top: 10px;">
        <div class="col-xs-12">
            <div class="alert alert-success">
                <h3><img style="width: 45px;" class="img-thumbnail"
                         src="static/image/greencheck.jpg">${locale_order_confirmation}</h3>
                <h4>${locale_date_delivery} </h4>
                <span>${order.dateDelivery}</span>
                <h4>${locale_time_delivery}</h4>
                <span>${order.timeOrder}</span>
            </div>
            <h3>${locale_flower_list}</h3>
            <c:forEach items="${orderFlowerList}" var="orderFlower">
                <div class="row">
                    <hr/>
                    <div class="col-xs-2"></div>
                    <div class="col-xs-2">
                        <img style="width: 70px;" class="img-responsive"
                             src="static/image/flower/${orderFlower.flower.flowerImage}"/>
                    </div>
                    <div class="col-xs-6">
                        <div class="center-block">
                            <h4>${orderFlower.flower.name}</h4>
                            <form action="Controller" method="post">
                                <input type="hidden" name="command" value="flower_detail_command"/>
                                <input type="hidden" value="${orderFlower.flower.id}" name="flowerId">
                                <button type="submit" class="btn btn-info">${locale_flower_detail}</button>
                            </form>
                        </div>
                    </div>
                    <div class="col-xs-2">
                        <h5 style="color: orangered; font-size: large">Br<span style="<c:if
                                test="${basketFlower.flower.storage.count} == 0">text-decoration: line-through</c:if>">${orderFlower.flower.price}</span>
                        </h5>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>

