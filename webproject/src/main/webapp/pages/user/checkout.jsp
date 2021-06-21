<%--
  Created by IntelliJ IDEA.
  User: panat
  Date: 11 июн. 2021 г.
  Time: 13:20
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
<fmt:message key="user.order.place" var="locale_order_place"/>
<fmt:message key="user.order.confirm" var="locale_order_confirm"/>
<fmt:message key="admin.order.sum" var="locale_order_sum"/>
<fmt:message key="user.order.address" var="locale_order_address"/>
<fmt:message key="common.address.detail" var="locale_address_detail"/>
<fmt:message key="user.order.delivery" var="locale_order_delivery"/>
<fmt:message key="user.order.time" var="locale_order_time"/>
<fmt:message key="user.time.select" var="locale_time_select"/>
<fmt:message key="common.next" var="locale_common_next"/>
<fmt:message key="user.order.payment" var="locale_order_payment"/>
<fmt:message key="user.payment.cash" var="locale_payment_cash"/>
<fmt:message key="common.yes" var="locale_common_yes"/>
<fmt:message key="common.no" var="locale_common_no"/>
<fmt:message key="user.review.item" var="locale_review_item"/>
<fmt:message key="admin.item.name" var="locale_item_name"/>
<fmt:message key="admin.price.name" var="locale_item_price"/>
<fmt:message key="user.storage.flower" var="locale_storage_flower"/>
<fmt:message key="user.storage.only" var="locale_storage_only"/>
<fmt:message key="user.flower.unavailable" var="locale_flower_unavailable"/>
<fmt:message key="user.order.amount" var="locale_order_amount"/>
<fmt:message key="user.total.price" var="locale_total_price"/>
<fmt:message key="admin.operation.delete" var="locale_operation_delete"/>

<!doctype html>
<html>
<body>
<jsp:include page="../../header.jsp"/>
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

    <div class="row" style="margin-top: 10px">
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="place_order_command"/>
            <!--Left Panel-->
            <div class="col-xs-4">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <button type="submit" class="btn btn-warning btn-block">${locale_order_place}</button>
                        <p style="font-size: smaller">${locale_order_confirm}</p>
                        <hr/>
                        <div class="row">
                            <div class="col-xs-7 text-left">
                                <h3 style="color: darkred"><strong>${locale_order_sum}</strong></h3>
                            </div>
                            <div class="col-xs-5 text-right">
                                <h3><strong style="color: darkred">Br <span>${basket.totalCost}</span></strong></h3>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!--CheckOut INFO-->
            <div class="col-xs-8">
                <c:if test="${missingRequiredField}">
                    <div>
                        <h5 class="alert alert-warning">There are some fields misssing</h5>
                    </div>
                </c:if>
                <div class="panel-group" id="accordeon">
                    <!--1. Address-->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordeon"
                                   href="#addressInfo">${locale_order_address}</a>
                            </h4>
                        </div>
                        <div id="addressInfo"
                             class="panel-collapse collapse <c:if test="${classActiveAddress}">in</c:if>">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="address">${locale_address_detail}</label>
                                    <input required type="text" class="form-control" id="address" name="address"
                                           value="${user.address}"/>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-xs-6">
                                            <label for="date">${locale_order_delivery}</label>
                                            <input required type="date" class="form-control" id="date" name="date"
                                                   max="2021-09-15" min="2021-06-20"/>
                                        </div>
                                        <div class="col-xs-6">
                                            <label for="time">${locale_order_time}</label>
                                            <select required id="time" name="time" class="form-control">
                                                <option value="" selected="selected"
                                                        disabled="disabled">${locale_time_select}</option>
                                                <option value="9:00 - 12:00">9:00 - 12:00</option>
                                                <option value="15:00 - 18:00">15:00 - 18:00</option>
                                                <option value="18:00 - 21:00">18:00 - 21:00</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <a data-toggle="collapse" data-parent="#accordeon" class="btn btn-warning pull-right"
                                   href="#paymentInfo">${locale_common_next}</a>
                            </div>
                        </div>
                    </div>
                    <!--2. Payment information-->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordeon"
                                   href="#paymentInfo">${locale_order_payment}</a>
                            </h4>
                        </div>
                        <div id="paymentInfo"
                             class="panel-collapse collapse <c:if test="${classActivePayment}">in</c:if>">
                            <div>
                                <p><b>${locale_payment_cash}</b></p>
                                <p><input name="cash" type="radio" value="true" checked/>${locale_common_yes}</p>
                                <p><input name="cash" type="radio" value="false"/>${locale_common_no}</p>
                            </div>
                            <a data-toggle="collapse" data-parent="#accordeon"
                               class="btn btn-warning pull-right" href="#reviewItems">${locale_common_next}</a>
                        </div>
                    </div>

                    <!--3. Review Items and Address-->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordeon"
                                   href="#reviewItems">${locale_review_item}</a>
                            </h4>
                        </div>
                        <div id="reviewItems" class="panel-collapse collapse">
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-8"><h4>${locale_item_name}</h4></div>
                                    <div class="col-xs-2"><h4>${locale_item_price}</h4></div>
                                    <div class="col-xs-2"><h4>${locale_order_amount}</h4></div>
                                </div>
                                <!--Display items in basket-->
                                <c:forEach items="${basketFlowerList}" var="basketFlower">
                                    <div class="row">
                                        <hr/>
                                        <div class="col-xs-2">
                                            <img style="width: 70px;" class="img-responsive"
                                                 src="static/image/flower/${basketFlower.flower.flowerImage}"/>
                                        </div>
                                        <div class="col-xs-6">
                                            <div class="center-block">
                                                <p><h4>${basketFlower.flower.name}</h4></p>
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
                                            </div>
                                        </div>
                                        <div class="col-xs-2">
                                            <h5 style="color: orangered; font-size: large">Br<span style="<c:if
                                                    test="${basketFlower.flower.storage.count} == 0">text-decoration: line-through</c:if>">${basketFlower.flower.price}</span>
                                            </h5>
                                        </div>
                                        <div class="col-xs-2">
                                            <h5 style="font-size: large">${basketFlower.count}</h5>
                                        </div>
                                    </div>
                                </c:forEach>
                                <hr/>
                                <h4 class="col-xs-12 text-right"><strong
                                        style="font-size: large">${locale_total_price}</strong>
                                    <span style="color: orangered; font-size: large">Br<span>${basket.totalCost}</span></span>
                                </h4>
                                <br/><br/>
                                <button type="submit" class="btn btn-warning btn-block">${locale_order_place}</button>
                                <p style="font-size: smaller">${locale_order_confirm}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        var d = new Date();
        var day = d.getDate();
        var month = d.getMonth() + 1;
        var year = d.getFullYear();
        var name_input = document.getElementById('date')
        name_input.value = day + "-" + month + "-" + year;
    });
</script>
<jsp:include page="../../footer.jsp"/>
</body>
</html>

