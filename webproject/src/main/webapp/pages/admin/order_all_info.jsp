<%--
  Created by IntelliJ IDEA.
  User: panat
  Date: 15 июн. 2021 г.
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="common.address.detail" var="locale_order_address"/>
<fmt:message key="common.order.payment" var="locale_order_payment"/>
<fmt:message key="common.order.delivery" var="locale_order_delivery"/>
<fmt:message key="common.order.date" var="locale_order_date"/>
<fmt:message key="common.status" var="locale_order_status"/>
<fmt:message key="common.detail" var="locale_order_detail"/>
<fmt:message key="admin.order.cash" var="locale_order_cash"/>
<fmt:message key="admin.order.card" var="locale_order_card"/>
<fmt:message key="common.detail" var="locale_common_detail"/>

<!doctype html>
<html lang="en">
<link rel="stylesheet">
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <br/>
    <div class="table-responsive">
        <table class="table table-bordered table-hover table-striped">
            <thead>
            <tr>
                <th>${locale_order_address}</th>
                <th>${locale_order_payment}</th>
                <th>${locale_order_delivery}</th>
                <th>${locale_order_date}</th>
                <th>${locale_order_status}</th>
                <th>${locale_order_detail}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orderList}">
            <tr>
                <td>${order.address}</td>
                <td>
                    <c:if test="${order.cash}">
                    <span>${locale_order_cash}</span>
                    </c:if>
                    <c:if test="${!order.cash}">
                    <span>${locale_order_card}</span>
                    </c:if>
                </td>
                <td>${order.dateDelivery}</td>
                <td>${order.dateOrder}</td>
                <td>${order.statusOrder}</td>
                <td>
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="go_to_order_detail_page_command" />
                        <input type="hidden" name="id" value="${order.id}" />
                        <button type="submit" class="btn btn-link">${locale_common_detail}</button>
                    </form>
                    <br/>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('#buttonClear').click(function () {
            window.location = "Controller?command=go_to_flower_list_page_command";
        })
    })
</script>
</body>
</html>
