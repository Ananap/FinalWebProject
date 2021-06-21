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
<fmt:message key="main.category" var="locale_main_category"/>
<fmt:message key="main.soil" var="locale_main_soil"/>
<fmt:message key="admin.flower.watering" var="locale_flower_watering"/>
<fmt:message key="admin.country.name" var="locale_country_name"/>
<fmt:message key="admin.light.name" var="locale_light_name"/>
<fmt:message key="admin.price.name" var="locale_price_name"/>
<fmt:message key="user.order.amount" var="locale_order_amount"/>
<fmt:message key="user.storage.flower" var="locale_storage_flower"/>
<fmt:message key="user.storage.only" var="locale_storage_only"/>
<fmt:message key="user.flower.unavailable" var="locale_flower_unavailable"/>
<fmt:message key="user.busket.add" var="locale_busket_add"/>

<!doctype html>
<html lang="en">
<body>
<jsp:include page="../header.jsp"/>
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

    <form action="Controller" method="post">
        <input type="hidden" name="command" value="add_item_to_basket_command">
        <input hidden="hidden" name="flowerId" value="${flower.id}"/>
        <input hidden="hidden" name="flowerPrice" value="${flower.price}"/>
        <input hidden="hidden" name="storageAmount" value="${storage.count}"/>
        <div class="row" style="margin-top: 120px;">
            <div class="col-xs-3">
                <a href="Controller?command=go_to_item_page_command">${locale_continue_shopping}</a><br/>
                <img class="img-responsive page-flower" src="static/image/flower/${flower.flowerImage}"/>
            </div>

            <div class="col-xs-9">
                <c:if test="${addFlowerSuccess}">
                    <div class="alert alert-success">An item has been added to your shopping basket</div>
                </c:if>
                <c:if test="${notEnoughStorage}">
                    <div class="alert alert-warning">Oooops, some of the products don't have enough count in storage.
                        Please update product quantity
                    </div>
                </c:if>
                <h3>${flower.name}</h3>
                <div class="row">
                    <div class="col-xs-5">
                        <h5><strong>${locale_main_category} </strong><span>${flower.flowerType.description}</span></h5>
                        <h5><strong>${locale_main_soil} </strong>
                            <c:if test="${flower.soil == 'PODZOLIC'}">
                                <span>Подзолистая</span>
                            </c:if>
                            <c:if test="${flower.soil == 'SODPODZOLIC'}">
                                <span>Дерново-подзолистая</span>
                            </c:if>
                            <c:if test="${flower.soil == 'UNPAVED'}">
                                <span>Грунтовая</span>
                            </c:if>
                        </h5>
                        <h5><strong>${locale_flower_watering} </strong><span>${flower.watering}  Мл в неделю</span></h5>
                        <h5><strong>${locale_country_name} </strong><span>${flower.originCountry}</span></h5>
                        <c:if test="${flower.light}">
                            <h5><strong>${locale_light_name} </strong><span>Светолюбивое</span></h5>
                        </c:if>
                        <c:if test="${!flower.light}">
                            <h5><strong>${locale_light_name} </strong><span>Темнолюбивое</span></h5>
                        </c:if>
                    </div>
                    <div class="col-xs-7">
                        <div class="panel panel-default" style="border-width: 5px; margin-top: 20px;">
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-6">
                                        <h4>${locale_price_name} <span
                                                style="color: darkorange;">Br<span>${flower.price}</span></span></h4>
                                        <span>${locale_order_amount}</span>
                                        <input required type="number" step="1"
                                               class="form-control" name="amount" min="1"
                                               placeholder="${locale_order_amount}"/>
                                    </div>
                                    <div class="col-xs-6">
                                        <c:if test="${storage.count >= 11}">
                                            <h4 style="color: green">${locale_storage_flower}</h4>
                                        </c:if>
                                        <c:if test="${storage.count < 11 && storage.count > 0}">
                                            <h4 style="color: green">
                                                <span>${locale_storage_only}  ${storage.count} ${locale_storage_flower}</span>
                                            </h4>
                                        </c:if>
                                        <c:if test="${storage.count==0}">
                                            <h4 style="color: darkred">${locale_flower_unavailable}</h4>
                                        </c:if>
                                        <button type="submit" class="btn btn-warning"
                                                style="color: black; border: 1px solid black; padding: 10px 40px 10px 40px;">${locale_busket_add}</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <hr/>
                <p>${flower.description}</p>
            </div>
        </div>
    </form>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>

