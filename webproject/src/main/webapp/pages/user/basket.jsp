<%--
  Created by IntelliJ IDEA.
  User: panat
  Date: 8 июн. 2021 г.
  Time: 8:51
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

<fmt:message key="common.all.items" var="locale_common_all_items"/>
<fmt:message key="user.continue.shopping" var="locale_continue_shopping"/>
<fmt:message key="user.order.check" var="locale_order_check"/>
<fmt:message key="main.flower.list" var="locale_flower_list"/>
<fmt:message key="admin.price.name" var="locale_admin_price_name"/>
<fmt:message key="user.order.count" var="locale_order_count"/>
<fmt:message key="user.storage.flower" var="locale_storage_flower"/>
<fmt:message key="user.storage.only" var="locale_storage_only"/>
<fmt:message key="user.flower.unavailable" var="locale_flower_unavailable"/>
<fmt:message key="admin.operation.delete" var="locale_operation_delete"/>
<fmt:message key="common.update" var="locale_common_update"/>
<fmt:message key="user.total.price" var="locale_total_price"/>

<!doctype html>
<html>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <div class="row" style="margin-bottom: -100px">
        <div class="col-xs-8">
            <h2 class="section-headline">
                <span>${locale_common_all_items}</span>
            </h2>
        </div>
        <div class="col-xs-4">
            <img src="static/image/logo1.jpg" class="img-responsive">
        </div>
    </div>
    <hr style="position: absolute; width: 75%; height: 6px; background-color: #1b6d85; z-index: -1; margin-top: -80px;"/>
    <mytag:image/>

    <div class="row" style="margin-top: 20px">
        <div class="col-xs-12">
            <div class="row">
                <div class="col-xs-6 text-left">
                    <a class="btn btn-warning"
                       href="Controller?command=go_to_item_page_command">${locale_continue_shopping}</a>
                </div>
                <div class="col-xs-6 text-right">
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="go_to_check_out_page_command">
                        <input type="hidden" name="id" value="${basket.id}">
                        <button class="btn btn-primary" type="submit">${locale_order_check}</button>
                    </form>
                </div>
                <br/>
                <c:if test="${notEnoughStorage}">
                    <div class="alert alert-warning">
                        Oooops, some of the products don't have enough count in storage. Please update product quantity
                    </div>
                </c:if>
                <c:if test="${emptyBasket}">
                    <div class="alert alert-warning">
                        Oooops, your basket is empty. See if you can find what you like in the Flower List
                    </div>
                </c:if>
                <c:if test="${removeItem}">
                    <div class="alert alert-success">
                        Item is successfully removed
                    </div>
                </c:if>
                <br/>
                <div class="row">
                    <div class="col-xs-8"><h4 class="center-block">${locale_flower_list}</h4></div>
                    <div class="col-xs-2"><h4>${locale_admin_price_name}</h4></div>
                    <div class="col-xs-2"><h4>${locale_order_count}</h4></div>
                </div>

                <!--Display Flowers in Cart-->
                <c:forEach items="${basketFlowerList}" var="basketFlower">
                    <div class="row">
                            <hr/>
                            <div class="col-xs-2">
                                <img style="width: 80px" class="img-responsive flower"
                                     src="static/image/flower/${basketFlower.flower.flowerImage}">

                            </div>
                            <div class="col-xs-6">
                                <div class="center-block">
                                    <h4>${basketFlower.flower.name}</h4>
                                    <c:if test="${basketFlower.flower.storage.count >= 11}">
                                        <h4 style="color: green">${locale_storage_flower}</h4>
                                    </c:if>
                                    <c:if test="${basketFlower.flower.storage.count < 11 && basketFlower.flower.storage.count > 0}">
                                        <h4 style="color: green">
                                            <span>${locale_storage_only}  ${basketFlower.flower.storage.count} ${locale_storage_flower}</span>
                                        </h4>
                                    </c:if>
                                    <c:if test="${basketFlower.flower.storage.count == 0}">
                                        <h4 style="color: darkred">${locale_flower_unavailable}</h4>
                                    </c:if>
                                    <form action="Controller" method="post">
                                        <input type="hidden" name="command" value="remove_item_command"/>
                                        <input type="hidden" name="basketFlowerId" value="${basketFlower.id}"/>
                                        <button class="btn btn-info" type="submit">${locale_operation_delete}</button>
                                    </form>
                                </div>
                            </div>
                            <div class="col-xs-2">
                                <h5 style="color: orangered; font-size: large">Br<span
                                        style="<c:if
                                                test="${basketFlower.flower.storage.count} == 0">text-decoration: line-through</c:if>">${basketFlower.flower.price}</span>
                                </h5>
                            </div>
                        <div class="col-xs-2">
                            <form action="Controller" method="post">
                                <input type="hidden" name="command" value="update_basket_command"/>
                                <input hidden="hidden" name="id" value="${basketFlower.id}"/>
                                <input id="count" name="count" type="number" min="1"
                                       <c:if test="${basketFlower.flower.storage.count==0}">disabled</c:if>
                                       class="form-control basketFlowerCount" value="${basketFlower.count}"/>
                                <button style="display: none;" id="update-item-${basketFlower.id}" type="submit"
                                        class="btn btn-warning btn-xs">${locale_common_update}</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
                <div class="row">
                    <hr/>
                    <h4 class="col-xs-12 text-right"><strong style="font-size: large">${locale_total_price}</strong>
                        <span style="color: orangered; font-size: large">Br<span>${basket.totalCost}</span></span></h4>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../footer.jsp"/>
</body>
</html>