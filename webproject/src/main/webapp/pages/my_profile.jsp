<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="common.edit" var="locale_common_edit"/>
<fmt:message key="main.order" var="locale_main_order"/>
<fmt:message key="common.order.payment" var="locale_common_order_payment"/>
<fmt:message key="user.first.name" var="locale_user_first_name"/>
<fmt:message key="user.last.name" var="locale_user_last_name"/>
<fmt:message key="common.address.detail" var="locale_address_detail"/>
<fmt:message key="common.username.name" var="locale_username_name"/>
<fmt:message key="user.current.password" var="locale_current_password"/>
<fmt:message key="user.password.enter" var="locale_user_password_enter"/>
<fmt:message key="common.email" var="locale_common_email"/>
<fmt:message key="common.phone" var="locale_phone"/>
<fmt:message key="user.new.password" var="locale_user_new_password"/>
<fmt:message key="user.password.confirm" var="locale_user_password_confirm"/>
<fmt:message key="user.change.confirm" var="locale_user_change_confirm"/>
<fmt:message key="common.create.user" var="locale_create_user"/>
<fmt:message key="user.order.date" var="locale_order_date"/>
<fmt:message key="user.order.number" var="locale_order_number"/>
<fmt:message key="admin.order.total" var="locale_order_total"/>
<fmt:message key="common.status" var="locale_common_status"/>
<fmt:message key="user.order.detail" var="locale_order_detail"/>
<fmt:message key="common.order.payment" var="locale_order_payment"/>
<fmt:message key="admin.order.cash" var="locale_admin_order_cash"/>
<fmt:message key="user.card.name" var="locale_user_card_name"/>
<fmt:message key="user.card.holder" var="locale_user_card_holder"/>
<fmt:message key="user.card.number" var="locale_user_card_number"/>
<fmt:message key="user.card.date" var="locale_user_card_date"/>
<fmt:message key="user.delivery.info" var="locale_user_delivery_info"/>
<fmt:message key="user.order.delivery" var="locale_user_order_delivery"/>
<fmt:message key="user.order.time" var="locale_user_order_time"/>
<fmt:message key="admin.order.sum" var="locale_admin_order_sum"/>
<fmt:message key="admin.item.name" var="locale_admin_item_name"/>
<fmt:message key="admin.flower.price" var="locale_flower_price"/>
<fmt:message key="admin.item.quantity" var="locale_item_quantity"/>

<!doctype html>
<html lang="en">
<body>
<jsp:include page="../header.jsp"/>
<div class="container">
    <div class="row" style="margin-bottom: -100px">
        <div class="col-xs-8">
            <h2 class="section-headline">
                <span>${user.username}</span>
            </h2>
        </div>
        <div class="col-xs-4">
            <img src="../static/image/logo1.jpg" class="img-responsive">
        </div>
    </div>
    <hr style="position: absolute; width: 75%; height: 6px; background-color: #1b6d85; z-index: -1; margin-top: -80px;"/>
    <img class="img-responsive" src="../static/image/flower1.jpg" style="margin-top: -75px;">

    <div class="row" style="margin-top: 60px;">
        <div class="col-xs-8 col-xs-offset-2">
            <!--Nav tabs-->
            <ul class="nav nav-tabs">
                <li class="<c:if test="${activeEdit}">active</c:if>"><a href="#tab-1" data-toggle="tab"><span
                        style="color: darkblue">${locale_common_edit}</span></a></li>
                <li class="<c:if test="${activeOrders}">active</c:if>"><a href="#tab-2" data-toggle="tab"><span
                        style="color: darkblue"></span>${locale_main_order}</a></li>
            </ul>
            <!--Tab panels-->
            <div class="tab-content">
                <!--Edit User Info-->
                <div class="tab-pane fade <c:if test="${activeEdit}">active in</c:if>" id="tab-1">
                    <div class="panel-group">
                        <div class="panel panel-default">
                            <div class="panel-body" style="background-color: lightgray; margin-top: 20px;">
                                <%--EDIT--%>
                                <form action="Controller" method="post">
                                    <input type="hidden" name="command" value="personal_edit_command">
                                    <c:if test="${updateUserInfo}">
                                        <div class="bg-info">User Info Updated</div>
                                    </c:if>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <label for="firstName">${locale_user_first_name}</label>
                                                <input type="text" class="form-control" id="firstName" name="firstName"
                                                       value="${user.firstName}"/>
                                            </div>
                                            <div class="col-xs-6">
                                                <label for="lastName">${locale_user_last_name}</label>
                                                <input type="text" class="form-control" id="lastName" name="lastName"
                                                       value="${user.lastName}"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="address">${locale_address_detail}</label>
                                        <input type="text" class="form-control" id="address" name="address"
                                               value="${user.address}"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="username">${locale_username_name}</label>
                                        <input type="text" class="form-control" id="username" name="username"
                                               value="${user.username}"/>
                                    </div>

                                    <div class="form-group">
                                        <label for="phone">${locale_phone}</label>
                                        <input type="text" class="form-control" id="phone" name="phone"
                                               value="${user.phone}"/>
                                    </div>

                                    <div class="form-group">
                                        <c:if test="${currentPasswordNotEquals}">
                                            <div class="alert alert-danger">Current password is wrong!</div>
                                        </c:if>
                                        <label for="currentPassword">${locale_current_password}</label>
                                        <input required type="password" class="form-control" id="currentPassword"
                                               name="currentPassword"/>
                                    </div>
                                    <p style="color: gray">${locale_user_password_enter}</p>

                                    <div class="form-group">
                                        <c:if test="${passwordNotEquals}">
                                            <div class="alert alert-danger">Please enter equal passwords in both
                                                fields
                                            </div>
                                        </c:if>
                                        <label for="newPassword">${locale_user_new_password}</label>
                                        <input type="password" class="form-control" id="newPassword"
                                               name="newPassword"/>
                                    </div>

                                    <div class="form-group">
                                        <label for="confirmPassword">${locale_user_password_confirm}</label>
                                        <input type="password" class="form-control" id="confirmPassword"
                                               name="confirmPassword"/>
                                    </div>
                                    <p style="color: gray">${locale_user_change_confirm}</p>
                                    <button type="submit" class="btn btn-primary">${locale_create_user}</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!--Order Info-->
                <div class="tab-pane fade <c:if test="${activeOrders}">active in</c:if>" id="tab-2">
                    <div class="panel-group">
                        <div class="panel panel-default">
                            <div class="panel-body" style="background-color: lightgray; margin-top: 20px;">
                                <table class="table table-sm table-inverse">
                                    <thead>
                                    <tr>
                                        <th>${locale_order_date}</th>
                                        <th>${locale_order_number}</th>
                                        <th>${locale_order_total}</th>
                                        <th>${locale_common_status}</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="order" items="${orderList}">
                                        <tr>
                                            <td>
                                                <a href="/orderDetail(id=${order.id})}">
                                                    <span>${order.dateOrder}</span>
                                                </a>
                                            </td>
                                            <td data-th-text="${order.id}"></td>
                                            <td data-th-text="${order.totalCost}"></td>
                                            <td data-th-text="${order.statusOrder}"></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <c:if test="${displayOrderDetail}">
                                    <div>
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="text-center">
                                                    <h2><span>${locale_order_detail} + ${order.id}</span></h2>
                                                </div>
                                                <hr/>

                                                <div class="row">
                                                    <div class="col-xs-4">
                                                        <div class="panel panel-default height">
                                                            <div class="panel-heading">
                                                                <strong>${locale_address_detail}</strong></div>
                                                            <div class="panel-body">
                                                                <span>${order.address}</span><br/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-4">
                                                        <div class="panel panel-default height">
                                                            <div class="panel-heading">
                                                                <strong>${locale_order_payment}</strong>
                                                            </div>
                                                            <c:if test="${cash}">
                                                                <div>${locale_admin_order_cash}</div>
                                                            </c:if>
                                                            <c:if test="${!cash}">
                                                                <div class="panel-body">
                                                                    <span>${locale_user_card_name}</span>
                                                                    <span>${order.payment.cardName}</span><br/>
                                                                    <span>${locale_user_card_holder}</span>
                                                                    <span>${order.payment.holderName}</span><br/>
                                                                    <span>${locale_user_card_number}:</span>
                                                                    <span>${order.payment.cardNumber}</span><br/>
                                                                    <span>${locale_user_card_date}</span>
                                                                    <span>${order.payment.expiryMonth}</span>/<span>${order.payment.expiryYear}</span><br/>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-4">
                                                        <div class="panel panel-default height">
                                                            <div class="panel-heading">
                                                                <strong>${locale_user_delivery_info}</strong>
                                                            </div>
                                                            <div class="panel-body">
                                                                <span>${locale_user_order_delivery}</span>
                                                                <span>${order.dateDelivery}</span><br/>
                                                                <span>${locale_user_order_time}</span>
                                                                <span>${order.timeOrder}</span><br/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="panel-heading">
                                                    <h3 class="text-center"><strong>${locale_admin_order_sum}</strong>
                                                    </h3>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="table-responsive">
                                                        <table class="table table-condensed">
                                                            <thead>
                                                            <tr>
                                                                <td><strong>${locale_admin_item_name}</strong></td>
                                                                <td class="text-center">
                                                                    <strong>${locale_flower_price}</strong></td>
                                                                <td class="text-center">
                                                                    <strong>${locale_item_quantity}</strong></td>
                                                                <td class="text-right">
                                                                    <strong>${locale_order_total}</strong></td>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="orderFlower" items="${orderFlowerList}">
                                                                <tr>
                                                                    <td>${orderFlower.flower.name}</td>
                                                                    <td class="text-center">${orderFlower.flower.price}</td>
                                                                    <td class="text-center">${orderFlower.count}</td>
                                                                    <td class="text-center">${orderFlower.subTotal}</td>
                                                                </tr>
                                                            </c:forEach>
                                                            <tr>
                                                                <td class="highrow"></td>
                                                                <td class="highrow"></td>
                                                                <td class="highrow text-center">
                                                                    <strong>${locale_order_total}</strong></td>
                                                                <td class="highrow text-right">${order.totalCost}</td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>
