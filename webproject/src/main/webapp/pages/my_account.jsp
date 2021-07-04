<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="customtag" prefix="mytag" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="user.account" var="locale_user_account"/>
<fmt:message key="common.create.user" var="locale_create_user"/>
<fmt:message key="common.login" var="locale_login"/>
<fmt:message key="common.forget" var="locale_forget"/>
<fmt:message key="common.username.name" var="locale_username_name"/>
<fmt:message key="common.username.enter" var="locale_username_enter"/>
<fmt:message key="common.email" var="locale_email"/>
<fmt:message key="common.phone" var="locale_phone"/>
<fmt:message key="common.email.enter" var="locale_email_enter"/>
<fmt:message key="common.email.register" var="locale_email_register"/>
<fmt:message key="common.password" var="locale_password"/>
<fmt:message key="common.password.enter" var="locale_password_enter"/>
<fmt:message key="user.first.name" var="locale_first_name"/>
<fmt:message key="user.last.name" var="locale_last_name"/>
<fmt:message key="common.address.detail" var="locale_address_detail"/>
<fmt:message key="main.save" var="locale_save"/>
<fmt:message key="main.submit" var="locale_submit"/>
<fmt:message key="common.signin.message" var="locale_signin_message"/>

<!doctype html>
<html lang="en">
<body>
<jsp:include page="../header.jsp"/>
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

    <div class="row" style="margin-top: 60px;">
        <div class="col-xs-8 col-xs-offset-2">
            <!--Nav tabs-->
            <ul class="nav nav-tabs">
                <li class="<c:if test="${activeNewAccount}">active</c:if>"><a href="#tab-1" data-toggle="tab"><span
                        style="color: darkblue">${locale_create_user}</span></a></li>
                <li class="<c:if test="${activeLogin}">active</c:if>"><a href="#tab-2" data-toggle="tab"><span
                        style="color: darkblue">${locale_login}</span></a></li>
                <li class="<c:if test="${activeForgetPassword}">active</c:if>"><a href="#tab-3" data-toggle="tab"><span
                        style="color: darkblue">${locale_forget}</span></a></li>
            </ul>
            <!--Tab panels-->
            <div class="tab-content">
                <!--Create a new User-->
                <div class="tab-pane fade <c:if test="${activeNewAccount}">active in</c:if>" id="tab-1">
                    <div class="panel-group">
                        <div class="panel panel-default">
                            <div class="panel-body" style="background-color: lightgray; margin-top: 20px;">
                                <c:if test="${duplicateEmail}">
                                    <div class="alert alert-warning">This email is already registered. Please choose
                                        another one
                                    </div>
                                </c:if>
                                <c:if test="${emailSent}">
                                    <div class="alert alert-info">An email has been sent to the email address.</div>
                                </c:if>
                                <form action="Controller" method="post">
                                    <input type="hidden" name="command" value="sign_up_command"/>
                                    <%--First name & last name--%>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <label for="firstName">${locale_first_name}</label>
                                                <input type="text" class="form-control" id="firstName" name="firstName"
                                                       required pattern="${attribute_regexp_fio}"/>
                                            </div>
                                            <div class="col-xs-6">
                                                <label for="lastName">${locale_last_name}</label>
                                                <input type="text" class="form-control" id="lastName" name="lastName"
                                                       required pattern="${attribute_regexp_fio}"/>
                                            </div>
                                        </div>
                                    </div>
                                    <%--Address--%>
                                    <div class="form-group">
                                        <label for="address">${locale_address_detail}</label>
                                        <input type="text" class="form-control" id="address" name="address"/>
                                    </div>
                                    <%--Username--%>
                                    <div class="form-group">
                                        <label for="username">${locale_username_name}</label>&nbsp;
                                        <input type="text" class="form-control" id="username" name="username"
                                               required pattern="${attribute_regexp_username}"/>
                                        <p style="color: gray">${locale_username_enter}</p>
                                    </div>
                                    <%--Phone--%>
                                    <div class="form-group">
                                        <label for="phone">${locale_phone}</label>&nbsp;
                                        <input type="text" class="form-control" id="phone" name="phone"
                                               required pattern="${attribute_regexp_phone_number}"/>
                                    </div>
                                    <%--Email--%>
                                    <div class="form-group">
                                        <label for="email">${locale_email}</label>
                                        <input type="email" class="form-control" id="email" name="email"
                                               required pattern="${attribute_regexp_email}"/>
                                        <p style="color: gray">${locale_email_enter}</p>
                                    </div>
                                    <button type="submit" class="btn btn-primary">${locale_save}</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!--Log in-->
                <div class="tab-pane fade <c:if test="${activeLogin}">active in</c:if>" id="tab-2">
                    <div class="panel-group">
                        <div class="panel panel-default">
                            <div class="panel-body" style="background-color: lightgray; margin-top: 20px;">
                                <c:if test="${message}">
                                    <div class="alert alert-warning">${locale_signin_message}</div>
                                </c:if>
                                <form action="Controller" method="post">
                                    <input type="hidden" name="command" value="sign_in_command">
                                    <c:if test="${wrongData}">
                                        <div class="alert alert-danger">Wrong email or password</div>
                                    </c:if>
                                    <div class="form-group">
                                        <label for="email">${locale_email}</label>
                                        <input required type="email" class="form-control" name="email"
                                               pattern="${attribute_regexp_email}"/>
                                        <p style="color: gray">${locale_email_enter}</p>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">${locale_password}</label>
                                        <input required type="password" class="form-control" id="password"
                                               name="password" pattern="${attribute_regexp_password}"/>
                                        <p style="color: gray">${locale_password_enter}</p>
                                    </div>
                                    <button type="submit" class="btn btn-primary">${locale_login}</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!--Forget password-->
                <div class="tab-pane fade <c:if test="${activeForgetPassword}">active in</c:if>" id="tab-3">
                    <div class="panel-group">
                        <div class="panel panel-default">
                            <div class="panel-body" style="background-color: lightgray; margin-top: 20px;">
                                <c:if test="${emailNotExists}">
                                    <div class="alert alert-danger">Email doesn't exist</div>
                                </c:if>
                                <c:if test="${emailSent}">
                                    <div class="alert alert-success">Email sent</div>
                                </c:if>
                                <form action="Controller" method="post">
                                    <input type="hidden" name="command" value="forget_password_command">
                                    <div class="form-group">
                                        <label for="recoverEmail">${locale_email}</label>
                                        <input required="required" type="email" class="form-control" id="recoverEmail"
                                               name="recoverEmail" pattern="${attribute_regexp_email}"/>
                                        <p style="color: gray">${locale_email_register}</p>
                                    </div>
                                    <button type="submit" class="btn btn-primary">${locale_submit}</button>
                                </form>
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

