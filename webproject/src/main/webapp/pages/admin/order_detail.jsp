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

<fmt:message key="admin.order.sum" var="locale_order_sum"/>
<fmt:message key="admin.item.name" var="locale_item_name"/>
<fmt:message key="admin.flower.price" var="locale_flower_price"/>
<fmt:message key="admin.item.quantity" var="locale_item_quantity"/>
<fmt:message key="admin.order.subtotal" var="locale_order_subtotal"/>
<fmt:message key="common.status" var="locale_common_status"/>
<fmt:message key="admin.order.status" var="locale_order_status"/>
<fmt:message key="admin.order.approved" var="locale_order_approved"/>
<fmt:message key="admin.order.inprocess" var="locale_order_inprocess"/>
<fmt:message key="admin.order.rejected" var="locale_order_rejected"/>
<fmt:message key="admin.order.total" var="locale_order_total"/>
<fmt:message key="main.save" var="locale_main_save"/>

<!doctype html>
<html lang="en">
<body>
<jsp:include page="../../header.jsp"/>
<div class="table-responsive">
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="change_order_status_command" />
        <input type="hidden" name="id" value="${order.id}"/>
        <div class="container">
            <h3><strong>${locale_order_sum}</strong></h3>
            <div class="table-responsive">
                <table class="table table-bordered table-hover table-striped">
                    <thead>
                    <tr>
                        <td><strong>${locale_item_name}</strong></td>
                        <td><strong>Status</strong></td>
                        <td class="text-center"><strong>${locale_flower_price}</strong></td>
                        <td class="text-center"><strong>${locale_item_quantity}</strong></td>
                        <td class="text-right"><strong>${locale_order_subtotal}</strong></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orderFlowerList}" var="orderFlower">
                    <tr>
                        <td><span data-th-text="${orderFlower.flower.name}"></span></td>
                        <td>
                            <label class="col-md-2 control-label" for="statusOrder">${locale_common_status}</label>
                            <select required value="${order.statusOrder}" id="statusOrder" name="statusOrder"
                                    class="form-control">
                                <option value="" selected disabled="disabled">${locale_order_status}</option>
                                <option value="APPROVED"
                                        selected="(${order.statusOrder}=='APPROVED')">${locale_order_approved}</option>
                                <option value="INPROCESS" selected="(${order.statusOrder}=='INPROCESS')">${locale_order_inprocess}</option>
                                <option value="REJECTED"
                                        selected="(${order.statusOrder}=='REJECTED')">${locale_order_rejected}</option>
                            </select>
                        </td>
                        <td class="text-center">${orderFlower.flower.price}</td>
                        <td class="text-center">${orderFlower.count}</td>
                        <td class="text-right">${orderFlower.subTotal}</td>
                    </tr>
                    </c:forEach>
                    <tr>
                        <td class="highrow"></td>
                        <td class="highrow"></td>
                        <td class="highrow"></td>
                        <td class="highrow text-center"><strong>${locale_order_total}</strong></td>
                        <td class="highrow text-right">${order.totalCost}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <br/>
            <button class="btn btn-success" type="submit">${locale_main_save}</button>
        </div>
    </form>
</div>
</body>
</html>
