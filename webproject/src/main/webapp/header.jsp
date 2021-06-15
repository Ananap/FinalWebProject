<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="main.title" var="locale_main_title"/>
<fmt:message key="main.catalog" var="locale_main_catalog"/>
<fmt:message key="main.flower.list" var="locale_main_flower_list"/>
<fmt:message key="main.store.hour" var="locale_main_store_hour"/>
<fmt:message key="main.add.item" var="locale_main_add_item"/>
<fmt:message key="main.admin.flowerlist" var="locale_main_admin_flowerlist"/>
<fmt:message key="main.admin.create" var="locale_main_admin_create"/>
<fmt:message key="main.flower.title" var="locale_main_flower_title"/>
<fmt:message key="main.admin.order" var="locale_main_admin_order"/>
<fmt:message key="main.basket.title" var="locale_main_basket_title"/>
<fmt:message key="main.profile.title" var="locale_main_profile_title"/>
<fmt:message key="main.account.title" var="locale_main_account_title"/>
<fmt:message key="main.logout" var="locale_main_logout"/>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <title>${locale_main_title}</title>

    <!-- Bootstrap core CSS -->
    <link href="static/css/bootstrap.css" rel="stylesheet"/>
    <link href="static/css/flowerItem.css" rel="stylesheet">
    <link href="static/css/flower.css" rel="stylesheet">
    <link href="static/css/fontawesome/fontawesome.css" rel="stylesheet"/>

    <link href="static/image" type="text/html">
    <link href="static/image/flower" type="text/html">

    <link href="static/css/style.css" rel="stylesheet"/>

    <script src="static/js/jquery.js"></script>
    <script src="static/js/bootstrap.min.js"></script>
    <script src="static/js/script.js"></script>
</head>

<body>
<div class="page-top" style="width: 100%; height: 20px; background-color: #1b6d85;"></div>
<!-- Static navbar -->
<nav class="navbar navbar-default navbar-inverse">
    <div class="container-fluid">
        <c:set var="user" value="${sessionScope.user}"/>
        <div class="navbar-header">
            <a class="navbar-brand" href="Controller?command=go_to_about_page_command">${locale_main_title}</a>
        </div>
        <div id="navbar">
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="Controller?command=go_to_about_page_command" class="dropdown-toggle" data-toggle="dropdown"
                       role="button" aria-haspopup="true"
                       aria-expanded="false">${locale_main_catalog}<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="Controller?command=go_to_item_page_command">${locale_main_flower_list}</a></li>
                        <c:if test="${user.role == 'ADMIN'}">
                            <li><a href="Controller?command=go_to_add_item_page_command">${locale_main_add_item}</a>
                            </li>
                            <li>
                                <a href="Controller?command=go_to_flower_list_page_command">${locale_main_admin_flowerlist}</a>
                            </li>
                        </c:if>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <form method="get" class="navbar-form">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="${locale_main_flower_title}"/>
                    </div>
                </form>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false"><img class="image-header" src="static/image/lang.png"/><span
                            class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li>
                            <button form="localeForm" type="submit" name="locale" value="ru">
                                <img class="image-header-dropdown" src="static/image/russia.png"/>
                            </button>
                        </li>
                        <li>
                            <button form="localeForm" type="submit" name="locale" value="en">
                                <img class="image-header-dropdown" src="static/image/en.png"/></button>
                        </li>
                    </ul>
                </li>
                <c:if test="${user.role == 'ADMIN'}">
                    <li>
                        <a href="Controller?command=go_to_order_info_page_command">${locale_main_admin_order}</a>
                    </li>
                </c:if>
                <c:if test="${user.role == 'USER' || user.role == 'ADMIN'}">
                    <li>
                        <a href="Controller?command=go_to_basket_page_command">${locale_main_basket_title}</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle cl-white" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false">
                                ${user.username}
                        </a>

                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">

                            <a type="button" class="btn dropdown-item"
                               href="Controller?command=go_to_profile_page_command">${locale_main_profile_title}</a>

                            <div class="dropdown-divider"></div>

                            <button form="headerForm" class="btn btn-primary btn-group-justified" type="submit"
                                    name="command" value="log_out_command">${locale_main_logout}
                            </button>

                        </div>
                    </li>
                </c:if>
                <c:if test="${user.email == null}">
                    <li>
                        <a href="Controller?command=go_to_login_page_command">${locale_main_account_title}</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
<form id="localeForm" action="Controller" method="post">
    <input type="hidden" name="command" value="change_locale_command">
</form>

<form id="headerForm" action="Controller" method="post"></form>
</body>